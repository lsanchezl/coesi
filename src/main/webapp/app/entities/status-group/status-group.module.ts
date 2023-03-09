import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoesiSharedModule } from 'app/shared/shared.module';
import { StatusGroupComponent } from './status-group.component';
import { StatusGroupDetailComponent } from './status-group-detail.component';
import { StatusGroupUpdateComponent } from './status-group-update.component';
import { StatusGroupDeleteDialogComponent } from './status-group-delete-dialog.component';
import { statusGroupRoute } from './status-group.route';

@NgModule({
  imports: [CoesiSharedModule, RouterModule.forChild(statusGroupRoute)],
  declarations: [StatusGroupComponent, StatusGroupDetailComponent, StatusGroupUpdateComponent, StatusGroupDeleteDialogComponent],
  entryComponents: [StatusGroupDeleteDialogComponent],
})
export class CoesiStatusGroupModule {}
