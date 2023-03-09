import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICareer } from 'app/shared/model/career.model';
import { CareerService } from './career.service';

@Component({
  templateUrl: './career-delete-dialog.component.html',
})
export class CareerDeleteDialogComponent {
  career?: ICareer;

  constructor(protected careerService: CareerService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.careerService.delete(id).subscribe(() => {
      this.eventManager.broadcast('careerListModification');
      this.activeModal.close();
    });
  }
}
