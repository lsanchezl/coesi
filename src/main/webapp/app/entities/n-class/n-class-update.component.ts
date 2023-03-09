import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { INClass, NClass } from 'app/shared/model/n-class.model';
import { NClassService } from './n-class.service';
import { ICareer } from 'app/shared/model/career.model';

@Component({
  selector: 'jhi-n-class-update',
  templateUrl: './n-class-update.component.html',
})
export class NClassUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(100)]],
    keyClass: [null, [Validators.maxLength(100)]],
    area: [null, [Validators.maxLength(100)]],
    division: [null, [Validators.maxLength(100)]],
    units: [],
    nOrder: [],
    career: [null, Validators.required],
  });

  constructor(
    protected nClassService: NClassService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nClass }) => {
      this.updateForm(nClass);
    });
  }

  updateForm(nClass: INClass): void {
    this.editForm.patchValue({
      id: nClass.id,
      name: nClass.name,
      keyClass: nClass.keyClass,
      area: nClass.area,
      division: nClass.division,
      units: nClass.units,
      nOrder: nClass.nOrder,
      career: nClass.career,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nClass = this.createFromForm();
    if (nClass.id !== undefined) {
      this.subscribeToSaveResponse(this.nClassService.update(nClass));
    } else {
      this.subscribeToSaveResponse(this.nClassService.create(nClass));
    }
  }

  private createFromForm(): INClass {
    return {
      ...new NClass(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      keyClass: this.editForm.get(['keyClass'])!.value,
      area: this.editForm.get(['area'])!.value,
      division: this.editForm.get(['division'])!.value,
      units: this.editForm.get(['units'])!.value,
      nOrder: this.editForm.get(['nOrder'])!.value,
      career: this.editForm.get(['career'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INClass>>): void {
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

  trackById(index: number, item: ICareer): any {
    return item.id;
  }
}
