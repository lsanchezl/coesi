import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoesiSharedModule } from 'app/shared/shared.module';
import { IncomeComponent } from './income.component';
import { IncomeDetailComponent } from './income-detail.component';
import { IncomeUpdateComponent } from './income-update.component';
import { IncomeDeleteDialogComponent } from './income-delete-dialog.component';
import { incomeRoute } from './income.route';

@NgModule({
  imports: [CoesiSharedModule, RouterModule.forChild(incomeRoute)],
  declarations: [IncomeComponent, IncomeDetailComponent, IncomeUpdateComponent, IncomeDeleteDialogComponent],
  entryComponents: [IncomeDeleteDialogComponent],
})
export class CoesiIncomeModule {}
