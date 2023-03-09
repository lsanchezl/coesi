import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoesiSharedModule } from 'app/shared/shared.module';
import { StudentEvaluationComponent } from './student-evaluation.component';
import { studentEvaluationRoute } from './student-evaluation.route';

@NgModule({
  imports: [CoesiSharedModule, RouterModule.forChild(studentEvaluationRoute)],
  declarations: [
    StudentEvaluationComponent,
  ],
})
export class CoesiStudentEvaluationModule {}
