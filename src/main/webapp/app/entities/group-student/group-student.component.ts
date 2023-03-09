import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AccountService } from 'app/core/auth/account.service';
import { IGroupStudent } from 'app/shared/model/group-student.model';
import { Observable } from 'rxjs';
import { GroupStudentService } from './group-student.service';
import { GroupStudentDeleteDialogComponent } from './group-student-delete-dialog.component';

@Component({
  selector: 'jhi-group-student',
  templateUrl: './group-student.component.html',
})
export class GroupStudentComponent implements OnInit, OnDestroy {
  groupStudents?: IGroupStudent[];
  eventSubscriber?: Subscription;
  nGroupId!: number;
  editOpportunity2 = false;
  oldValue:any = 0;

  constructor(
    protected groupStudentService: GroupStudentService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    private accountService: AccountService
  ) {}

  loadPage(): void {
    const snapshot = this.activatedRoute.snapshot;

    if(!this.nGroupId){
        // when the user deletes one evaluation criteria, loadPage is called again
        this.nGroupId = snapshot.params['nGroupId'];
    }
    this.groupStudentService
      .query(this.nGroupId)
      .subscribe(
        (res: HttpResponse<IGroupStudent[]>) => this.onSuccess(res.body),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.editOpportunity2 = this.accountService.hasAnyAuthority(['ROLE_PRINCIPAL', 'ROLE_SECRETARY']);
    this.loadPage();
    this.registerChangeInGroupStudents();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IGroupStudent): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInGroupStudents(): void {
    this.eventSubscriber = this.eventManager.subscribe('groupStudentListModification', () => this.loadPage());
  }

  delete(groupStudent: IGroupStudent): void {
    const modalRef = this.modalService.open(GroupStudentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.groupStudent = groupStudent;
  }

  protected onSuccess(data: IGroupStudent[] | null): void {
    this.groupStudents = data || [];
  }

  protected onError(): void {
    alert('Error al cargar la información');
  }
  
  onFocusScore(groupStudent: IGroupStudent) : void {
    this.oldValue = groupStudent.opportunity2;
  }

  onBlurScore(groupStudent: IGroupStudent) : void {
    if (this.oldValue !== groupStudent.opportunity2){
        this.subscribeToSaveResponse(this.groupStudentService.update(groupStudent));
    }
  }
  
   protected subscribeToSaveResponse(result: Observable<HttpResponse<IGroupStudent>>): void {
    result.subscribe(
      () => {},
      () => alert('Error al guardar')
    );
  }
}
