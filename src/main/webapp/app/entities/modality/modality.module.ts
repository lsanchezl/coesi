import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoesiSharedModule } from 'app/shared/shared.module';
import { ModalityComponent } from './modality.component';
import { ModalityDetailComponent } from './modality-detail.component';
import { ModalityUpdateComponent } from './modality-update.component';
import { ModalityDeleteDialogComponent } from './modality-delete-dialog.component';
import { modalityRoute } from './modality.route';

@NgModule({
  imports: [CoesiSharedModule, RouterModule.forChild(modalityRoute)],
  declarations: [ModalityComponent, ModalityDetailComponent, ModalityUpdateComponent, ModalityDeleteDialogComponent],
  entryComponents: [ModalityDeleteDialogComponent],
})
export class CoesiModalityModule {}
