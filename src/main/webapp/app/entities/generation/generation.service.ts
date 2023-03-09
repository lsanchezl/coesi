import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGeneration } from 'app/shared/model/generation.model';

type EntityResponseType = HttpResponse<IGeneration>;
type EntityArrayResponseType = HttpResponse<IGeneration[]>;

@Injectable({ providedIn: 'root' })
export class GenerationService {
  public resourceUrl = SERVER_API_URL + 'api/generations';

  constructor(protected http: HttpClient) {}

  create(generation: IGeneration): Observable<EntityResponseType> {
    return this.http.post<IGeneration>(this.resourceUrl, generation, { observe: 'response' });
  }

  update(generation: IGeneration): Observable<EntityResponseType> {
    return this.http.put<IGeneration>(this.resourceUrl, generation, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGeneration>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGeneration[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  findAllByPrincipalLogin(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGeneration[]>(this.resourceUrl + "/principal", { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
