import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGroupStudent, GroupStudent } from 'app/shared/model/group-student.model';
import { GroupStudentService } from './group-student.service';
import { GroupStudentComponent } from './group-student.component';
import { GroupStudentRoleStudentComponent } from './group-student-role-student.component';
import { GroupStudentUpdateComponent } from './group-student-update.component';
import { NGroup } from 'app/shared/model/n-group.model';
import { NGroupService } from 'app/entities/n-group/n-group.service';

@Injectable({ providedIn: 'root' })
export class GroupStudentResolve implements Resolve<IGroupStudent> {
  constructor(private service: GroupStudentService, private nGroupService: NGroupService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGroupStudent> | Observable<never> {
    const id = route.params['id'];
    const nGroupId = route.params['nGroupId'];

    if (id) {
      return this.service.find(id).pipe(
        flatMap((groupStudent: HttpResponse<GroupStudent>) => {
          if (groupStudent.body) {
            return of(groupStudent.body);
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
            const groupStudent = new GroupStudent();
            groupStudent.nGroup= nGroup.body;
            return of(groupStudent);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
  }
}

export const groupStudentRoute: Routes = [
  {
    path: 'role-student/n-group/:nGroupId',
    component: GroupStudentRoleStudentComponent,
    data: {
      authorities: [Authority.STUDENT],
      pageTitle: 'coesiApp.groupStudent.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'n-group/:nGroupId',
    component: GroupStudentComponent,
    data: {
      authorities: [Authority.PRINCIPAL, Authority.SECRETARY, Authority.TEACHER, Authority.STUDENT],
      defaultSort: 'id,asc',
      pageTitle: 'coesiApp.groupStudent.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'n-group/:nGroupId/new',
    component: GroupStudentUpdateComponent,
    resolve: {
      groupStudent: GroupStudentResolve,
    },
    data: {
      authorities: [Authority.PRINCIPAL, Authority.SECRETARY],
      pageTitle: 'coesiApp.groupStudent.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
