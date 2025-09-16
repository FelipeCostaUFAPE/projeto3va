import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DiaryService {
  private readonly API_URL = 'http://localhost:8080/api/diario';

  constructor(private http: HttpClient) { }

  // Busca todas as entradas do diário para o usuário logado
  getEntries(): Observable<any> {
    return this.http.get(this.API_URL);
  }

  // Cria uma nova entrada no diário
  createEntry(data: any): Observable<any> {
    return this.http.post(this.API_URL, data);
  }
}