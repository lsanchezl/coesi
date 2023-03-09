import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoesiSharedModule } from 'app/shared/shared.module';
import { PerformanceIndicatorComponent } from './performance-indicator.component';
import { PerformanceIndicatorDetailComponent } from './performance-indicator-detail.component';
import { PerformanceIndicatorUpdateComponent } from './performance-indicator-update.component';
import { PerformanceIndicatorDeleteDialogComponent } from './performance-indicator-delete-dialog.component';
import { performanceIndicatorRoute } from './performance-indicator.route';

@NgModule({
  imports: [CoesiSharedModule, RouterModule.forChild(performanceIndicatorRoute)],
  declarations: [
    PerformanceIndicatorComponent,
    PerformanceIndicatorDetailComponent,
    PerformanceIndicatorUpdateComponent,
    PerformanceIndicatorDeleteDialogComponent,
  ],
  entryComponents: [PerformanceIndicatorDeleteDialogComponent],
})
export class CoesiPerformanceIndicatorModule {}
