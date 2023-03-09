import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IConceptIncome } from 'app/shared/model/concept-income.model';
import { ConceptIncomeService } from './concept-income.service';
import { ConceptIncomeDeleteDialogComponent } from './concept-income-delete-dialog.component';

@Component({
  selector: 'jhi-concept-income',
  templateUrl: './concept-income.component.html',
})
export class ConceptIncomeComponent implements OnInit, OnDestroy {
  conceptIncomes?: IConceptIncome[];
  eventSubscriber?: Subscription;

  constructor(
    protected conceptIncomeService: ConceptIncomeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.conceptIncomeService.query().subscribe((res: HttpResponse<IConceptIncome[]>) => (this.conceptIncomes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInConceptIncomes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IConceptIncome): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInConceptIncomes(): void {
    this.eventSubscriber = this.eventManager.subscribe('conceptIncomeListModification', () => this.loadAll());
  }

  delete(conceptIncome: IConceptIncome): void {
    const modalRef = this.modalService.open(ConceptIncomeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.conceptIncome = conceptIncome;
  }
}
