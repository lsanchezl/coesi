import { Routes } from '@angular/router';
import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { AttendanceComponent } from './attendance.component';
import { AttendanceUpdateComponent } from './attendance-update.component';

export const attendanceRoute: Routes = [
  {
    path: 'n-group/:nGroupId',
    component: AttendanceComponent,
    data: {
      authorities: [Authority.PRINCIPAL, Authority.SECRETARY, Authority.TEACHER],
      defaultSort: 'id,asc',
      pageTitle: 'coesiApp.attendance.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'n-group/:nGroupId/new',
    component: AttendanceUpdateComponent,
    data: {
      authorities: [Authority.PRINCIPAL, Authority.SECRETARY, Authority.TEACHER],
      pageTitle: 'coesiApp.attendance.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
