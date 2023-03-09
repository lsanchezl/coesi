import { Routes } from '@angular/router';
import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { StudentEvaluationComponent } from './student-evaluation.component';

export const studentEvaluationRoute: Routes = [
  {
    path: 'n-group/:nGroupId',
    component: StudentEvaluationComponent,
    data: {
      authorities: [Authority.PRINCIPAL, Authority.SECRETARY, Authority.TEACHER],
      defaultSort: 'id,asc',
      pageTitle: 'coesiApp.studentEvaluation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
