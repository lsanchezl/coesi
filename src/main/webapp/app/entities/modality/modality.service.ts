import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IModality } from 'app/shared/model/modality.model';

type EntityResponseType = HttpResponse<IModality>;
type EntityArrayResponseType = HttpResponse<IModality[]>;

@Injectable({ providedIn: 'root' })
export class ModalityService {
  public resourceUrl = SERVER_API_URL + 'api/modalities';

  constructor(protected http: HttpClient) {}

  create(modality: IModality): Observable<EntityResponseType> {
    return this.http.post<IModality>(this.resourceUrl, modality, { observe: 'response' });
  }

  update(modality: IModality): Observable<EntityResponseType> {
    return this.http.put<IModality>(this.resourceUrl, modality, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IModality>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IModality[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
