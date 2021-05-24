import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { faHeart } from '@fortawesome/free-solid-svg-icons';
import { LikeType } from './shared/models/like-type';
import { LikePayload } from './shared/models/like-payload';
import { LikeService } from './shared/services/like.service';
import { ToastrService } from 'ngx-toastr';
import { RecipeService } from '../recipes/shared/services/recipe.service';
import { RecipeModel } from '../recipes/shared/models/recipe-model';
import { throwError, ReplaySubject } from 'rxjs';
import { AuthService } from '../auth/shared/services/auth.service';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-like',
  templateUrl: './like.component.html',
  styleUrls: ['./like.component.css']
})
export class LikeComponent implements OnInit, OnDestroy {

  likePayload: LikePayload;

  isActive: boolean = false;

  isClick: boolean = false;

  faHeart = faHeart;

  @Input() recipe: RecipeModel;

  destroy: ReplaySubject<any> = new ReplaySubject<any>(1);

  constructor(private likeService: LikeService, 
  	private toastr: ToastrService, 
  	private recipeService: RecipeService, 
    private authService: AuthService) {
  	this.likePayload = {
      likeType: undefined,
      idRecipe: undefined
    }
  }

  ngOnInit(): void {
    this.isLiked();
  }

  isLiked() {
    if (this.authService.isLoggedIn()) {
      this.likeService.isLiked(this.recipe.idRecipe)
      .pipe(takeUntil(this.destroy))
      .subscribe(isLiked => {
        if (isLiked) {
          this.isActive = true;
          console.log("isLiked");
        } else {
          this.isActive = false;
        }
      });
    }
  }

  onClick() {
    if (this.authService.isLoggedIn()) {
      this.isClick = !this.isClick;
      if (this.isClick) {
        this.isActive = true;
        this.likeRecipe();
      } else {
        this.isActive = false;
        this.unlikeRecipe();
      }
    } else {
      this.toastr.warning("Зарегистрируйтесь, чтобы поставить лайк.");
    }
  }

  likeRecipe() {
    this.likePayload.likeType = LikeType.LIKE;
    this.like();
  }

  unlikeRecipe() {
    this.likePayload.likeType = LikeType.UNLIKE;
    this.like();
  }

  like() {
    this.likePayload.idRecipe = this.recipe.idRecipe;
    this.likeService.like(this.likePayload)
    .pipe(takeUntil(this.destroy))
    .subscribe(() => {
      this.getRecipeById();
    }, error => {
      throwError(error); 
    });
  }

  getRecipeById() {
    this.recipeService.getRecipeById(this.recipe.idRecipe)
    .pipe(takeUntil(this.destroy))
    .subscribe(recipe => {
      this.recipe = recipe;
    }, error => {
      throwError(error); 
    });
  }

  ngOnDestroy(): void {
    this.destroy.next(null);
    this.destroy.complete();
  }
}
