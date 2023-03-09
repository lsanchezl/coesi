import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoesiSharedModule } from 'app/shared/shared.module';
import { NClassComponent } from './n-class.component';
import { NClassDetailComponent } from './n-class-detail.component';
import { NClassUpdateComponent } from './n-class-update.component';
import { NClassDeleteDialogComponent } from './n-class-delete-dialog.component';
import { nClassRoute } from './n-class.route';

@NgModule({
  imports: [CoesiSharedModule, RouterModule.forChild(nClassRoute)],
  declarations: [NClassComponent, NClassDetailComponent, NClassUpdateComponent, NClassDeleteDialogComponent],
  entryComponents: [NClassDeleteDialogComponent],
})
export class CoesiNClassModule {}
