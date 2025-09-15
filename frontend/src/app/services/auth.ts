import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  
  private readonly API_URL = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) { }

  // Método para chamar o endpoint de login
  login(data: any) {
    return this.http.post(`${this.API_URL}/autenticar`, data);
  }
}