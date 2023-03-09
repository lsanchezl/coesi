import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IStatusGroup } from 'app/shared/model/status-group.model';

type EntityResponseType = HttpResponse<IStatusGroup>;
type EntityArrayResponseType = HttpResponse<IStatusGroup[]>;

@Injectable({ providedIn: 'root' })
export class StatusGroupService {
  public resourceUrl = SERVER_API_URL + 'api/status-groups';

  constructor(protected http: HttpClient) {}

  create(statusGroup: IStatusGroup): Observable<EntityResponseType> {
    return this.http.post<IStatusGroup>(this.resourceUrl, statusGroup, { observe: 'response' });
  }

  update(statusGroup: IStatusGroup): Observable<EntityResponseType> {
    return this.http.put<IStatusGroup>(this.resourceUrl, statusGroup, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStatusGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStatusGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
