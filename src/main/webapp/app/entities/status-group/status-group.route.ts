import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IStatusGroup, StatusGroup } from 'app/shared/model/status-group.model';
import { StatusGroupService } from './status-group.service';
import { StatusGroupComponent } from './status-group.component';
import { StatusGroupDetailComponent } from './status-group-detail.component';
import { StatusGroupUpdateComponent } from './status-group-update.component';

@Injectable({ providedIn: 'root' })
export class StatusGroupResolve implements Resolve<IStatusGroup> {
  constructor(private service: StatusGroupService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStatusGroup> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((statusGroup: HttpResponse<StatusGroup>) => {
          if (statusGroup.body) {
            return of(statusGroup.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new StatusGroup());
  }
}

export const statusGroupRoute: Routes = [
  {
    path: '',
    component: StatusGroupComponent,
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'coesiApp.statusGroup.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StatusGroupDetailComponent,
    resolve: {
      statusGroup: StatusGroupResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'coesiApp.statusGroup.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StatusGroupUpdateComponent,
    resolve: {
      statusGroup: StatusGroupResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'coesiApp.statusGroup.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StatusGroupUpdateComponent,
    resolve: {
      statusGroup: StatusGroupResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'coesiApp.statusGroup.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
