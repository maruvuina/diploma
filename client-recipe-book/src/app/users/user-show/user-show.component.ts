import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormGroup, Validators, FormControl, FormArray, FormBuilder } from '@angular/forms';
import { UserModel } from '../shared/models/user-model';
import { UserService } from '../shared/services/user.service';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { takeUntil, switchMap } from 'rxjs/operators';
import { ReplaySubject, throwError } from 'rxjs';

@Component({
  selector: 'app-user-show',
  templateUrl: './user-show.component.html',
  styleUrls: ['./user-show.component.css']
})
export class UserShowComponent implements OnInit, OnDestroy {

  id: number;

  userLoaded: boolean = false;

  user: UserModel;

  followersCount: number = 0;

  followingsCount: number = 0;

  recipeCount: number = 0;

  destroy: ReplaySubject<any> = new ReplaySubject<any>(1);

  constructor(private userService: UserService, 
    private route: ActivatedRoute) {
    //this.id = +this.route.snapshot.paramMap.get('id');
  }

  ngOnInit(): void {
    this.route.paramMap.pipe(
      switchMap(params => params.getAll('id'))
    )
    .subscribe(data=> this.id = +data);
    this.getUserById();
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
      let followersCount = user.followers.length;
      if (followersCount != 0) {
        this.followersCount = followersCount;
      }
      let followingsCount = user.followings.length;
      if (followingsCount != 0) {
        this.followingsCount = followingsCount;
      }
    });
  }

  ngOnDestroy(): void {
    this.destroy.next(null);
    this.destroy.complete();
  }
}
