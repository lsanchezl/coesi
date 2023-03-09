import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISchoolCycle } from 'app/shared/model/school-cycle.model';

type EntityResponseType = HttpResponse<ISchoolCycle>;
type EntityArrayResponseType = HttpResponse<ISchoolCycle[]>;

@Injectable({ providedIn: 'root' })
export class SchoolCycleService {
  public resourceUrl = SERVER_API_URL + 'api/school-cycles';

  constructor(protected http: HttpClient) {}

  create(schoolCycle: ISchoolCycle): Observable<EntityResponseType> {
    return this.http.post<ISchoolCycle>(this.resourceUrl, schoolCycle, { observe: 'response' });
  }

  update(schoolCycle: ISchoolCycle): Observable<EntityResponseType> {
    return this.http.put<ISchoolCycle>(this.resourceUrl, schoolCycle, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISchoolCycle>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISchoolCycle[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  findAllByPrincipalLogin(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISchoolCycle[]>(this.resourceUrl + "/principal", { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
