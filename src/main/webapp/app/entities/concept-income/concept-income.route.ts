import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IConceptIncome, ConceptIncome } from 'app/shared/model/concept-income.model';
import { ConceptIncomeService } from './concept-income.service';
import { ConceptIncomeComponent } from './concept-income.component';
import { ConceptIncomeDetailComponent } from './concept-income-detail.component';
import { ConceptIncomeUpdateComponent } from './concept-income-update.component';

@Injectable({ providedIn: 'root' })
export class ConceptIncomeResolve implements Resolve<IConceptIncome> {
  constructor(private service: ConceptIncomeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConceptIncome> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((conceptIncome: HttpResponse<ConceptIncome>) => {
          if (conceptIncome.body) {
            return of(conceptIncome.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ConceptIncome());
  }
}

export const conceptIncomeRoute: Routes = [
  {
    path: '',
    component: ConceptIncomeComponent,
    data: {
      authorities: [Authority.PRINCIPAL],
      pageTitle: 'coesiApp.conceptIncome.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConceptIncomeDetailComponent,
    resolve: {
      conceptIncome: ConceptIncomeResolve,
    },
    data: {
      authorities: [Authority.PRINCIPAL],
      pageTitle: 'coesiApp.conceptIncome.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConceptIncomeUpdateComponent,
    resolve: {
      conceptIncome: ConceptIncomeResolve,
    },
    data: {
      authorities: [Authority.PRINCIPAL],
      pageTitle: 'coesiApp.conceptIncome.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConceptIncomeUpdateComponent,
    resolve: {
      conceptIncome: ConceptIncomeResolve,
    },
    data: {
      authorities: [Authority.PRINCIPAL],
      pageTitle: 'coesiApp.conceptIncome.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
