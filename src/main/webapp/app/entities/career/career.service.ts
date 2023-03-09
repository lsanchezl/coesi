import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICareer } from 'app/shared/model/career.model';

type EntityResponseType = HttpResponse<ICareer>;
type EntityArrayResponseType = HttpResponse<ICareer[]>;

@Injectable({ providedIn: 'root' })
export class CareerService {
  public resourceUrl = SERVER_API_URL + 'api/careers';

  constructor(protected http: HttpClient) {}

  create(career: ICareer): Observable<EntityResponseType> {
    return this.http.post<ICareer>(this.resourceUrl, career, { observe: 'response' });
  }

  update(career: ICareer): Observable<EntityResponseType> {
    return this.http.put<ICareer>(this.resourceUrl, career, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICareer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICareer[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  findAllByPrincipalLogin(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICareer[]>(this.resourceUrl + "/principal", { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
