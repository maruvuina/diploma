import { Component, OnInit, OnDestroy, ViewChild, AfterViewChecked } from '@angular/core';
import { UserModel } from '../shared/models/user-model';
import { UserService } from '../shared/services/user.service';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { UserDetails } from '../shared/models/user-details';
import { map } from 'rxjs/operators';
import { RecipeDetails } from '../../recipes/shared/models/recipe-details';
import { takeUntil } from 'rxjs/operators';
import { ReplaySubject, throwError } from 'rxjs';
import { RecipeService } from '../../recipes/shared/services/recipe.service';
import { ContactPayload } from '../../contact/shared/contact-payload';
import { AuthService } from '../../auth/shared/services/auth.service';
import { ToastrService } from 'ngx-toastr';
import { FollowersComponent } from '../../users/followers/followers.component';
import { FollowingsComponent } from '../../users/followings/followings.component';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit, OnDestroy, AfterViewChecked {

  user: UserModel;

  isRecipes: boolean = false;

  id: number;

  userLoaded: boolean = false;

  recipesCount: number = 0;

  followersCount: number;

  followingsCount: number;

  config: any;

  recipes: Array<RecipeDetails>;

  isMailing: boolean;

  destroy: ReplaySubject<any> = new ReplaySubject<any>(1);

  @ViewChild(FollowersComponent, { static: false }) 
  private followersComponent: FollowersComponent|undefined;

  @ViewChild(FollowingsComponent, { static: false }) 
  private followingsComponent: FollowingsComponent|undefined;

  constructor(private userService: UserService, 
  	private route: ActivatedRoute, 
    private recipeService: RecipeService, 
    private authService: AuthService, 
    private toastr: ToastrService) {
  	this.id = +this.route.snapshot.paramMap.get('id');
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
    this.isUserMailing();
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

  isUserMailing() {
    this.userService.isMailing()
    .pipe(takeUntil(this.destroy))
    .subscribe(isMailing => {
      this.isMailing = isMailing;
    }, error => {
      throwError(error);
    });
  }

  subscribeToNewsletter() {
    let contactPayload = new ContactPayload();
    contactPayload.fromEmail = this.authService.getUserName();
    if (this.isMailing) {
      this.userService.unsubscribeToNewsletter(contactPayload)
      .subscribe(() => {
        this.toastr.info("На почту отправлено сообщение об отписки на рассылки.");
      });
      this.isMailing = false;
    } else {
      this.userService.subscribeToNewsletter(contactPayload)
      .subscribe(() => {
        this.toastr.info("На почту отправлено сообщение о подписки на рассылки.");
      });
      this.isMailing = true;
    }
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
