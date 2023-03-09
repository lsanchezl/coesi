import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAttendance } from 'app/shared/model/attendance.model';
import { IAttendanceMatrix } from 'app/shared/model/attendance-matrix.model';

type EntityResponseType = HttpResponse<IAttendance>;
type EntityResponseTypeMatrix = HttpResponse<IAttendanceMatrix>;
type EntityArrayResponseType = HttpResponse<IAttendance[]>;

@Injectable({ providedIn: 'root' })
export class AttendanceService {
  public resourceUrl = SERVER_API_URL + 'api/attendances';

  constructor(protected http: HttpClient) {}

  create(attendance: IAttendance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(attendance);
    return this.http
      .post<IAttendance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  createByGroup(groupId: number, dateAttendance: string): Observable<EntityArrayResponseType> {
    const copy = {id: groupId, valueLocalDate: dateAttendance};
    return this.http
      .post<IAttendance[]>(`${this.resourceUrl}/n-group`, copy, { observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  update(attendanceId: number, typeAttendanceId: number | undefined): Observable<EntityResponseType> {
    const copy = {id: attendanceId, value: typeAttendanceId};
    return this.http
      .put<IAttendance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAttendance>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAttendance[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  findByGroupMatrix(nGroupId: number): Observable<EntityResponseTypeMatrix> {
    return this.http.get<IAttendanceMatrix>(`${this.resourceUrl}/n-group/${nGroupId}`, { observe: 'response' });
  }

  protected convertDateFromClient(attendance: IAttendance): IAttendance {
    const copy: IAttendance = Object.assign({}, attendance, {
      dateAttendance:
        attendance.dateAttendance && attendance.dateAttendance.isValid() ? attendance.dateAttendance.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateAttendance = res.body.dateAttendance ? moment(res.body.dateAttendance) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((attendance: IAttendance) => {
        attendance.dateAttendance = attendance.dateAttendance ? moment(attendance.dateAttendance) : undefined;
      });
    }
    return res;
  }
}
