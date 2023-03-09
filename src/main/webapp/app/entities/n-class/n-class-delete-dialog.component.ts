import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INClass } from 'app/shared/model/n-class.model';
import { NClassService } from './n-class.service';

@Component({
  templateUrl: './n-class-delete-dialog.component.html',
})
export class NClassDeleteDialogComponent {
  nClass?: INClass;

  constructor(protected nClassService: NClassService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.nClassService.delete(id).subscribe(() => {
      this.eventManager.broadcast('nClassListModification');
      this.activeModal.close();
    });
  }
}
