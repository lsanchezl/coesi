import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INClass } from 'app/shared/model/n-class.model';

@Component({
  selector: 'jhi-n-class-detail',
  templateUrl: './n-class-detail.component.html',
})
export class NClassDetailComponent implements OnInit {
  nClass: INClass | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nClass }) => (this.nClass = nClass));
  }

  previousState(): void {
    window.history.back();
  }
}
