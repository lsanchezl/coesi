import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INGroup } from 'app/shared/model/n-group.model';
import { NGroupService } from './n-group.service';

@Component({
  templateUrl: './n-group-delete-dialog.component.html',
})
export class NGroupDeleteDialogComponent {
  nGroup?: INGroup;

  constructor(protected nGroupService: NGroupService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.nGroupService.delete(id).subscribe(() => {
      this.eventManager.broadcast('nGroupListModification');
      this.activeModal.close();
    });
  }
}
