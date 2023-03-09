import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IModality, Modality } from 'app/shared/model/modality.model';
import { ModalityService } from './modality.service';
import { ModalityComponent } from './modality.component';
import { ModalityDetailComponent } from './modality-detail.component';
import { ModalityUpdateComponent } from './modality-update.component';

@Injectable({ providedIn: 'root' })
export class ModalityResolve implements Resolve<IModality> {
  constructor(private service: ModalityService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IModality> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((modality: HttpResponse<Modality>) => {
          if (modality.body) {
            return of(modality.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Modality());
  }
}

export const modalityRoute: Routes = [
  {
    path: '',
    component: ModalityComponent,
    data: {
      authorities: [Authority.ADMIN],
      defaultSort: 'id,asc',
      pageTitle: 'coesiApp.modality.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ModalityDetailComponent,
    resolve: {
      modality: ModalityResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'coesiApp.modality.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ModalityUpdateComponent,
    resolve: {
      modality: ModalityResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'coesiApp.modality.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ModalityUpdateComponent,
    resolve: {
      modality: ModalityResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'coesiApp.modality.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
