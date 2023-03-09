import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPerformanceIndicator, PerformanceIndicator } from 'app/shared/model/performance-indicator.model';
import { PerformanceIndicatorService } from './performance-indicator.service';
import { PerformanceIndicatorComponent } from './performance-indicator.component';
import { PerformanceIndicatorDetailComponent } from './performance-indicator-detail.component';
import { PerformanceIndicatorUpdateComponent } from './performance-indicator-update.component';

@Injectable({ providedIn: 'root' })
export class PerformanceIndicatorResolve implements Resolve<IPerformanceIndicator> {
  constructor(private service: PerformanceIndicatorService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPerformanceIndicator> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((performanceIndicator: HttpResponse<PerformanceIndicator>) => {
          if (performanceIndicator.body) {
            return of(performanceIndicator.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PerformanceIndicator());
  }
}

export const performanceIndicatorRoute: Routes = [
  {
    path: '',
    component: PerformanceIndicatorComponent,
    data: {
      authorities: [Authority.PRINCIPAL],
      defaultSort: 'id,asc',
      pageTitle: 'coesiApp.performanceIndicator.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PerformanceIndicatorDetailComponent,
    resolve: {
      performanceIndicator: PerformanceIndicatorResolve,
    },
    data: {
      authorities: [Authority.PRINCIPAL],
      pageTitle: 'coesiApp.performanceIndicator.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PerformanceIndicatorUpdateComponent,
    resolve: {
      performanceIndicator: PerformanceIndicatorResolve,
    },
    data: {
      authorities: [Authority.PRINCIPAL],
      pageTitle: 'coesiApp.performanceIndicator.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PerformanceIndicatorUpdateComponent,
    resolve: {
      performanceIndicator: PerformanceIndicatorResolve,
    },
    data: {
      authorities: [Authority.PRINCIPAL],
      pageTitle: 'coesiApp.performanceIndicator.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
