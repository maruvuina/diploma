import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { UserService } from '../shared/services/user.service';
import { UserDetails } from '../shared/models/user-details';
import { AuthService } from '../../auth/shared/services/auth.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-subscribe',
  templateUrl: './subscribe.component.html',
  styleUrls: ['./subscribe.component.css']
})
export class SubscribeComponent implements OnInit {

  idUser: number;

  @Input() idFollowing: number;

  isActive: boolean = false;

  isClick: boolean = false;

  constructor(private userService: UserService, 
  	private authService: AuthService, 
    private toastr: ToastrService) { }

  ngOnInit(): void {
  	this.idUser = this.authService.getIdUser();
    this.isSubscribed();
  }

  isSubscribed() {
    if (this.authService.isLoggedIn()) {
      this.userService.isSubscribed(this.idFollowing)
      //.pipe(takeUntil(this.destroy))
      .subscribe(isSubscribed => {
        if (isSubscribed) {
          this.isActive = true;
        } else {
          this.isActive = false;
        }
      });
    }
  }

  subscribe() {
    if (this.authService.isLoggedIn()) {
      this.isClick = !this.isClick;
      let followign = new UserDetails();
      followign.id = this.idFollowing;
      if (this.isClick) {
        console.log("-----------------subscribe");
        this.isActive = true;
        this.userService.subscribe(this.idUser, followign)
        .subscribe(response => {
          this.toastr.success("Вы успешно подписались.");
        });
      } else {
        console.log("-----------------UNsubscribe");
        this.isActive = false;
        this.userService.subscribe(this.idUser, followign)
        .subscribe(response => {
          this.toastr.info("Вы отписались от польвателя.");
        });
      }
    } else {
      this.toastr.warning("Только зарегистрированные пользователи могут подписаться.");
    }
  }
}
