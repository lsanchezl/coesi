import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICareer } from 'app/shared/model/career.model';

@Component({
  selector: 'jhi-career-detail',
  templateUrl: './career-detail.component.html',
})
export class CareerDetailComponent implements OnInit {
  career: ICareer | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ career }) => (this.career = career));
  }

  previousState(): void {
    window.history.back();
  }
}
