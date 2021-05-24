import { Component, OnInit, OnDestroy, ViewChild, AfterViewChecked } from '@angular/core';
import { FormGroup, Validators, FormControl, FormArray, FormBuilder } from '@angular/forms';
import { UserModel } from '../shared/models/user-model';
import { UserService } from '../shared/services/user.service';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { takeUntil, switchMap } from 'rxjs/operators';
import { ReplaySubject, throwError } from 'rxjs';
import { AuthService } from '../../auth/shared/services/auth.service';
import { ToastrService } from 'ngx-toastr';
import { UserDetails } from '../shared/models/user-details';
import { FollowersComponent } from '../../users/followers/followers.component';
import { FollowingsComponent } from '../../users/followings/followings.component';
import { ElementRef } from '@angular/core';
import { startWith, tap, delay } from 'rxjs/operators';

@Component({
  selector: 'app-user-show',
  templateUrl: './user-show.component.html',
  styleUrls: ['./user-show.component.css']
})
export class UserShowComponent implements OnInit, OnDestroy, AfterViewChecked  {

  id: number;

  userLoaded: boolean = false;

  user: UserModel;

  followersCount: number;

  followingsCount: number = 0;

  recipeCount: number = 0;

  idCurrentUser: number;

  subscribeText: string = 'Подписаться';

  isClick: boolean;

  @ViewChild(FollowersComponent, { static: false }) 
  private followersComponent: FollowersComponent|undefined;

  @ViewChild(FollowingsComponent, { static: false }) 
  private followingsComponent: FollowingsComponent|undefined;

  destroy: ReplaySubject<any> = new ReplaySubject<any>(1);

  constructor(private userService: UserService, 
    private route: ActivatedRoute, 
    private authService: AuthService, 
    private toastr: ToastrService) {}

  ngOnInit(): void {
    this.route.paramMap.pipe(
      switchMap(params => params.getAll('id'))
    )
    .subscribe(data=> this.id = +data);
    this.getUserById();
    this.idCurrentUser = this.authService.getIdUser();
    this.isSubscribed();
  }

  ngAfterViewChecked() { 
    setTimeout(() => {
      this.followersCount = this.followersComponent.getFollowersCount();
      this.followingsCount = this.followingsComponent.getFollowingsCount();
    }, 3000);
  }

  updateFollowersList() {
    this.followersComponent.updateFollowers();
  }

  getUserById() {
    this.userService.getById(this.id)
    .pipe(takeUntil(this.destroy))
    .subscribe(user => {
      this.userLoaded = true;
      this.user = user;
      let recipeCount = user.recipeList.length;
      if (recipeCount != 0) {
        this.recipeCount = recipeCount;
      }
      let followingsCount = user.followings.length;
      if (followingsCount != 0) {
        this.followingsCount = followingsCount;
      }
    });
  }

  isSubscribed() {
    if (this.authService.isLoggedIn()) {
      this.userService.isSubscribed(this.id)
      .pipe(takeUntil(this.destroy))
      .subscribe(isSubscribed => {
        if (isSubscribed) {
          this.isClick = false;
          this.subscribeText = 'Отписаться';
        } else {
          this.isClick = true;
          this.subscribeText = 'Подписаться';
        }
      });
    }
  }

  subscribe() {
    if (this.authService.isLoggedIn()) {
      let followign = new UserDetails();
      followign.id = this.id;
      if (this.isClick) {
        this.isClick = false;
        this.subscribeText = 'Отписаться';
        this.userService.subscribe(this.idCurrentUser, followign)
        .pipe(takeUntil(this.destroy))
        .subscribe(response => {
          this.toastr.success("Вы успешно подписались.");
          this.updateFollowersList();
        });
      } else {
        this.isClick = true;
        this.subscribeText = 'Подписаться';
        this.userService.subscribe(this.idCurrentUser, followign)
        .pipe(takeUntil(this.destroy))
        .subscribe(response => {
          this.toastr.info("Вы отписались от польвателя.");
          this.updateFollowersList();
        });
      }
    } else {
      this.toastr.warning("Только зарегистрированные пользователи могут подписаться.");
    }
  }

  ngOnDestroy(): void {
    this.destroy.next(null);
    this.destroy.complete();
  }
}
