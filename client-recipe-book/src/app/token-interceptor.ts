import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { Observable, BehaviorSubject, throwError } from 'rxjs';
import { AuthService } from './auth/shared/services/auth.service';
import { catchError, switchMap, take, filter } from 'rxjs/operators';
import { LoginResponse } from './auth/shared/models/login-response.payload';

const TOKEN_HEADER_KEY = 'Authorization';

@Injectable({
    providedIn: 'root'
})
export class TokenInterceptor implements HttpInterceptor {

    private isTokenRefreshing = false;

    private refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject(null);
    
    constructor(public authService: AuthService) { }

    intercept(request: HttpRequest<any>, next: HttpHandler):
        Observable<HttpEvent<any>> {
        // if (request.url.indexOf('refresh') !== -1 || 
        //     request.url.indexOf('login') !== -1 || 
        //     request.url.indexOf('contact') !== -1) {
        //     return next.handle(request);
        // }
        const jwtToken = this.authService.getJwtToken();
        if (jwtToken) {
          request = this.addToken(request, jwtToken);
        }
        return next.handle(request).pipe(catchError(error => {
            if (error instanceof HttpErrorResponse && error.status === 401) {
                //const tokenExpired = new Date().getTime() >= this.authService.getExpirationTime();
                return this.handleAuthErrors(request, next); 
            } else {
                return throwError(error);
            }
        }));
    }

    private addToken(request: HttpRequest<any>, jwtToken: string) {
        return request.clone({
            headers: request.headers.set(TOKEN_HEADER_KEY, 'Bearer ' + jwtToken)
        });
    }

    private handleAuthErrors(request: HttpRequest<any>, next: HttpHandler)
        : Observable<HttpEvent<any>> {
        if (!this.isTokenRefreshing) {
            this.isTokenRefreshing = true;
            this.refreshTokenSubject.next(null);
            return this.authService.refreshToken().pipe(
                switchMap((refreshTokenResponse: LoginResponse) => {
                    this.isTokenRefreshing = false;
                    this.refreshTokenSubject.next(refreshTokenResponse.authenticationToken);
                    return next.handle(this.addToken(request, refreshTokenResponse.authenticationToken));
                })
            )    
        } else {
            return this.refreshTokenSubject.pipe(
                filter(token => token !== null),
                take(1),
                switchMap(jwt => {
                    return next.handle(this.addToken(request, jwt));
                })
            );
        }
    }
}