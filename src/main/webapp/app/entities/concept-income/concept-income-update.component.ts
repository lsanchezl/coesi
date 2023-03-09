import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IConceptIncome, ConceptIncome } from 'app/shared/model/concept-income.model';
import { ConceptIncomeService } from './concept-income.service';

@Component({
  selector: 'jhi-concept-income-update',
  templateUrl: './concept-income-update.component.html',
})
export class ConceptIncomeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(100)]],
    keyConcept: [null, [Validators.maxLength(20)]],
  });

  constructor(protected conceptIncomeService: ConceptIncomeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conceptIncome }) => {
      this.updateForm(conceptIncome);
    });
  }

  updateForm(conceptIncome: IConceptIncome): void {
    this.editForm.patchValue({
      id: conceptIncome.id,
      name: conceptIncome.name,
      keyConcept: conceptIncome.keyConcept,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const conceptIncome = this.createFromForm();
    if (conceptIncome.id !== undefined) {
      this.subscribeToSaveResponse(this.conceptIncomeService.update(conceptIncome));
    } else {
      this.subscribeToSaveResponse(this.conceptIncomeService.create(conceptIncome));
    }
  }

  private createFromForm(): IConceptIncome {
    return {
      ...new ConceptIncome(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      keyConcept: this.editForm.get(['keyConcept'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConceptIncome>>): void {
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
