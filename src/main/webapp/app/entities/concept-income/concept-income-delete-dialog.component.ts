import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConceptIncome } from 'app/shared/model/concept-income.model';
import { ConceptIncomeService } from './concept-income.service';

@Component({
  templateUrl: './concept-income-delete-dialog.component.html',
})
export class ConceptIncomeDeleteDialogComponent {
  conceptIncome?: IConceptIncome;

  constructor(
    protected conceptIncomeService: ConceptIncomeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.conceptIncomeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('conceptIncomeListModification');
      this.activeModal.close();
    });
  }
}
