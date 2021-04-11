import { Component, OnInit } from '@angular/core';
import { UserModel } from '../shared/models/user-model';
import { UserService } from '../shared/services/user.service';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { UserDetails } from '../shared/models/user-details';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  user: UserModel;

  isRecipes: boolean = false;

  id: number;

  userLoaded: boolean = false;

  followigns: Array<UserDetails>;

  followers: Array<UserDetails>;

  isFollowigns: boolean = false;

  followignsCount: number;

  isFollowers: boolean = false;

  followersCount: number;

  constructor(private userService: UserService, 
  	private route: ActivatedRoute) {
  	this.id = +this.route.snapshot.paramMap.get('id');
    this.followignsCount = 0;
    this.followersCount = 0;
  }

  ngOnInit(): void {
    this.getUserById();
    this.getFollowigns();
  }

  private getUserById() {
    this.userService.getById(this.id).subscribe(user => {
      this.user = user;
      this.userLoaded = true;
      if (user.recipeList.length != 0) {
        this.isRecipes = true;
      }
    });
  }

  private getFollowigns() {
    this.userService.getFollowings(this.id).subscribe(followigns => {
      this.followigns = followigns;
      if (followigns.length != 0) {
        this.isFollowigns = true;
        this.followignsCount = followigns.length;
      }
    });
  }

  private getFollowers() {
    this.userService.getFollowers(this.id).subscribe(followers => {
      this.followers = followers;
      if (followers.length != 0) {
        this.isFollowers = true;
        this.followersCount = followers.length;
      }
    });
  }

  subscribe() {

  }

}
