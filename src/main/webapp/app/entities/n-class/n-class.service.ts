import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { INClass } from 'app/shared/model/n-class.model';

type EntityResponseType = HttpResponse<INClass>;
type EntityArrayResponseType = HttpResponse<INClass[]>;

@Injectable({ providedIn: 'root' })
export class NClassService {
  public resourceUrl = SERVER_API_URL + 'api/n-classes';

  constructor(protected http: HttpClient) {}

  create(nClass: INClass): Observable<EntityResponseType> {
    return this.http.post<INClass>(this.resourceUrl, nClass, { observe: 'response' });
  }

  update(nClass: INClass): Observable<EntityResponseType> {
    return this.http.put<INClass>(this.resourceUrl, nClass, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INClass>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(careerId?: number, req?: any): Observable<EntityArrayResponseType> {
   const options = createRequestOption(req);
   return this.http.get<INClass[]>(`${this.resourceUrl}/career?careerId=${careerId}`, { params: options, observe: 'response' });
  }
  
  list(careerId: number): Observable<INClass[]> {
    return this.http.get<INClass[]>(`${this.resourceUrl}/list/career?careerId=${careerId}`);
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
