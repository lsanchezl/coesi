import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { INGroup } from 'app/shared/model/n-group.model';

type EntityResponseType = HttpResponse<INGroup>;
type EntityArrayResponseType = HttpResponse<INGroup[]>;

@Injectable({ providedIn: 'root' })
export class NGroupService {
  public resourceUrl = SERVER_API_URL + 'api/n-groups';

  constructor(protected http: HttpClient) {}

  create(nGroup: INGroup): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(nGroup);
    return this.http
      .post<INGroup>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(nGroup: INGroup): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(nGroup);
    return this.http
      .put<INGroup>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }
  
  updateStatus(nGroup: INGroup | null, newStatus: string): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(nGroup);
    return this.http
      .patch<INGroup>(`${this.resourceUrl}/${nGroup?.id}/status/${newStatus}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number | undefined): Observable<EntityResponseType> {
    return this.http
      .get<INGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(careerId?: number, schoolCycleId?: number, roomId?: number,req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<INGroup[]>(`${this.resourceUrl}/filtered?careerId=${careerId}&schoolCycleId=${schoolCycleId}&roomId=${roomId}`, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(nGroup: INGroup | null): INGroup {
    const copy: INGroup = Object.assign({}, nGroup, {
      startDate: nGroup?.startDate && nGroup?.startDate.isValid() ? nGroup?.startDate.format(DATE_FORMAT) : undefined,
      endDate: nGroup?.endDate && nGroup?.endDate.isValid() ? nGroup?.endDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate ? moment(res.body.startDate) : undefined;
      res.body.endDate = res.body.endDate ? moment(res.body.endDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((nGroup: INGroup) => {
        nGroup.startDate = nGroup.startDate ? moment(nGroup.startDate) : undefined;
        nGroup.endDate = nGroup.endDate ? moment(nGroup.endDate) : undefined;
      });
    }
    return res;
  }
}
