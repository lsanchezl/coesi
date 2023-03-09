import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEvaluationCriteria } from 'app/shared/model/evaluation-criteria.model';

type EntityResponseType = HttpResponse<IEvaluationCriteria>;
type EntityArrayResponseType = HttpResponse<IEvaluationCriteria[]>;

@Injectable({ providedIn: 'root' })
export class EvaluationCriteriaService {
  public resourceUrl = SERVER_API_URL + 'api/evaluation-criteria';

  constructor(protected http: HttpClient) {}

  create(evaluationCriteria: IEvaluationCriteria): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(evaluationCriteria);
    return this.http
      .post<IEvaluationCriteria>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(evaluationCriteria: IEvaluationCriteria): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(evaluationCriteria);
    return this.http
      .put<IEvaluationCriteria>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEvaluationCriteria>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(nGroupId?: number, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEvaluationCriteria[]>(`${this.resourceUrl}/n-group?nGroupId=${nGroupId}`, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(evaluationCriteria: IEvaluationCriteria): IEvaluationCriteria {
    const copy: IEvaluationCriteria = Object.assign({}, evaluationCriteria, {
      deliveryDate:
        evaluationCriteria.deliveryDate && evaluationCriteria.deliveryDate.isValid()
          ? evaluationCriteria.deliveryDate.format(DATE_FORMAT)
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.deliveryDate = res.body.deliveryDate ? moment(res.body.deliveryDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((evaluationCriteria: IEvaluationCriteria) => {
        evaluationCriteria.deliveryDate = evaluationCriteria.deliveryDate ? moment(evaluationCriteria.deliveryDate) : undefined;
      });
    }
    return res;
  }
}
