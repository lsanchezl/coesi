import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoesiSharedModule } from 'app/shared/shared.module';
import { SchoolCycleComponent } from './school-cycle.component';
import { SchoolCycleDetailComponent } from './school-cycle-detail.component';
import { SchoolCycleUpdateComponent } from './school-cycle-update.component';
import { SchoolCycleDeleteDialogComponent } from './school-cycle-delete-dialog.component';
import { schoolCycleRoute } from './school-cycle.route';

@NgModule({
  imports: [CoesiSharedModule, RouterModule.forChild(schoolCycleRoute)],
  declarations: [SchoolCycleComponent, SchoolCycleDetailComponent, SchoolCycleUpdateComponent, SchoolCycleDeleteDialogComponent],
  entryComponents: [SchoolCycleDeleteDialogComponent],
})
export class CoesiSchoolCycleModule {}
