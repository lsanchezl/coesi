import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoesiSharedModule } from 'app/shared/shared.module';
import { GenerationComponent } from './generation.component';
import { GenerationDetailComponent } from './generation-detail.component';
import { GenerationUpdateComponent } from './generation-update.component';
import { GenerationDeleteDialogComponent } from './generation-delete-dialog.component';
import { generationRoute } from './generation.route';

@NgModule({
  imports: [CoesiSharedModule, RouterModule.forChild(generationRoute)],
  declarations: [GenerationComponent, GenerationDetailComponent, GenerationUpdateComponent, GenerationDeleteDialogComponent],
  entryComponents: [GenerationDeleteDialogComponent],
})
export class CoesiGenerationModule {}
