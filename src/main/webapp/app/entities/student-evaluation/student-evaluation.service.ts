import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IStudentEvaluation } from 'app/shared/model/student-evaluation.model';

type EntityResponseType = HttpResponse<IStudentEvaluation>;
type EntityResponseTypeMatrix = HttpResponse<string[][]>;
type EntityArrayResponseType = HttpResponse<IStudentEvaluation[]>;

@Injectable({ providedIn: 'root' })
export class StudentEvaluationService {
  public resourceUrl = SERVER_API_URL + 'api/student-evaluations';

  constructor(protected http: HttpClient) {}

  create(studentEvaluation: IStudentEvaluation): Observable<EntityResponseType> {
    return this.http.post<IStudentEvaluation>(this.resourceUrl, studentEvaluation, { observe: 'response' });
  }

  update(matrix: string[][]): Observable<HttpResponse<{}>> {
    return this.http.put<string[][]>(this.resourceUrl, matrix, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStudentEvaluation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
 
  findByGroupMatrix(nGroupId: number): Observable<EntityResponseTypeMatrix> {
    return this.http.get<string[][]>(`${this.resourceUrl}/n-group/${nGroupId}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStudentEvaluation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

}
