import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription} from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { StudentEvaluationService } from './student-evaluation.service';
import { AccountService } from 'app/core/auth/account.service';
import { StatusGroup } from 'app/shared/constants/status-group.constants';
import { NGroupService } from '../n-group/n-group.service';
import { INGroup } from 'app/shared/model/n-group.model';

@Component({
  selector: 'jhi-student-evaluation',
  templateUrl: './student-evaluation.component.html',
})
export class StudentEvaluationComponent implements OnInit, OnDestroy {
  eventSubscriber?: Subscription;
  nGroupId!: number;
  isSaving = false;
  matrix!: string[][];
  isEditable: any;
   
  constructor(
    protected studentEvaluationService: StudentEvaluationService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    private accountService: AccountService,
    protected nGroupService: NGroupService
  ) {}

  ngOnInit(): void {
    const snapshot = this.activatedRoute.snapshot;
    this.nGroupId = snapshot.params['nGroupId'];

    this.studentEvaluationService
    .findByGroupMatrix(this.nGroupId)
    .subscribe(
      (res: HttpResponse<string[][]>) => this.onSuccess(res.body),
      () => this.onError()
    );
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  protected onSuccess(matrix: string[][] | null): void {
    if (matrix) {
        this.matrix = matrix;
        this.nGroupService.find(this.nGroupId).subscribe((res: HttpResponse<INGroup>) => {
            const nGroup = res.body;
            this.isEditable = this.accountService.hasAnyAuthority(['ROLE_PRINCIPAL', 'ROLE_SECRETARY']) 
                                    || nGroup?.statusGroup?.keyStatus === StatusGroup.IN_PROGRESS;
        });
    }
  }

  protected onError(): void {
    alert('Error al consultar');
  }
  
  onScoreChange(idxRow: number) : void {
      let score = 0;
      
      // recorremos las calificaciones y las ponderamos para calcular el total 
      // recordar que las primeras 2 posiciones son el groupStudentId y el nombre del alumno, no hay calificación
      // la última posición no la recorremos porque tiene el total
      const idx = this.matrix[idxRow].length - 1;
      
      for (let i = 2; i < idx;  i++) {
          // el row 1 de la matrix contiene las ponderaciones
        score = score + ((parseInt(this.matrix[idxRow][i], 10) * parseInt(this.matrix[1][i], 10)) / 100);
      }
      this.matrix[idxRow][idx]=Math.round(score) + '';
  }
  
  trackByIndex(index: number): any {
    return index;
  }

    updateScores(): void {
        this.isSaving = true;
        this.studentEvaluationService.update(this.matrix).subscribe(() => {
            this.isSaving = false;
        });
    }
}
