import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { IGroupStudent } from 'app/shared/model/group-student.model';
import { IStudentStatsGroup } from 'app/shared/model/student-stats-group.model';

type EntityResponseType = HttpResponse<IGroupStudent>;
type EntityStatsResponseType = HttpResponse<IStudentStatsGroup>;
type EntityArrayResponseType = HttpResponse<IGroupStudent[]>;

@Injectable({ providedIn: 'root' })
export class GroupStudentService {
  public resourceUrl = SERVER_API_URL + 'api/group-students';

  constructor(protected http: HttpClient) {}

  create(groupStudent: IGroupStudent): Observable<EntityResponseType> {
    return this.http.post<IGroupStudent>(this.resourceUrl, groupStudent, { observe: 'response' });
  }

  update(groupStudent: IGroupStudent): Observable<EntityResponseType> {
    return this.http.put<IGroupStudent>(this.resourceUrl, groupStudent, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGroupStudent>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findStudentStatsGroup(nGroupId: number): Observable<EntityStatsResponseType> {
    return this.http.get<IStudentStatsGroup>(`${this.resourceUrl}/student/stats/n-group/${nGroupId}`, { observe: 'response' });
  }

  query(nGroupId?: number): Observable<EntityArrayResponseType> {
    return this.http.get<IGroupStudent[]>(`${this.resourceUrl}/n-group/${nGroupId}`, { observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
