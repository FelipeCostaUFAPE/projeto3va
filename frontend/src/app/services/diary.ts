import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DiaryService {
  private readonly API_URL = `${environment.apiUrl}/api/diario`;

  constructor(private http: HttpClient) { }

  getEntries(palavraChave?: string, data?: string): Observable<any> {
    let params = new HttpParams();
    if (palavraChave) {
      params = params.set('palavraChave', palavraChave);
    }
    if (data) {
      params = params.set('data', data);
    }
    return this.http.get(this.API_URL, { params });
  }

  createEntry(data: any): Observable<any> {
    return this.http.post(this.API_URL, data);
  }
}