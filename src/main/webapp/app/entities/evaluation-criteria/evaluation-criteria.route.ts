import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEvaluationCriteria, EvaluationCriteria } from 'app/shared/model/evaluation-criteria.model';
import { EvaluationCriteriaService } from './evaluation-criteria.service';
import { EvaluationCriteriaComponent } from './evaluation-criteria.component';
import { EvaluationCriteriaUpdateComponent } from './evaluation-criteria-update.component';
import { NGroup } from 'app/shared/model/n-group.model';
import { NGroupService } from 'app/entities/n-group/n-group.service';

@Injectable({ providedIn: 'root' })
export class EvaluationCriteriaResolve implements Resolve<IEvaluationCriteria> {
  constructor(private service: EvaluationCriteriaService, private nGroupService: NGroupService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEvaluationCriteria> | Observable<never> {
    const id = route.params['id'];
    const nGroupId = route.params['nGroupId'];

    if (id) {
      return this.service.find(id).pipe(
        flatMap((evaluationCriteria: HttpResponse<EvaluationCriteria>) => {
          if (evaluationCriteria.body) {
            return of(evaluationCriteria.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }

    return this.nGroupService.find(nGroupId).pipe(
        flatMap((nGroup: HttpResponse<NGroup>) => {
          if (nGroup.body) {
            const evaluationCriteria = new EvaluationCriteria();
            evaluationCriteria.nGroup= nGroup.body;
            return of(evaluationCriteria);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
  }
}

export const evaluationCriteriaRoute: Routes = [
  {
    path: 'n-group/:nGroupId',
    component: EvaluationCriteriaComponent,
    data: {
      authorities: [Authority.PRINCIPAL, Authority.SECRETARY, Authority.TEACHER, Authority.STUDENT],
      defaultSort: 'id,asc',
      pageTitle: 'coesiApp.evaluationCriteria.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'n-group/:nGroupId/new',
    component: EvaluationCriteriaUpdateComponent,
    resolve: {
      evaluationCriteria: EvaluationCriteriaResolve,
    },
    data: {
      authorities: [Authority.PRINCIPAL, Authority.SECRETARY, Authority.TEACHER],
      pageTitle: 'coesiApp.evaluationCriteria.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EvaluationCriteriaUpdateComponent,
    resolve: {
      evaluationCriteria: EvaluationCriteriaResolve,
    },
    data: {
      authorities: [Authority.PRINCIPAL, Authority.SECRETARY, Authority.TEACHER],
      pageTitle: 'coesiApp.evaluationCriteria.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
