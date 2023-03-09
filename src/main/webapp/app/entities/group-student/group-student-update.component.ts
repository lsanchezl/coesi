import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IGroupStudent, GroupStudent } from 'app/shared/model/group-student.model';
import { GroupStudentService } from './group-student.service';
import { IStudent } from 'app/shared/model/student.model';
import { StudentService } from 'app/entities/student/student.service';
import { INGroup } from 'app/shared/model/n-group.model';

type SelectableEntity = IStudent | INGroup;

@Component({
  selector: 'jhi-group-student-update',
  templateUrl: './group-student-update.component.html',
})
export class GroupStudentUpdateComponent implements OnInit {
  isSaving = false;
  students: IStudent[] = [];

  editForm = this.fb.group({
    id: [],
    student: [null, Validators.required],
    nGroup: [null, Validators.required],
  });

  constructor(
    protected groupStudentService: GroupStudentService,
    protected studentService: StudentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ groupStudent }) => {
      this.updateForm(groupStudent);
      this.studentService.list().subscribe(students => {
        this.students = students;
      });

    });
  }

  updateForm(groupStudent: IGroupStudent): void {
    this.editForm.patchValue({
      id: groupStudent.id,
      student: groupStudent.student,
      nGroup: groupStudent.nGroup,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const groupStudent = this.createFromForm();
    if (groupStudent.id !== undefined) {
      this.subscribeToSaveResponse(this.groupStudentService.update(groupStudent));
    } else {
      this.subscribeToSaveResponse(this.groupStudentService.create(groupStudent));
    }
  }

  private createFromForm(): IGroupStudent {
    return {
      ...new GroupStudent(),
      id: this.editForm.get(['id'])!.value,
      student: this.editForm.get(['student'])!.value,
      nGroup: this.editForm.get(['nGroup'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGroupStudent>>): void {
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
