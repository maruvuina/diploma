import { Injectable, Output, EventEmitter } from '@angular/core';
import { SignupRequestPayload } from '../models/signup-request.payload';
import { Observable, throwError } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { LocalStorageService } from 'ngx-webstorage';
import { LoginRequestPayload } from '../models/login-request.payload';
import { LoginResponse } from '../models/login-response.payload';
import { map, tap } from 'rxjs/operators';

const API_AUTH = 'http://localhost:8080/api/auth/';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  idUser: number;

  @Output() loggedIn: EventEmitter<boolean> = new EventEmitter();

  @Output() username: EventEmitter<string> = new EventEmitter();

  constructor(private httpClient: HttpClient, 
  	private localStorage: LocalStorageService) { }

  signup(signupRequestPayload: SignupRequestPayload): Observable<boolean> {
    return this.httpClient.post<boolean>(API_AUTH + 'signup', signupRequestPayload);
  }

  isUserActive(username: string): Observable<boolean> {
    return this.httpClient.get<boolean>(API_AUTH + 'active/?username=' + username);
  }

  login(loginRequestPayload: LoginRequestPayload): Observable<boolean> {
    return this.httpClient.post<LoginResponse>(API_AUTH + 'login', loginRequestPayload)
      .pipe(map(data => {
        this.localStorage.store('authenticationToken', data.authenticationToken);
        this.localStorage.store('username', data.username);
        this.localStorage.store('refreshToken', data.refreshToken);
        this.localStorage.store('expiresAt', data.expiresAt);
        this.localStorage.store('idUser', data.idUser);
        this.localStorage.store('roles', JSON.stringify(data.roles));
        this.loggedIn.emit(true);
        this.username.emit(data.username);
        return true;
      }));
  }

  refreshToken() {
    let refreshTokenPayload = {
      refreshToken: this.getRefreshToken(),
      username: this.getUserName()
    };
    return this.httpClient.post<LoginResponse>(API_AUTH + 'refresh/token',
      refreshTokenPayload)
      .pipe(tap(response => {
      	this.localStorage.clear('authenticationToken');
        this.localStorage.clear('expiresAt');
        this.localStorage.store('authenticationToken', response.authenticationToken);
        this.localStorage.store('expiresAt', response.expiresAt);
      }));
  }

  getRoles() {
    return this.localStorage.retrieve('roles');
  }

  isActive() {
    return this.localStorage.retrieve('isActive');
  }

  getIdUser() {
    return this.localStorage.retrieve('idUser');
  }

  getJwtToken() {
    return this.localStorage.retrieve('authenticationToken');
  }

  getRefreshToken() {
    return this.localStorage.retrieve('refreshToken');
  }

  getUserName() {
    return this.localStorage.retrieve('username');
  }

  getExpirationTime() {
    return this.localStorage.retrieve('expiresAt');
  }

  logout() {
    let refreshTokenPayload = {
      refreshToken: this.getRefreshToken(),
      username: this.getUserName()
    };
    this.httpClient.post(API_AUTH + 'logout', refreshTokenPayload,
      { responseType: 'text' })
      .subscribe(data => {
        console.log("logout---> " + data);
      }, error => {
        throwError(error);
      })
    this.localStorage.clear('authenticationToken');
    this.localStorage.clear('username');
    this.localStorage.clear('refreshToken');
    this.localStorage.clear('expiresAt');
    this.localStorage.clear('idUser');
    this.localStorage.clear('roles');
  }

  isLoggedIn(): boolean {
    return this.getJwtToken() != null;
  }
}
