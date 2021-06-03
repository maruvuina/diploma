import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth/shared/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit {

  isLoggedIn: boolean;

  username: string;
  
  idUser: number;

  constructor(private authService: AuthService, 
    private router: Router) { }

  ngOnInit(): void {
  	this.isLoggedIn = this.authService.isLoggedIn();
    this.username = this.authService.getUserName();
    this.idUser = this.authService.getIdUser();
    this.authService.loggedIn.subscribe(data => this.isLoggedIn = data);
    this.authService.username.subscribe(data => this.username = data);
  }

  goToUserProfile() {
    const userRoles = this.authService.getRoles();
    let regex = "ROLE_[A-Z]+";
    let matches = userRoles.matchAll(regex);
    let roles = [];
    for (const match of matches) {
      roles.push(match + '');
    }
    if (roles.length == 1) {
      switch(roles[0]) {
        case 'ROLE_USER':
          this.router.navigateByUrl('/users/account/user/' + this.idUser);
          break;
        case 'ROLE_ADMIN':
          this.router.navigateByUrl('/users/account/admin/' + this.idUser);
          break; 
        default:
          break; 
      }
    } else {
      if (roles.includes('ROLE_ADMIN')) {
        this.router.navigateByUrl('/users/account/admin/' + this.idUser);
      }
    }
  }

  logout() {
    this.authService.logout();
    this.router.navigateByUrl('').then(() => {
      window.location.reload();
    })
  }
}
