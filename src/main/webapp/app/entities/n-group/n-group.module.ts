import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { EvaluationCriteriaComponent } from '../evaluation-criteria/evaluation-criteria.component';
import { CoesiSharedModule } from 'app/shared/shared.module';
import { NGroupComponent } from './n-group.component';
import { NGroupDetailComponent } from './n-group-detail.component';
import { NGroupUpdateComponent } from './n-group-update.component';
import { NGroupDeleteDialogComponent } from './n-group-delete-dialog.component';
import { NGroupChangeStatusDialogComponent } from './n-group-change-status-dialog.component';
import { nGroupRoute } from './n-group.route';

@NgModule({
  imports: [CoesiSharedModule, RouterModule.forChild(nGroupRoute)],
  declarations: [EvaluationCriteriaComponent,NGroupComponent, NGroupDetailComponent, 
                        NGroupUpdateComponent, NGroupDeleteDialogComponent, NGroupChangeStatusDialogComponent],
  entryComponents: [NGroupDeleteDialogComponent, NGroupChangeStatusDialogComponent],
})
export class CoesiNGroupModule {}
