import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPerformanceIndicator } from 'app/shared/model/performance-indicator.model';

type EntityResponseType = HttpResponse<IPerformanceIndicator>;
type EntityArrayResponseType = HttpResponse<IPerformanceIndicator[]>;

@Injectable({ providedIn: 'root' })
export class PerformanceIndicatorService {
  public resourceUrl = SERVER_API_URL + 'api/performance-indicators';

  constructor(protected http: HttpClient) {}

  create(performanceIndicator: IPerformanceIndicator): Observable<EntityResponseType> {
    return this.http.post<IPerformanceIndicator>(this.resourceUrl, performanceIndicator, { observe: 'response' });
  }

  update(performanceIndicator: IPerformanceIndicator): Observable<EntityResponseType> {
    return this.http.put<IPerformanceIndicator>(this.resourceUrl, performanceIndicator, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPerformanceIndicator>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPerformanceIndicator[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  findAllByPrincipalLogin(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPerformanceIndicator[]>(this.resourceUrl + "/principal", { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
  
  list(): Observable<IPerformanceIndicator[]> {
    return this.http.get<IPerformanceIndicator[]>(`${this.resourceUrl}/list`);
  }
}
