import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISchoolCycle } from 'app/shared/model/school-cycle.model';
import { SchoolCycleService } from './school-cycle.service';

@Component({
  templateUrl: './school-cycle-delete-dialog.component.html',
})
export class SchoolCycleDeleteDialogComponent {
  schoolCycle?: ISchoolCycle;

  constructor(
    protected schoolCycleService: SchoolCycleService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.schoolCycleService.delete(id).subscribe(() => {
      this.eventManager.broadcast('schoolCycleListModification');
      this.activeModal.close();
    });
  }
}
