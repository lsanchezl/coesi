import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IIncome } from 'app/shared/model/income.model';

type EntityResponseType = HttpResponse<IIncome>;
type EntityArrayResponseType = HttpResponse<IIncome[]>;

@Injectable({ providedIn: 'root' })
export class IncomeService {
  public resourceUrl = SERVER_API_URL + 'api/incomes';

  constructor(protected http: HttpClient) {}

  create(income: IIncome): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(income);
    return this.http
      .post<IIncome>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(income: IIncome): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(income);
    return this.http
      .put<IIncome>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IIncome>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IIncome[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(income: IIncome): IIncome {
    const copy: IIncome = Object.assign({}, income, {
      applicationDate: income.applicationDate && income.applicationDate.isValid() ? income.applicationDate.toJSON() : undefined,
      creationDate: income.creationDate && income.creationDate.isValid() ? income.creationDate.toJSON() : undefined,
      modificationDate: income.modificationDate && income.modificationDate.isValid() ? income.modificationDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.applicationDate = res.body.applicationDate ? moment(res.body.applicationDate) : undefined;
      res.body.creationDate = res.body.creationDate ? moment(res.body.creationDate) : undefined;
      res.body.modificationDate = res.body.modificationDate ? moment(res.body.modificationDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((income: IIncome) => {
        income.applicationDate = income.applicationDate ? moment(income.applicationDate) : undefined;
        income.creationDate = income.creationDate ? moment(income.creationDate) : undefined;
        income.modificationDate = income.modificationDate ? moment(income.modificationDate) : undefined;
      });
    }
    return res;
  }
}
