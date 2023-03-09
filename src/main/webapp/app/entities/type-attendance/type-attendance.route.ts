import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeAttendance, TypeAttendance } from 'app/shared/model/type-attendance.model';
import { TypeAttendanceService } from './type-attendance.service';
import { TypeAttendanceComponent } from './type-attendance.component';
import { TypeAttendanceDetailComponent } from './type-attendance-detail.component';
import { TypeAttendanceUpdateComponent } from './type-attendance-update.component';

@Injectable({ providedIn: 'root' })
export class TypeAttendanceResolve implements Resolve<ITypeAttendance> {
  constructor(private service: TypeAttendanceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeAttendance> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeAttendance: HttpResponse<TypeAttendance>) => {
          if (typeAttendance.body) {
            return of(typeAttendance.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeAttendance());
  }
}

export const typeAttendanceRoute: Routes = [
  {
    path: '',
    component: TypeAttendanceComponent,
    data: {
      authorities: [Authority.ADMIN],
      defaultSort: 'id,asc',
      pageTitle: 'coesiApp.typeAttendance.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TypeAttendanceDetailComponent,
    resolve: {
      typeAttendance: TypeAttendanceResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'coesiApp.typeAttendance.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TypeAttendanceUpdateComponent,
    resolve: {
      typeAttendance: TypeAttendanceResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'coesiApp.typeAttendance.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TypeAttendanceUpdateComponent,
    resolve: {
      typeAttendance: TypeAttendanceResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'coesiApp.typeAttendance.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
