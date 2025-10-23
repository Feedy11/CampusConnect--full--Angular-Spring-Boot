import { environment } from '../../../environments/environment';
import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap, catchError, throwError } from 'rxjs';
import { User } from '../models/user.model';


interface LoginModelResponse {
  token: string;
  user: User;
}
@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private currentUser = signal<User | null>(null);
  private apiURL = `${environment.apiUrlAuth}/auth`; // utilise environment
  private isLoggedIn = false;

  constructor(private http: HttpClient) {
    // Vérifie si un token existe déjà au démarrage
    const token = localStorage.getItem('token');
    this.isLoggedIn = !!token;
  }

  login(email: string, password: string): Observable<LoginModelResponse> {
    return this.http.post<LoginModelResponse>(`${this.apiURL}/login`, { email, password }).pipe(
      tap(res => {
        this.isLoggedIn = true;
        localStorage.setItem('token', res.token);
        this.currentUser.set(res.user);
      }),
      catchError(err => {
        console.error(' Error login:', err);
        this.isLoggedIn = false;
        return throwError(() => err);
      })
    );
  }

  register(fullName: string, email: string, password: string, major: string, skills: string): Observable<any> {
    return this.http.post(`${this.apiURL}/register`, { fullName, email, password, major, skills });
  }

  logout(): void {
    this.isLoggedIn = false;
    localStorage.removeItem('token');
    this.currentUser.set(null);
  }

  isAuthenticated(): boolean {
    return this.isLoggedIn;
  }
  me(): Observable<User> {
    return this.http.get<User>(`${this.apiURL}/me`);
  }

  getCurrentUser(): User | null {
    return this.currentUser();
  }
}
