import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IStudent, Student } from 'app/shared/model/student.model';
import { StudentService } from './student.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IGeneration } from 'app/shared/model/generation.model';
import { GenerationService } from 'app/entities/generation/generation.service';
import { ICareer } from 'app/shared/model/career.model';
import { CareerService } from 'app/entities/career/career.service';

type SelectableEntity = IUser | IGeneration | ICareer;

@Component({
  selector: 'jhi-student-update',
  templateUrl: './student-update.component.html',
})
export class StudentUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  generations: IGeneration[] = [];
  careers: ICareer[] = [];

  editForm = this.fb.group({
    id: [],
    tuition: [null, [Validators.required, Validators.maxLength(100)]],
    graduationSermon: [],
    user: [null, Validators.required],
    generation: [null, Validators.required],
    career: [null, Validators.required],
  });

  constructor(
    protected studentService: StudentService,
    protected userService: UserService,
    protected generationService: GenerationService,
    protected careerService: CareerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ student }) => {
      this.updateForm(student);
      this.userService.listNoStudents().subscribe(users => {
        this.users = users;
      });

      this.generationService.query().subscribe((res: HttpResponse<IGeneration[]>) => (this.generations = res.body || []));

      this.careerService.query().subscribe((res: HttpResponse<ICareer[]>) => {
        this.careers = res.body || [];
        if(!student.id){
            if (this.careers.length > 0) {
                this.editForm.patchValue({
                    career: this.careers[0] 
            });
            }
        }
      });
    });
  }

  updateForm(student: IStudent): void {
    this.editForm.patchValue({
      id: student.id,
      tuition: student.tuition,
      graduationSermon: student.graduationSermon,
      user: student.user,
      generation: student.generation,
      career: student.career,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const student = this.createFromForm();
    if (student.id !== undefined) {
      this.subscribeToSaveResponse(this.studentService.update(student));
    } else {
      this.subscribeToSaveResponse(this.studentService.create(student));
    }
  }

  private createFromForm(): IStudent {
    return {
      ...new Student(),
      id: this.editForm.get(['id'])!.value,
      tuition: this.editForm.get(['tuition'])!.value,
      graduationSermon: this.editForm.get(['graduationSermon'])!.value,
      user: this.editForm.get(['user'])!.value,
      generation: this.editForm.get(['generation'])!.value,
      career: this.editForm.get(['career'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStudent>>): void {
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
}
