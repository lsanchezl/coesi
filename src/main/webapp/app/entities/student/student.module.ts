import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoesiSharedModule } from 'app/shared/shared.module';
import { StudentComponent } from './student.component';
import { StudentDetailComponent } from './student-detail.component';
import { StudentUpdateComponent } from './student-update.component';
import { StudentDeleteDialogComponent } from './student-delete-dialog.component';
import { studentRoute } from './student.route';

@NgModule({
  imports: [CoesiSharedModule, RouterModule.forChild(studentRoute)],
  declarations: [StudentComponent, StudentDetailComponent, StudentUpdateComponent, StudentDeleteDialogComponent],
  entryComponents: [StudentDeleteDialogComponent],
})
export class CoesiStudentModule {}
