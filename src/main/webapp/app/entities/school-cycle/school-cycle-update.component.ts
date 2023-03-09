import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISchoolCycle, SchoolCycle } from 'app/shared/model/school-cycle.model';
import { SchoolCycleService } from './school-cycle.service';

@Component({
  selector: 'jhi-school-cycle-update',
  templateUrl: './school-cycle-update.component.html',
})
export class SchoolCycleUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(100)]],
  });

  constructor(
    protected schoolCycleService: SchoolCycleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ schoolCycle }) => {
      this.updateForm(schoolCycle);
    });
  }

  updateForm(schoolCycle: ISchoolCycle): void {
    this.editForm.patchValue({
      id: schoolCycle.id,
      name: schoolCycle.name,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const schoolCycle = this.createFromForm();
    if (schoolCycle.id !== undefined) {
      this.subscribeToSaveResponse(this.schoolCycleService.update(schoolCycle));
    } else {
      this.subscribeToSaveResponse(this.schoolCycleService.create(schoolCycle));
    }
  }

  private createFromForm(): ISchoolCycle {
    return {
      ...new SchoolCycle(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISchoolCycle>>): void {
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
