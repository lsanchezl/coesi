import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AccountService } from 'app/core/auth/account.service';
import { IEvaluationCriteria } from 'app/shared/model/evaluation-criteria.model';
import { StatusGroup } from 'app/shared/constants/status-group.constants';

import { EvaluationCriteriaService } from './evaluation-criteria.service';
import { EvaluationCriteriaDeleteDialogComponent } from './evaluation-criteria-delete-dialog.component';

@Component({
  selector: 'jhi-evaluation-criteria',
  templateUrl: './evaluation-criteria.component.html',
})
export class EvaluationCriteriaComponent implements OnInit, OnDestroy {
  evaluationCriteria?: IEvaluationCriteria[];
  eventSubscriber?: Subscription;
  nGroupId!: number;
  isEditable = false;

  constructor(
    protected evaluationCriteriaService: EvaluationCriteriaService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    private accountService: AccountService
  ) {}

  loadPage(): void {
    const snapshot = this.activatedRoute.snapshot;
    this.activatedRoute.data.subscribe(({ nGroup }) => {
        this.nGroupId = nGroup.id;
        this.isEditable = this.accountService.hasAnyAuthority(['ROLE_PRINCIPAL', 'ROLE_SECRETARY']) 
                                || nGroup?.statusGroup?.keyStatus === StatusGroup.IN_PROGRESS;
                                });

    if(!this.nGroupId){
        // when the user deletes one evaluation criteria, loadPage is called again
        this.nGroupId = snapshot.params['nGroupId'];
    }

    this.evaluationCriteriaService
      .query(this.nGroupId)
      .subscribe(
        (res: HttpResponse<IEvaluationCriteria[]>) => this.onSuccess(res.body),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.loadPage();
    this.registerChangeInEvaluationCriteria();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEvaluationCriteria): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEvaluationCriteria(): void {
    this.eventSubscriber = this.eventManager.subscribe('evaluationCriteriaListModification', () => this.loadPage());
  }

  delete(evaluationCriteria: IEvaluationCriteria): void {
    const modalRef = this.modalService.open(EvaluationCriteriaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.evaluationCriteria = evaluationCriteria;
  }

  protected onSuccess(data: IEvaluationCriteria[] | null): void {
    this.evaluationCriteria = data || [];
  }

  protected onError(): void {
    alert('Error al cargar los criterios de evaluación.');
  }
}
