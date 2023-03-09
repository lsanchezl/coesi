import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IIncome } from 'app/shared/model/income.model';
import { IncomeService } from './income.service';
import { IncomeDeleteDialogComponent } from './income-delete-dialog.component';

@Component({
  selector: 'jhi-income',
  templateUrl: './income.component.html',
})
export class IncomeComponent implements OnInit, OnDestroy {
  incomes?: IIncome[];
  eventSubscriber?: Subscription;

  constructor(protected incomeService: IncomeService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.incomeService.query().subscribe((res: HttpResponse<IIncome[]>) => (this.incomes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInIncomes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IIncome): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInIncomes(): void {
    this.eventSubscriber = this.eventManager.subscribe('incomeListModification', () => this.loadAll());
  }

  delete(income: IIncome): void {
    const modalRef = this.modalService.open(IncomeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.income = income;
  }
}
