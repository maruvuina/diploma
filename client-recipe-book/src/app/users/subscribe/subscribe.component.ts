import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { UserService } from '../shared/services/user.service';
import { UserDetails } from '../shared/models/user-details';
import { AuthService } from '../../auth/shared/services/auth.service';

@Component({
  selector: 'app-subscribe',
  templateUrl: './subscribe.component.html',
  styleUrls: ['./subscribe.component.css']
})
export class SubscribeComponent implements OnInit {

  idUser: number;

  @Input() idFollowing: number;

  constructor(private userService: UserService, 
  	private authService: AuthService) { }

  ngOnInit(): void {
  	this.idUser = this.authService.getIdUser();
  }

  subscribe() {
  	let followign = new UserDetails();
  	followign.id = this.idFollowing;
  	this.userService.subscribe(this.idUser, followign);
  }
}
