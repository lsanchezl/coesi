import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISchoolCycle, SchoolCycle } from 'app/shared/model/school-cycle.model';
import { SchoolCycleService } from './school-cycle.service';
import { SchoolCycleComponent } from './school-cycle.component';
import { SchoolCycleDetailComponent } from './school-cycle-detail.component';
import { SchoolCycleUpdateComponent } from './school-cycle-update.component';

@Injectable({ providedIn: 'root' })
export class SchoolCycleResolve implements Resolve<ISchoolCycle> {
  constructor(private service: SchoolCycleService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISchoolCycle> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((schoolCycle: HttpResponse<SchoolCycle>) => {
          if (schoolCycle.body) {
            return of(schoolCycle.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SchoolCycle());
  }
}

export const schoolCycleRoute: Routes = [
  {
    path: '',
    component: SchoolCycleComponent,
    data: {
      authorities: [Authority.PRINCIPAL],
      defaultSort: 'id,asc',
      pageTitle: 'coesiApp.schoolCycle.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SchoolCycleDetailComponent,
    resolve: {
      schoolCycle: SchoolCycleResolve,
    },
    data: {
      authorities: [Authority.PRINCIPAL],
      pageTitle: 'coesiApp.schoolCycle.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SchoolCycleUpdateComponent,
    resolve: {
      schoolCycle: SchoolCycleResolve,
    },
    data: {
      authorities: [Authority.PRINCIPAL],
      pageTitle: 'coesiApp.schoolCycle.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SchoolCycleUpdateComponent,
    resolve: {
      schoolCycle: SchoolCycleResolve,
    },
    data: {
      authorities: [Authority.PRINCIPAL],
      pageTitle: 'coesiApp.schoolCycle.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
