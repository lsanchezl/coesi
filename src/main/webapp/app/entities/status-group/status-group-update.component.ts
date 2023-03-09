import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IStatusGroup, StatusGroup } from 'app/shared/model/status-group.model';
import { StatusGroupService } from './status-group.service';

@Component({
  selector: 'jhi-status-group-update',
  templateUrl: './status-group-update.component.html',
})
export class StatusGroupUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(100)]],
    keyStatus: [null, [Validators.required, Validators.maxLength(20)]],
  });

  constructor(protected statusGroupService: StatusGroupService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ statusGroup }) => {
      this.updateForm(statusGroup);
    });
  }

  updateForm(statusGroup: IStatusGroup): void {
    this.editForm.patchValue({
      id: statusGroup.id,
      name: statusGroup.name,
      keyStatus: statusGroup.keyStatus,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const statusGroup = this.createFromForm();
    if (statusGroup.id !== undefined) {
      this.subscribeToSaveResponse(this.statusGroupService.update(statusGroup));
    } else {
      this.subscribeToSaveResponse(this.statusGroupService.create(statusGroup));
    }
  }

  private createFromForm(): IStatusGroup {
    return {
      ...new StatusGroup(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      keyStatus: this.editForm.get(['keyStatus'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStatusGroup>>): void {
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
