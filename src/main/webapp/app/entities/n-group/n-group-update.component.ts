import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { CareerService } from 'app/entities/career/career.service';
import { INGroup, NGroup } from 'app/shared/model/n-group.model';
import { NGroupService } from './n-group.service';
import { ISchoolCycle } from 'app/shared/model/school-cycle.model';
import { SchoolCycleService } from 'app/entities/school-cycle/school-cycle.service';
import { ITeacher } from 'app/shared/model/teacher.model';
import { TeacherService } from 'app/entities/teacher/teacher.service';
import { INClass } from 'app/shared/model/n-class.model';
import { NClassService } from 'app/entities/n-class/n-class.service';
import { IRoom } from 'app/shared/model/room.model';
import { RoomService } from 'app/entities/room/room.service';
import { IModality } from 'app/shared/model/modality.model';
import { ModalityService } from 'app/entities/modality/modality.service';
import { Career } from 'app/shared/model/career.model';
import { ICareer } from 'app/shared/model/career.model';

type SelectableEntity = ISchoolCycle | ITeacher | INClass | IRoom | IModality;

@Component({
  selector: 'jhi-n-group-update',
  templateUrl: './n-group-update.component.html',
})
export class NGroupUpdateComponent implements OnInit {
  isSaving = false;
  schoolcycles: ISchoolCycle[] = [];
  teachers: ITeacher[] = [];
  nclasses: INClass[] = [];
  rooms: IRoom[] = [];
  modalities: IModality[] = [];
  startDateDp: any;
  endDateDp: any;
  selectedCareer: Career = new Career();
  careers: ICareer[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    startDate: [],
    endDate: [],
    schoolCycle: [null, Validators.required],
    teacher: [null, Validators.required],
    nClass: [null, Validators.required],
    room: [null, Validators.required],
    modality: [null, Validators.required],
    statusGroup: [],
    career: []
  });

  constructor(
    protected nGroupService: NGroupService,
    protected schoolCycleService: SchoolCycleService,
    protected teacherService: TeacherService,
    protected nClassService: NClassService,
    protected roomService: RoomService,
    protected modalityService: ModalityService,
    protected activatedRoute: ActivatedRoute,
    protected careerService: CareerService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nGroup }) => {
    this.updateForm(nGroup);
    
    this.schoolCycleService.query().subscribe((resSC: HttpResponse<ISchoolCycle[]>) => {
        this.schoolcycles = resSC.body || [];
    });
    
    this.roomService.query().subscribe((resR: HttpResponse<IRoom[]>) => {
        this.rooms= resR.body || [];
     });

    this.teacherService.list().subscribe(teachers => {
        this.teachers = teachers;
      });

    this.modalityService.query().subscribe((res: HttpResponse<IModality[]>) => (this.modalities = res.body || []));

    this.careerService.query().subscribe((res: HttpResponse<ICareer[]>) => {
        this.careers = res.body || [];
        // cargamos las materias de la primer carrera que venga como parámetro
        this.changeCareer(this.careers[0]);
     });

    });
  }

  updateForm(nGroup: INGroup): void {
    this.editForm.patchValue({
      id: nGroup.id,
      name: nGroup.name,
      startDate: nGroup.startDate,
      endDate: nGroup.endDate,
      schoolCycle: nGroup.schoolCycle,
      teacher: nGroup.teacher,
      nClass: nGroup.nClass,
      room: nGroup.room,
      modality: nGroup.modality,
      statusGroup: nGroup.statusGroup
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nGroup = this.createFromForm();
    if (this.editForm.get(['startDate'])!.value > this.editForm.get(['endDate'])!.value){
        alert("La fecha final debe ser mayor que la inicial");
        return;
    }

    if (nGroup.id !== undefined) {
      this.subscribeToSaveResponse(this.nGroupService.update(nGroup));
    } else {
      this.subscribeToSaveResponse(this.nGroupService.create(nGroup));
    }
  }

  private createFromForm(): INGroup {
    return {
      ...new NGroup(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      startDate: this.editForm.get(['startDate'])!.value,
      endDate: this.editForm.get(['endDate'])!.value,
      schoolCycle: this.editForm.get(['schoolCycle'])!.value,
      teacher: this.editForm.get(['teacher'])!.value,
      nClass: this.editForm.get(['nClass'])!.value,
      room: this.editForm.get(['room'])!.value,
      modality: this.editForm.get(['modality'])!.value,
      statusGroup: this.editForm.get(['statusGroup'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INGroup>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  changeCareer(newCareer: Career): void {
    this.selectedCareer = newCareer;

    let careerId = 0;

    if (this.selectedCareer.id){
        careerId = this.selectedCareer.id;
    }

    this.nClassService.list(careerId).subscribe(nclasses => {
        this.nclasses = nclasses;
      });
   }
}
