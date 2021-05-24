import { Component, OnInit, OnDestroy } from '@angular/core';
import { UserModel } from '../shared/models/user-model';
import { UserService } from '../shared/services/user.service';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { UserDetails } from '../shared/models/user-details';
import { map } from 'rxjs/operators';
import { RecipeDetails } from '../../recipes/shared/models/recipe-details';
import { takeUntil } from 'rxjs/operators';
import { ReplaySubject, throwError } from 'rxjs';
import { RecipeService } from '../../recipes/shared/services/recipe.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit, OnDestroy {

  user: UserModel;

  isRecipes: boolean = false;

  id: number;

  userLoaded: boolean = false;

  recipesCount: number;

  followignsCount: number;

  followersCount: number;

  config: any;

  recipes: Array<RecipeDetails>;

  destroy: ReplaySubject<any> = new ReplaySubject<any>(1);

  constructor(private userService: UserService, 
  	private route: ActivatedRoute, 
    private recipeService: RecipeService) {
  	this.id = +this.route.snapshot.paramMap.get('id');
    this.recipesCount = 0;
    this.followignsCount = 0;
    this.followersCount = 0;
    this.config = {
      itemsPerPage: 3,
      currentPage: 1,
      totalItems: 0,
      id: 'paginationUserRecipeList'
    };
  }

  ngOnInit(): void {
    this.getUserById();
    this.getRecipes();
  }

  getUserById() {
    this.userService.getById(this.id).subscribe(user => {
      this.user = user;
      this.userLoaded = true;
      let recipesCount = user.recipeList.length;
      if (recipesCount != 0) {
        this.isRecipes = true;
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
