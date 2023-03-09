import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoesiSharedModule } from 'app/shared/shared.module';
import { GroupStudentComponent } from './group-student.component';
import { GroupStudentRoleStudentComponent } from './group-student-role-student.component';
import { GroupStudentUpdateComponent } from './group-student-update.component';
import { GroupStudentDeleteDialogComponent } from './group-student-delete-dialog.component';
import { groupStudentRoute } from './group-student.route';

@NgModule({
  imports: [CoesiSharedModule, RouterModule.forChild(groupStudentRoute)],
  declarations: [GroupStudentComponent,GroupStudentRoleStudentComponent, GroupStudentUpdateComponent,
  GroupStudentDeleteDialogComponent],
  entryComponents: [GroupStudentDeleteDialogComponent],
})
export class CoesiGroupStudentModule {}
