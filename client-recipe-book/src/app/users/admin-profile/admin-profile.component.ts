import { Component, OnInit, Input, OnDestroy, ViewChild, AfterViewChecked } from '@angular/core';
import { UserModel } from '../shared/models/user-model';
import { UserService } from '../shared/services/user.service';
import { FollowersComponent } from '../../users/followers/followers.component';
import { FollowingsComponent } from '../../users/followings/followings.component';
import { takeUntil } from 'rxjs/operators';
import { ReplaySubject, throwError } from 'rxjs';
import { RecipeService } from '../../recipes/shared/services/recipe.service';
import { RecipeDetails } from '../../recipes/shared/models/recipe-details';

@Component({
  selector: 'app-admin-profile',
  templateUrl: './admin-profile.component.html',
  styleUrls: ['./admin-profile.component.css']
})
export class AdminProfileComponent implements OnInit, OnDestroy, AfterViewChecked {

  @Input() id: number;

  user: UserModel;

  userLoaded: boolean = false;

  recipesCount: number = 0;

  followersCount: number;

  followingsCount: number;

  isRecipes: boolean = false;

  config: any;

  recipes: Array<RecipeDetails>;

  @ViewChild(FollowersComponent, { static: false }) 
  private followersComponent: FollowersComponent|undefined;

  @ViewChild(FollowingsComponent, { static: false }) 
  private followingsComponent: FollowingsComponent|undefined;

  destroy: ReplaySubject<any> = new ReplaySubject<any>(1);

  constructor(private userService: UserService, 
    private recipeService: RecipeService) {
    this.config = {
      itemsPerPage: 2,
      currentPage: 1,
      totalItems: 0,
      id: 'paginationUserRecipeList'
    }; 
  }

  ngOnInit(): void {
  	this.getUserById();
    this.getRecipes();
  }

  ngAfterViewChecked() { 
    setTimeout(() => {
      this.followersCount = this.followersComponent.getFollowersCount();
      this.followingsCount = this.followingsComponent.getFollowingsCount();
    }, 3000);
  }

  getUserById() {
    this.userService.getById(this.id)
    .pipe(takeUntil(this.destroy))
    .subscribe(user => {
      this.user = user;
      this.userLoaded = true;
      let recipesCount = user.recipeList.length;
      if (recipesCount != 0) {
        this.isRecipes = true;
        this.recipesCount = recipesCount;
      }
    });
  }

  getRequestParams(page: number, pageSize: number): any {
    let params: any = {};
    if (page) {
      params[`page`] = page - 1;
    }
    if (pageSize) {
      params[`size`] = pageSize;
    }
    return params;
  }

  getRecipes() {
    const params = this.getRequestParams(this.config.currentPage, 
      this.config.itemsPerPage);
    this.recipeService.getRecipesByAuthor(this.id, params)
    .pipe(takeUntil(this.destroy))
    .subscribe(response => {
      const { recipes, totalItems } = response;
      let recipesCount = recipes.length;
      this.recipes = recipes;
      this.config.totalItems = totalItems;
    }, error => {
      throwError(error);
    });
  }

  handlePageChange(event: number): void {
    this.config.currentPage = event;
    this.getRecipes(); 
  }

  ngOnDestroy(): void {
    this.destroy.next(null);
    this.destroy.complete();
  }
}
