import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGroupStudent } from 'app/shared/model/group-student.model';
import { GroupStudentService } from './group-student.service';

@Component({
  templateUrl: './group-student-delete-dialog.component.html',
})
export class GroupStudentDeleteDialogComponent {
  groupStudent?: IGroupStudent;

  constructor(
    protected groupStudentService: GroupStudentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.groupStudentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('groupStudentListModification');
      this.activeModal.close();
    });
  }
}
