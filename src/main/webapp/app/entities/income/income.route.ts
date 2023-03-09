import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IIncome, Income } from 'app/shared/model/income.model';
import { IncomeService } from './income.service';
import { IncomeComponent } from './income.component';
import { IncomeDetailComponent } from './income-detail.component';
import { IncomeUpdateComponent } from './income-update.component';

@Injectable({ providedIn: 'root' })
export class IncomeResolve implements Resolve<IIncome> {
  constructor(private service: IncomeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIncome> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((income: HttpResponse<Income>) => {
          if (income.body) {
            return of(income.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Income());
  }
}

export const incomeRoute: Routes = [
  {
    path: '',
    component: IncomeComponent,
    data: {
      authorities: [Authority.PRINCIPAL, Authority.TREASURER],
      pageTitle: 'coesiApp.income.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IncomeDetailComponent,
    resolve: {
      income: IncomeResolve,
    },
    data: {
      authorities: [Authority.PRINCIPAL, Authority.TREASURER],
      pageTitle: 'coesiApp.income.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IncomeUpdateComponent,
    resolve: {
      income: IncomeResolve,
    },
    data: {
      authorities: [Authority.PRINCIPAL, Authority.TREASURER],
      pageTitle: 'coesiApp.income.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IncomeUpdateComponent,
    resolve: {
      income: IncomeResolve,
    },
    data: {
      authorities: [Authority.PRINCIPAL, Authority.TREASURER],
      pageTitle: 'coesiApp.income.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
