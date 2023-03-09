import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeAttendance } from 'app/shared/model/type-attendance.model';

type EntityResponseType = HttpResponse<ITypeAttendance>;
type EntityArrayResponseType = HttpResponse<ITypeAttendance[]>;

@Injectable({ providedIn: 'root' })
export class TypeAttendanceService {
  public resourceUrl = SERVER_API_URL + 'api/type-attendances';

  constructor(protected http: HttpClient) {}

  create(typeAttendance: ITypeAttendance): Observable<EntityResponseType> {
    return this.http.post<ITypeAttendance>(this.resourceUrl, typeAttendance, { observe: 'response' });
  }

  update(typeAttendance: ITypeAttendance): Observable<EntityResponseType> {
    return this.http.put<ITypeAttendance>(this.resourceUrl, typeAttendance, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITypeAttendance>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypeAttendance[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
