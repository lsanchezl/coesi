import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICareer, Career } from 'app/shared/model/career.model';
import { CareerService } from './career.service';
import { CareerComponent } from './career.component';
import { CareerDetailComponent } from './career-detail.component';
import { CareerUpdateComponent } from './career-update.component';

@Injectable({ providedIn: 'root' })
export class CareerResolve implements Resolve<ICareer> {
  constructor(private service: CareerService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICareer> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((career: HttpResponse<Career>) => {
          if (career.body) {
            return of(career.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Career());
  }
}

export const careerRoute: Routes = [
  {
    path: '',
    component: CareerComponent,
    data: {
      authorities: [Authority.PRINCIPAL],
      defaultSort: 'id,asc',
      pageTitle: 'coesiApp.career.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CareerDetailComponent,
    resolve: {
      career: CareerResolve,
    },
    data: {
      authorities: [Authority.PRINCIPAL],
      pageTitle: 'coesiApp.career.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CareerUpdateComponent,
    resolve: {
      career: CareerResolve,
    },
    data: {
      authorities: [Authority.PRINCIPAL],
      pageTitle: 'coesiApp.career.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CareerUpdateComponent,
    resolve: {
      career: CareerResolve,
    },
    data: {
      authorities: [Authority.PRINCIPAL],
      pageTitle: 'coesiApp.career.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
