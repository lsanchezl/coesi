import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import * as moment from 'moment';
import { IAttendance } from 'app/shared/model/attendance.model';
import { AttendanceService } from './attendance.service';
import { INGroup, NGroup} from 'app/shared/model/n-group.model';
import { NGroupService } from 'app/entities/n-group/n-group.service';

@Component({
  selector: 'jhi-attendance-update',
  templateUrl: './attendance-update.component.html',
})
export class AttendanceUpdateComponent implements OnInit {
  isSaving = false;
  dateAttendanceDp: any;
  nGroupId!: number;
  currentNGroup: INGroup = new NGroup();
  minDate: any;
  maxDate: any;
  
  editForm = this.fb.group({
    dateAttendance: [null, [Validators.required]],
  });

  constructor(
    protected attendanceService: AttendanceService,
    protected nGroupService: NGroupService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    const snapshot = this.activatedRoute.snapshot;
    this.nGroupId = snapshot.params['nGroupId'];
    this.nGroupService.find(this.nGroupId).subscribe((res: HttpResponse<INGroup>) => {
        if (res.body) {
            this.currentNGroup = res.body;
            
            const startDate = this.currentNGroup.startDate ? moment(this.currentNGroup.startDate) : undefined;
            if (startDate) {
                this.minDate = { year: startDate.get('year'), month: startDate.get('month') + 1, day: startDate.get('date') };
            }
            
            const endDate = this.currentNGroup.endDate ? moment(this.currentNGroup.endDate) : undefined;
            if (endDate) {
                this.maxDate = { year: endDate.get('year'), month: endDate.get('month') + 1, day: endDate.get('date') };
            }
        }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dateSelected = this.editForm.get(['dateAttendance'])!.value;
    const dateFormated = dateSelected.format(DATE_FORMAT);
    this.subscribeToSaveResponse(this.attendanceService.createByGroup(this.nGroupId, dateFormated));
  }


  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAttendance[]>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

}
