import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { INGroup } from 'app/shared/model/n-group.model';
import { NGroupService } from './n-group.service';
import { StatusGroupService } from '../status-group/status-group.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NGroupChangeStatusDialogComponent } from './n-group-change-status-dialog.component';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

@Component({
  selector: 'jhi-n-group-detail',
  templateUrl: './n-group-detail.component.html',
})
export class NGroupDetailComponent implements OnInit, OnDestroy {
  nGroup: INGroup | null = null;
  isSaving = false;
  eventSubscriber?: Subscription;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected nGroupService: NGroupService,
    protected statusGroupService: StatusGroupService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.loadPage();
  }
  
  loadPage(): void {
      this.activatedRoute.data.subscribe(({ nGroup }) => {
                    this.nGroup = nGroup;
          });
  }

  previousState(): void {
    window.history.back();
  }
  
  changeStatus(isPrevious: boolean): void {
    const modalRef = this.modalService.open(NGroupChangeStatusDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.nGroup = this.nGroup;
    modalRef.componentInstance.newStatusGroup = isPrevious ? this.nGroup?.previousStatusGroup : this.nGroup?.nextStatusGroup;
  }
  
  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }
}
