import { Component, OnInit, Input } from '@angular/core';
import { UserModel } from '../shared/models/user-model';
import { UserService } from '../shared/services/user.service';

@Component({
  selector: 'app-admin-profile',
  templateUrl: './admin-profile.component.html',
  styleUrls: ['./admin-profile.component.css']
})
export class AdminProfileComponent implements OnInit {

  @Input() id: number;

  user: UserModel;

  userLoaded: boolean = false;

  recipesCount: number = 0;

  followignsCount: number = 0;

  followersCount: number = 0;
  
  constructor(private userService: UserService) { }

  ngOnInit(): void {
  	this.getUserById();
  }

  getUserById() {
    this.userService.getById(this.id).subscribe(user => {
    	console.log(JSON.stringify(user));
      this.user = user;
      this.userLoaded = true;
      let recipesCount = user.recipeList.length;
      if (recipesCount != 0) {
        // this.isRecipes = true;
        this.recipesCount = recipesCount;
      }
      let followignsCount = user.followings.length;
      if (followignsCount != 0) {
        this.followignsCount = followignsCount;
      }
      let followersCount = user.followers.length;
      if (followersCount != 0) {
        this.followersCount = followersCount;
      }
    });
  }

}
