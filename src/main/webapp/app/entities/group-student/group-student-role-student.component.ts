import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { GroupStudentService } from './group-student.service';
import { IStudentStatsGroup } from 'app/shared/model/student-stats-group.model';

@Component({
  selector: 'jhi-group-student-role-student',
  templateUrl: './group-student-role-student.component.html',
})
export class GroupStudentRoleStudentComponent implements OnInit {
  eventSubscriber?: Subscription;
  nGroupId!: number;
  matrixEvaluation!: string[][];
  headersAttendance: string[] = [];
  dataAttendance: any;

  constructor(
    protected groupStudentService: GroupStudentService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager
  ) {}

  ngOnInit(): void {
    const snapshot = this.activatedRoute.snapshot;
    this.nGroupId = snapshot.params['nGroupId'];
    this.groupStudentService
    .findStudentStatsGroup(this.nGroupId)
    .subscribe(
      (res: HttpResponse<IStudentStatsGroup>) => this.onSuccess(res.body),
      () => this.onError()
    );
  }

  protected onSuccess(studentStatsGroup: IStudentStatsGroup | null): void {
    if (studentStatsGroup?.evaluation) {
        this.matrixEvaluation = studentStatsGroup.evaluation;
        this.headersAttendance = studentStatsGroup.attendanceMatrix?.headers || [];
        this.dataAttendance = studentStatsGroup.attendanceMatrix?.data;
    }
  }

  protected onError(): void {
    alert('Error al consultar');
  }
}
