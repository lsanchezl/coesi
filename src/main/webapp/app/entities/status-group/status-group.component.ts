import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IStatusGroup } from 'app/shared/model/status-group.model';
import { StatusGroupService } from './status-group.service';
import { StatusGroupDeleteDialogComponent } from './status-group-delete-dialog.component';

@Component({
  selector: 'jhi-status-group',
  templateUrl: './status-group.component.html',
})
export class StatusGroupComponent implements OnInit, OnDestroy {
  statusGroups?: IStatusGroup[];
  eventSubscriber?: Subscription;

  constructor(
    protected statusGroupService: StatusGroupService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.statusGroupService.query().subscribe((res: HttpResponse<IStatusGroup[]>) => (this.statusGroups = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInStatusGroups();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IStatusGroup): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInStatusGroups(): void {
    this.eventSubscriber = this.eventManager.subscribe('statusGroupListModification', () => this.loadAll());
  }

  delete(statusGroup: IStatusGroup): void {
    const modalRef = this.modalService.open(StatusGroupDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.statusGroup = statusGroup;
  }
}
