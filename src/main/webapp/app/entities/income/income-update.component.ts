import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IIncome, Income } from 'app/shared/model/income.model';
import { IncomeService } from './income.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IConceptIncome } from 'app/shared/model/concept-income.model';
import { ConceptIncomeService } from 'app/entities/concept-income/concept-income.service';

type SelectableEntity = IUser | IConceptIncome;

@Component({
  selector: 'jhi-income-update',
  templateUrl: './income-update.component.html',
})
export class IncomeUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  conceptincomes: IConceptIncome[] = [];

  editForm = this.fb.group({
    id: [],
    amount: [null, [Validators.required]],
    comments: [null, [Validators.maxLength(500)]],
    applicationDate: [null, [Validators.required]],
    creationDate: [null],
    modificationDate: [null],
    user: [null, Validators.required],
    creationUser: [null],
    modificationUser: [null],
    conceptIncome: [null, Validators.required],
  });

  constructor(
    protected incomeService: IncomeService,
    protected userService: UserService,
    protected conceptIncomeService: ConceptIncomeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ income }) => {
      if (!income.id) {
        const today = moment().startOf('day');
        income.applicationDate = today;
        income.creationDate = today;
        income.modificationDate = today;
      }

      this.updateForm(income);

      this.userService.list().subscribe(users => {
        this.users = users;
      });

      this.conceptIncomeService.query().subscribe((res: HttpResponse<IConceptIncome[]>) => (this.conceptincomes = res.body || []));
    });
  }

  updateForm(income: IIncome): void {
    this.editForm.patchValue({
      id: income.id,
      amount: income.amount,
      comments: income.comments,
      applicationDate: income.applicationDate,
      creationDate: income.creationDate ? income.creationDate.format(DATE_TIME_FORMAT) : null,
      modificationDate: income.modificationDate ? income.modificationDate.format(DATE_TIME_FORMAT) : null,
      user: income.user,
      creationUser: income.creationUser,
      modificationUser: income.modificationUser,
      conceptIncome: income.conceptIncome,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const income = this.createFromForm();
    if (income.id !== undefined) {
      this.subscribeToSaveResponse(this.incomeService.update(income));
    } else {
      this.subscribeToSaveResponse(this.incomeService.create(income));
    }
  }

  private createFromForm(): IIncome {
    return {
      ...new Income(),
      id: this.editForm.get(['id'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      comments: this.editForm.get(['comments'])!.value,
      applicationDate: this.editForm.get(['applicationDate'])!.value,
      creationDate: this.editForm.get(['creationDate'])!.value
        ? moment(this.editForm.get(['creationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      modificationDate: this.editForm.get(['modificationDate'])!.value
        ? moment(this.editForm.get(['modificationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      user: this.editForm.get(['user'])!.value,
      creationUser: this.editForm.get(['creationUser'])!.value,
      modificationUser: this.editForm.get(['modificationUser'])!.value,
      conceptIncome: this.editForm.get(['conceptIncome'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIncome>>): void {
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
