import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPerformanceIndicator, PerformanceIndicator } from 'app/shared/model/performance-indicator.model';
import { PerformanceIndicatorService } from './performance-indicator.service';

@Component({
  selector: 'jhi-performance-indicator-update',
  templateUrl: './performance-indicator-update.component.html',
})
export class PerformanceIndicatorUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(100)]],
  });

  constructor(
    protected performanceIndicatorService: PerformanceIndicatorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ performanceIndicator }) => {
      this.updateForm(performanceIndicator);
    });
  }

  updateForm(performanceIndicator: IPerformanceIndicator): void {
    this.editForm.patchValue({
      id: performanceIndicator.id,
      name: performanceIndicator.name,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const performanceIndicator = this.createFromForm();
    if (performanceIndicator.id !== undefined) {
      this.subscribeToSaveResponse(this.performanceIndicatorService.update(performanceIndicator));
    } else {
      this.subscribeToSaveResponse(this.performanceIndicatorService.create(performanceIndicator));
    }
  }

  private createFromForm(): IPerformanceIndicator {
    return {
      ...new PerformanceIndicator(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPerformanceIndicator>>): void {
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
  
}
