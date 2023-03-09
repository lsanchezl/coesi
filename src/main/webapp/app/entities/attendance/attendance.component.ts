import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IAttendanceMatrix } from 'app/shared/model/attendance-matrix.model';
import { AttendanceService } from './attendance.service';
import { ITypeAttendance } from 'app/shared/model/type-attendance.model';
import { TypeAttendance } from 'app/shared/model/type-attendance.model';
import { TypeAttendanceService } from 'app/entities/type-attendance/type-attendance.service';
import { Observable } from 'rxjs';
import { IAttendance } from 'app/shared/model/attendance.model';
import { INGroup } from 'app/shared/model/n-group.model';
import { NGroupService } from '../n-group/n-group.service';
import { AccountService } from 'app/core/auth/account.service';
import { StatusGroup } from 'app/shared/constants/status-group.constants';

@Component({
  selector: 'jhi-attendance',
  templateUrl: './attendance.component.html',
})
export class AttendanceComponent implements OnInit, OnDestroy {
  eventSubscriber?: Subscription;
  nGroupId!: number;
  typeattendances: ITypeAttendance[] = [];
  headers: string[] = [];
  data: any;
  attendancesIds: any;
  selectedTA: ITypeAttendance = new TypeAttendance();
  isSaving = false;
  isEditable: any;


  constructor(
    protected attendanceService: AttendanceService,
    protected typeAttendanceService: TypeAttendanceService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected nGroupService: NGroupService,
    private accountService: AccountService
  ) {}


  ngOnInit(): void {
    const snapshot = this.activatedRoute.snapshot;
    this.nGroupId = snapshot.params['nGroupId'];
    this.typeAttendanceService.query().subscribe((res: HttpResponse<ITypeAttendance[]>) => (this.typeattendances = res.body || []));

    this.attendanceService
    .findByGroupMatrix(this.nGroupId)
    .subscribe(
      (res: HttpResponse<IAttendanceMatrix>) => this.onSuccess(res.body),
      () => this.onError()
    );
  }

 changeAttendance(newTypeAttendance: TypeAttendance, idxRow: number, idxColumn: number): void {
     this.isSaving = true;
     // Envíamos el id de la asistencia y el tipo de asistencia seleccionado
    this.subscribeToSaveResponse(this.attendanceService.update(this.attendancesIds[idxRow][idxColumn], newTypeAttendance.id));
 }
 
 protected subscribeToSaveResponse(result: Observable<HttpResponse<IAttendance>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
  
 protected onSuccess(matrix: IAttendanceMatrix | null): void {
    if (matrix) {
        this.headers = matrix.headers || [];
        this.data = matrix.data;
        this.attendancesIds = matrix.ids;
        this.nGroupService.find(this.nGroupId).subscribe((res: HttpResponse<INGroup>) => {
            const nGroup = res.body;
            this.isEditable = this.accountService.hasAnyAuthority(['ROLE_PRINCIPAL', 'ROLE_SECRETARY']) 
                                    || nGroup?.statusGroup?.keyStatus === StatusGroup.IN_PROGRESS;
        });
    }
  }

  protected onError(): void {
    alert('Error al consultar');
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }
}
