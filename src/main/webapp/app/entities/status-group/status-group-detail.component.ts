import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStatusGroup } from 'app/shared/model/status-group.model';

@Component({
  selector: 'jhi-status-group-detail',
  templateUrl: './status-group-detail.component.html',
})
export class StatusGroupDetailComponent implements OnInit {
  statusGroup: IStatusGroup | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ statusGroup }) => (this.statusGroup = statusGroup));
  }

  previousState(): void {
    window.history.back();
  }
}
