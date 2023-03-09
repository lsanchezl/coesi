import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { TranslateService } from '@ngx-translate/core';
import { INGroup } from 'app/shared/model/n-group.model';
import { IStatusGroup } from 'app/shared/model/status-group.model';
import { NGroupService } from './n-group.service';
import { Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';

@Component({
  templateUrl: './n-group-change-status-dialog.component.html',
})
export class NGroupChangeStatusDialogComponent implements OnInit {
  nGroup?: INGroup;
  newStatusGroup?: IStatusGroup;
  currentStatusGroupLabel = '';
  newStatusGroupLabel = '';
  

  constructor(protected nGroupService: NGroupService, public activeModal: NgbActiveModal,
                    protected eventManager: JhiEventManager, protected translate: TranslateService) {}

  ngOnInit(): void {
       this.currentStatusGroupLabel = this.translate.instant("coesiApp.statusGroup." + this.nGroup?.statusGroup?.keyStatus);
       this.newStatusGroupLabel = this.translate.instant("coesiApp.statusGroup." + this.newStatusGroup?.keyStatus);
  }
  
  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmChangeStatus(): void {
      if (this.nGroup){
        this.nGroup.statusGroup = this.newStatusGroup;
        this.subscribeToSaveResponse(this.nGroupService.update(this.nGroup));
      }
  }
  
  protected subscribeToSaveResponse(result: Observable<HttpResponse<INGroup>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.activeModal.close();
    window.location.reload();
  }

  protected onSaveError(): void {
    alert("Ocurrió un error al guardar el cambio de estatus, consulte al administrador.")
  }
}
