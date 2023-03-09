import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoesiSharedModule } from 'app/shared/shared.module';
import { ConceptIncomeComponent } from './concept-income.component';
import { ConceptIncomeDetailComponent } from './concept-income-detail.component';
import { ConceptIncomeUpdateComponent } from './concept-income-update.component';
import { ConceptIncomeDeleteDialogComponent } from './concept-income-delete-dialog.component';
import { conceptIncomeRoute } from './concept-income.route';

@NgModule({
  imports: [CoesiSharedModule, RouterModule.forChild(conceptIncomeRoute)],
  declarations: [ConceptIncomeComponent, ConceptIncomeDetailComponent, ConceptIncomeUpdateComponent, ConceptIncomeDeleteDialogComponent],
  entryComponents: [ConceptIncomeDeleteDialogComponent],
})
export class CoesiConceptIncomeModule {}
