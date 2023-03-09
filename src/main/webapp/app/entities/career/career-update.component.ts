import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICareer, Career } from 'app/shared/model/career.model';
import { CareerService } from './career.service';

@Component({
  selector: 'jhi-career-update',
  templateUrl: './career-update.component.html',
})
export class CareerUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(100)]],
  });

  constructor(
    protected careerService: CareerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ career }) => {
      this.updateForm(career);
    });
  }

  updateForm(career: ICareer): void {
    this.editForm.patchValue({
      id: career.id,
      name: career.name,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const career = this.createFromForm();
    if (career.id !== undefined) {
      this.subscribeToSaveResponse(this.careerService.update(career));
    } else {
      this.subscribeToSaveResponse(this.careerService.create(career));
    }
  }

  private createFromForm(): ICareer {
    return {
      ...new Career(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICareer>>): void {
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
