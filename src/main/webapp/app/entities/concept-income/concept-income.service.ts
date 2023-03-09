import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IConceptIncome } from 'app/shared/model/concept-income.model';

type EntityResponseType = HttpResponse<IConceptIncome>;
type EntityArrayResponseType = HttpResponse<IConceptIncome[]>;

@Injectable({ providedIn: 'root' })
export class ConceptIncomeService {
  public resourceUrl = SERVER_API_URL + 'api/concept-incomes';

  constructor(protected http: HttpClient) {}

  create(conceptIncome: IConceptIncome): Observable<EntityResponseType> {
    return this.http.post<IConceptIncome>(this.resourceUrl, conceptIncome, { observe: 'response' });
  }

  update(conceptIncome: IConceptIncome): Observable<EntityResponseType> {
    return this.http.put<IConceptIncome>(this.resourceUrl, conceptIncome, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IConceptIncome>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConceptIncome[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
