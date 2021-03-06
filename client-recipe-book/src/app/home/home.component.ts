import { Component, OnInit, OnDestroy } from '@angular/core';
import { CategoryService } from '../categories/shared/services/category.service';
import { CategoryModel } from '../categories/shared/category-model';
import { RecipeService } from '../recipes/shared/services/recipe.service';
import { CuisineModel } from '../cuisines/shared/models/cuisine-model';
import { CuisineService } from '../cuisines/shared/services/cuisine.service';
import { throwError, Observable, ReplaySubject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { Router } from '@angular/router';
import { AuthService } from '../auth/shared/services/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, OnDestroy {

  categories: Observable<Array<CategoryModel>>;

  cuisines: Observable<Array<CuisineModel>>;

  keyword = 'recipeName';

  data: any;

  errorMsg: string;

  isLoadingResult: boolean;

  destroy: ReplaySubject<any> = new ReplaySubject<any>(1);
  
  constructor(private categoryService: CategoryService, 
  	private recipeService: RecipeService, 
    private cuisineService: CuisineService, 
    private authService: AuthService) {
  }

  ngOnInit(): void {
    this.categories = this.getAllCategories();
    this.cuisines = this.getAllCuisines();
    //this.authService.getRoles();
  }

  getAllCategories() {
    return this.categoryService.getAll();
  }

  getAllCuisines() {
    return this.cuisineService.getAll();
  }

  onChangeSearch(event: string) {
    this.isLoadingResult = true;
    if (event != '' && event != undefined && event != null) {
      this.recipeService.getRecipeByRecipeNamePattern(event)
        .pipe(takeUntil(this.destroy))
        .subscribe(recipes => {
          this.data = recipes as any[];
          this.isLoadingResult = false;
        }, error => {
          this.isLoadingResult = false;
          throwError(error);
      });
    } else {
        this.data = [];
        this.errorMsg = "";
      } 
  }

  searchCleared() {
    this.data = [];
    this.isLoadingResult = false;
  }

  selectEvent(item) {}

  onFocused(e) {}

  ngOnDestroy(): void {
    this.destroy.next(null);
    this.destroy.complete();
  }
}
