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
    this.authService.loggedIn.subscribe((data: boolean) => this.isLoggedIn = data);
    this.authService.username.subscribe((data: string) => this.username = data);
  }

  goToUserProfile() {
    this.router.navigateByUrl('/users/account/user/' + this.idUser);
  }

  logout() {
    this.authService.logout();
    this.router.navigateByUrl('').then(() => {
      window.location.reload();
    })
  }

}
