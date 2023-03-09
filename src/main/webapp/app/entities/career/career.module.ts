import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoesiSharedModule } from 'app/shared/shared.module';
import { CareerComponent } from './career.component';
import { CareerDetailComponent } from './career-detail.component';
import { CareerUpdateComponent } from './career-update.component';
import { CareerDeleteDialogComponent } from './career-delete-dialog.component';
import { careerRoute } from './career.route';

@NgModule({
  imports: [CoesiSharedModule, RouterModule.forChild(careerRoute)],
  declarations: [CareerComponent, CareerDetailComponent, CareerUpdateComponent, CareerDeleteDialogComponent],
  entryComponents: [CareerDeleteDialogComponent],
})
export class CoesiCareerModule {}
