import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEvaluationCriteria } from 'app/shared/model/evaluation-criteria.model';
import { EvaluationCriteriaService } from './evaluation-criteria.service';

@Component({
  templateUrl: './evaluation-criteria-delete-dialog.component.html',
})
export class EvaluationCriteriaDeleteDialogComponent {
  evaluationCriteria?: IEvaluationCriteria;

  constructor(
    protected evaluationCriteriaService: EvaluationCriteriaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.evaluationCriteriaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('evaluationCriteriaListModification');
      this.activeModal.close();
    });
  }
}
