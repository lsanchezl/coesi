import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeAttendance } from 'app/shared/model/type-attendance.model';
import { TypeAttendanceService } from './type-attendance.service';

@Component({
  templateUrl: './type-attendance-delete-dialog.component.html',
})
export class TypeAttendanceDeleteDialogComponent {
  typeAttendance?: ITypeAttendance;

  constructor(
    protected typeAttendanceService: TypeAttendanceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeAttendanceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeAttendanceListModification');
      this.activeModal.close();
    });
  }
}
