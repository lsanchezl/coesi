import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoesiSharedModule } from 'app/shared/shared.module';
import { AttendanceComponent } from './attendance.component';
import { AttendanceUpdateComponent } from './attendance-update.component';
import { attendanceRoute } from './attendance.route';

@NgModule({
  imports: [CoesiSharedModule, RouterModule.forChild(attendanceRoute)],
  declarations: [AttendanceComponent, AttendanceUpdateComponent],
})
export class CoesiAttendanceModule {}
