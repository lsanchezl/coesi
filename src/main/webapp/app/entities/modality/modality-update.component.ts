import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IModality, Modality } from 'app/shared/model/modality.model';
import { ModalityService } from './modality.service';

@Component({
  selector: 'jhi-modality-update',
  templateUrl: './modality-update.component.html',
})
export class ModalityUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(100)]],
    description: [null, [Validators.required, Validators.maxLength(500)]],
  });

  constructor(protected modalityService: ModalityService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ modality }) => {
      this.updateForm(modality);
    });
  }

  updateForm(modality: IModality): void {
    this.editForm.patchValue({
      id: modality.id,
      name: modality.name,
      description: modality.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const modality = this.createFromForm();
    if (modality.id !== undefined) {
      this.subscribeToSaveResponse(this.modalityService.update(modality));
    } else {
      this.subscribeToSaveResponse(this.modalityService.create(modality));
    }
  }

  private createFromForm(): IModality {
    return {
      ...new Modality(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IModality>>): void {
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
