import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeAttendance, TypeAttendance } from 'app/shared/model/type-attendance.model';
import { TypeAttendanceService } from './type-attendance.service';

@Component({
  selector: 'jhi-type-attendance-update',
  templateUrl: './type-attendance-update.component.html',
})
export class TypeAttendanceUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(100)]],
    description: [null, [Validators.required, Validators.maxLength(500)]],
  });

  constructor(protected typeAttendanceService: TypeAttendanceService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeAttendance }) => {
      this.updateForm(typeAttendance);
    });
  }

  updateForm(typeAttendance: ITypeAttendance): void {
    this.editForm.patchValue({
      id: typeAttendance.id,
      name: typeAttendance.name,
      description: typeAttendance.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeAttendance = this.createFromForm();
    if (typeAttendance.id !== undefined) {
      this.subscribeToSaveResponse(this.typeAttendanceService.update(typeAttendance));
    } else {
      this.subscribeToSaveResponse(this.typeAttendanceService.create(typeAttendance));
    }
  }

  private createFromForm(): ITypeAttendance {
    return {
      ...new TypeAttendance(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeAttendance>>): void {
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
