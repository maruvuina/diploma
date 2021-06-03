import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './shared/services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  
  constructor(private authService: AuthService, private router: Router) { }
  
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): 
  Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
  	const isAuthenticated = this.authService.isLoggedIn();
    if (isAuthenticated) {
      const userRoles = this.authService.getRoles();
      let regex = "ROLE_[A-Z]+";
      let roles = Array.from(userRoles.matchAll(regex));
      if (!route.data.role || route.data.role.length === 0) {
        return true;
      }
      if (route.data.role.includes(roles)) {
        return true;
      } 
      if (roles.length > 1) {
        for (let i = 0; i < roles.length; i++) {
          if (route.data.role.includes(roles[i])) {
            return true;
          }
        }
      }
      this.router.navigateByUrl('/login');
      return false;
    }
    this.router.navigateByUrl('/login');
    return false;
  }
}
