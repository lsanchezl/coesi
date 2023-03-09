import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPerformanceIndicator } from 'app/shared/model/performance-indicator.model';
import { PerformanceIndicatorService } from './performance-indicator.service';

@Component({
  templateUrl: './performance-indicator-delete-dialog.component.html',
})
export class PerformanceIndicatorDeleteDialogComponent {
  performanceIndicator?: IPerformanceIndicator;

  constructor(
    protected performanceIndicatorService: PerformanceIndicatorService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.performanceIndicatorService.delete(id).subscribe(() => {
      this.eventManager.broadcast('performanceIndicatorListModification');
      this.activeModal.close();
    });
  }
}
