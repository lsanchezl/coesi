import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { INClass, NClass } from 'app/shared/model/n-class.model';
import { NClassService } from './n-class.service';
import { NClassComponent } from './n-class.component';
import { NClassDetailComponent } from './n-class-detail.component';
import { NClassUpdateComponent } from './n-class-update.component';
import { Career } from 'app/shared/model/career.model';
import { CareerService } from 'app/entities/career/career.service';


@Injectable({ providedIn: 'root' })
export class NClassResolve implements Resolve<INClass> {
  constructor(private service: NClassService, private careerService: CareerService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INClass> | Observable<never> {
    const id = route.params['id'];
    const careerId = route.params['careerId'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((nClass: HttpResponse<NClass>) => {
          if (nClass.body) {
            return of(nClass.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    } else {
        return this.careerService.find(careerId).pipe(
            flatMap((career: HttpResponse<Career>) => {
              if (career.body) {
                const nClass = new NClass();
                nClass.career = career.body;
                return of(nClass);
              } else {
                this.router.navigate(['404']);
                return EMPTY;
              }
            })
          );
    }
  }
}

export const nClassRoute: Routes = [
  {
    path: 'career/:careerId',
    component: NClassComponent,
    data: {
      authorities: [Authority.PRINCIPAL, Authority.SECRETARY],
      defaultSort: 'id,asc',
      pageTitle: 'coesiApp.nClass.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NClassDetailComponent,
    resolve: {
      nClass: NClassResolve,
    },
    data: {
      authorities: [Authority.PRINCIPAL, Authority.SECRETARY],
      pageTitle: 'coesiApp.nClass.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'career/:careerId/new',
    component: NClassUpdateComponent,
    resolve: {
      nClass: NClassResolve,
    },
    data: {
      authorities: [Authority.PRINCIPAL, Authority.SECRETARY],
      pageTitle: 'coesiApp.nClass.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NClassUpdateComponent,
    resolve: {
      nClass: NClassResolve,
    },
    data: {
      authorities: [Authority.PRINCIPAL, Authority.SECRETARY],
      pageTitle: 'coesiApp.nClass.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
