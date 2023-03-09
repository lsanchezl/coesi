import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { INGroup, NGroup } from 'app/shared/model/n-group.model';
import { NGroupService } from './n-group.service';
import { NGroupComponent } from './n-group.component';
import { NGroupDetailComponent } from './n-group-detail.component';
import { NGroupUpdateComponent } from './n-group-update.component';

@Injectable({ providedIn: 'root' })
export class NGroupResolve implements Resolve<INGroup> {
  constructor(private service: NGroupService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INGroup> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((nGroup: HttpResponse<NGroup>) => {
          if (nGroup.body) {
            return of(nGroup.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new NGroup());
  }
}

export const nGroupRoute: Routes = [
  {
    path: '',
    component: NGroupComponent,
    data: {
      authorities: [Authority.PRINCIPAL, Authority.SECRETARY, Authority.TEACHER, Authority.STUDENT],
      defaultSort: 'id,asc',
      pageTitle: 'coesiApp.nGroup.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NGroupDetailComponent,
    resolve: {
      nGroup: NGroupResolve,
    },
    data: {
      authorities: [Authority.PRINCIPAL, Authority.SECRETARY, Authority.TEACHER],
      pageTitle: 'coesiApp.nGroup.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NGroupUpdateComponent,
    resolve: {
      nGroup: NGroupResolve,
    },
    data: {
      authorities: [Authority.PRINCIPAL, Authority.SECRETARY],
      pageTitle: 'coesiApp.nGroup.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NGroupUpdateComponent,
    resolve: {
      nGroup: NGroupResolve,
    },
    data: {
      authorities: [Authority.PRINCIPAL, Authority.SECRETARY],
      pageTitle: 'coesiApp.nGroup.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
