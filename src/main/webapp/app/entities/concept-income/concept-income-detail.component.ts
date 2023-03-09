import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConceptIncome } from 'app/shared/model/concept-income.model';

@Component({
  selector: 'jhi-concept-income-detail',
  templateUrl: './concept-income-detail.component.html',
})
export class ConceptIncomeDetailComponent implements OnInit {
  conceptIncome: IConceptIncome | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conceptIncome }) => (this.conceptIncome = conceptIncome));
  }

  previousState(): void {
    window.history.back();
  }
}
