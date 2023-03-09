import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoesiSharedModule } from 'app/shared/shared.module';
import { EvaluationCriteriaUpdateComponent } from './evaluation-criteria-update.component';
import { EvaluationCriteriaDeleteDialogComponent } from './evaluation-criteria-delete-dialog.component';
import { evaluationCriteriaRoute } from './evaluation-criteria.route';

@NgModule({
  imports: [CoesiSharedModule, RouterModule.forChild(evaluationCriteriaRoute)],
  declarations: [
    EvaluationCriteriaUpdateComponent,
    EvaluationCriteriaDeleteDialogComponent,
  ],
  entryComponents: [EvaluationCriteriaDeleteDialogComponent],
})
export class CoesiEvaluationCriteriaModule {}
