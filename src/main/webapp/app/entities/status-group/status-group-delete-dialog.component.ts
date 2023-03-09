import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStatusGroup } from 'app/shared/model/status-group.model';
import { StatusGroupService } from './status-group.service';

@Component({
  templateUrl: './status-group-delete-dialog.component.html',
})
export class StatusGroupDeleteDialogComponent {
  statusGroup?: IStatusGroup;

  constructor(
    protected statusGroupService: StatusGroupService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.statusGroupService.delete(id).subscribe(() => {
      this.eventManager.broadcast('statusGroupListModification');
      this.activeModal.close();
    });
  }
}
