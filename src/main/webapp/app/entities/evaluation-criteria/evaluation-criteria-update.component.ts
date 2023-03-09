import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEvaluationCriteria, EvaluationCriteria } from 'app/shared/model/evaluation-criteria.model';
import { EvaluationCriteriaService } from './evaluation-criteria.service';
import { IPerformanceIndicator } from 'app/shared/model/performance-indicator.model';
import { PerformanceIndicatorService } from 'app/entities/performance-indicator/performance-indicator.service';
import { INGroup } from 'app/shared/model/n-group.model';

type SelectableEntity = IPerformanceIndicator | INGroup;

@Component({
  selector: 'jhi-evaluation-criteria-update',
  templateUrl: './evaluation-criteria-update.component.html',
})
export class EvaluationCriteriaUpdateComponent implements OnInit {
  isSaving = false;
  performanceindicators: IPerformanceIndicator[] = [];
  ngroups: INGroup[] = [];
  deliveryDateDp: any;

  editForm = this.fb.group({
    id: [],
    description: [null, [Validators.required, Validators.maxLength(100)]],
    percentage: [null, [Validators.required]],
    deliveryDate: [],
    performanceIndicator: [null, Validators.required],
    nGroup: [null, Validators.required],
  });

  constructor(
    protected evaluationCriteriaService: EvaluationCriteriaService,
    protected performanceIndicatorService: PerformanceIndicatorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ evaluationCriteria }) => {
      this.updateForm(evaluationCriteria);
      
      this.performanceIndicatorService.list().subscribe(performanceindicators => {
        this.performanceindicators = performanceindicators;
      });

    });
  }

  updateForm(evaluationCriteria: IEvaluationCriteria): void {
    this.editForm.patchValue({
      id: evaluationCriteria.id,
      description: evaluationCriteria.description,
      percentage: evaluationCriteria.percentage,
      deliveryDate: evaluationCriteria.deliveryDate,
      performanceIndicator: evaluationCriteria.performanceIndicator,
      nGroup: evaluationCriteria.nGroup,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const evaluationCriteria = this.createFromForm();
    if (evaluationCriteria.id !== undefined) {
      this.subscribeToSaveResponse(this.evaluationCriteriaService.update(evaluationCriteria));
    } else {
      this.subscribeToSaveResponse(this.evaluationCriteriaService.create(evaluationCriteria));
    }
  }

  private createFromForm(): IEvaluationCriteria {
    return {
      ...new EvaluationCriteria(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
      percentage: this.editForm.get(['percentage'])!.value,
      deliveryDate: this.editForm.get(['deliveryDate'])!.value,
      performanceIndicator: this.editForm.get(['performanceIndicator'])!.value,
      nGroup: this.editForm.get(['nGroup'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEvaluationCriteria>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
