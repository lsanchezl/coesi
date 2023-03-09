import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeAttendance } from 'app/shared/model/type-attendance.model';

@Component({
  selector: 'jhi-type-attendance-detail',
  templateUrl: './type-attendance-detail.component.html',
})
export class TypeAttendanceDetailComponent implements OnInit {
  typeAttendance: ITypeAttendance | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeAttendance }) => (this.typeAttendance = typeAttendance));
  }

  previousState(): void {
    window.history.back();
  }
}
