import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoesiSharedModule } from 'app/shared/shared.module';
import { TypeAttendanceComponent } from './type-attendance.component';
import { TypeAttendanceDetailComponent } from './type-attendance-detail.component';
import { TypeAttendanceUpdateComponent } from './type-attendance-update.component';
import { TypeAttendanceDeleteDialogComponent } from './type-attendance-delete-dialog.component';
import { typeAttendanceRoute } from './type-attendance.route';

@NgModule({
  imports: [CoesiSharedModule, RouterModule.forChild(typeAttendanceRoute)],
  declarations: [
    TypeAttendanceComponent,
    TypeAttendanceDetailComponent,
    TypeAttendanceUpdateComponent,
    TypeAttendanceDeleteDialogComponent,
  ],
  entryComponents: [TypeAttendanceDeleteDialogComponent],
})
export class CoesiTypeAttendanceModule {}
