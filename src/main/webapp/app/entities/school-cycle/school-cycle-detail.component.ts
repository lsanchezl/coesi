import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISchoolCycle } from 'app/shared/model/school-cycle.model';

@Component({
  selector: 'jhi-school-cycle-detail',
  templateUrl: './school-cycle-detail.component.html',
})
export class SchoolCycleDetailComponent implements OnInit {
  schoolCycle: ISchoolCycle | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ schoolCycle }) => (this.schoolCycle = schoolCycle));
  }

  previousState(): void {
    window.history.back();
  }
}
