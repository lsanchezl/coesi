import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIncome } from 'app/shared/model/income.model';
import { IncomeService } from './income.service';

@Component({
  templateUrl: './income-delete-dialog.component.html',
})
export class IncomeDeleteDialogComponent {
  income?: IIncome;

  constructor(protected incomeService: IncomeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.incomeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('incomeListModification');
      this.activeModal.close();
    });
  }
}
