import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormGroup, Validators, FormControl, FormArray, FormBuilder } from '@angular/forms';
import { RecipeService } from '../shared/services/recipe.service';
import { IngredientService } from '../shared/services/ingredient.service';
import { IngredientModel } from '../shared/models/ingredient-model';
import { throwError, ReplaySubject } from 'rxjs';
import { RecipeModel } from '../shared/models/recipe-model';
import { takeUntil } from 'rxjs/operators';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';

@Component({
  selector: 'app-recipe-search-by-ingredient',
  templateUrl: './recipe-search-by-ingredient.component.html',
  styleUrls: ['./recipe-search-by-ingredient.component.css']
})
export class RecipeSearchByIngredientComponent implements OnInit, OnDestroy {
  
  isSearch: boolean = true;

  ingredientList: Array<IngredientModel>;

  selectedIngredient: string;

  searchByIngredientsForm: FormGroup;

  searchedIngredients: IngredientModel[] = [];

  recipes: Array<RecipeModel> = [];

  config: any;

  isRecipesFounded: boolean = false;

  destroy: ReplaySubject<any> = new ReplaySubject<any>(1);

  isSearchByIngredients: boolean = false;

  searchByNameForm: FormGroup;

  keyword = 'recipeName';

  data: any;

  errorMsg: string;

  isLoadingResult: boolean;

  constructor(private recipeService: RecipeService, 
    private ingredientService: IngredientService, 
    private router: Router) { 
      this.ingredientList = [];
      this.config = {
        itemsPerPage: 2,
        currentPage: 1,
        totalItems: 0
      };
    }

  ngOnInit(): void {
    this.searchByIngredientsForm = new FormGroup({
      searchedIngredients: new FormControl('', Validators.required)
    });
    this.searchByNameForm = new FormGroup({
      searchedName: new FormControl('', Validators.required)
    });
    this.getAllIngredients();
  }

  getAllIngredients() {
    this.ingredientService.getAllIngredients()
    .pipe(takeUntil(this.destroy))
    .subscribe(ingredientList => {
      this.ingredientList = ingredientList;
    }, error => {
      throwError(error);
    });
  }

  change() {
    this.isSearch = !this.isSearch;
    this.isSearchByIngredients = true;
  }

  searchRecipe() {
    if (this.isSearch) {
      this.searchedIngredients = [];
      for (var i = 0; i < this.searchByIngredientsForm.get('searchedIngredients').value.length; i++) {
        let ingredient = new IngredientModel();
        ingredient.ingredientName = this.searchByIngredientsForm.get('searchedIngredients').value[i];
        this.searchedIngredients.push(ingredient);
      }
      this.getRecipesByIngredients();
      this.searchByIngredientsForm.reset();
    } else {

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

  getRecipesByIngredients() {
    const params = this.getRequestParams(this.config.currentPage, 
      this.config.itemsPerPage);
      this.recipeService.getRecipesByIngredients(params, this.searchedIngredients)
      .pipe(takeUntil(this.destroy))
      .subscribe(response => {
      const { recipes, totalItems } = response;
      this.recipes = recipes;
      this.config.totalItems = totalItems;
      this.isRecipesFounded = true;
    }, error => {
      throwError(error);
    });
  }

  handlePageChange(event: number): void {
    this.config.currentPage = event;
    this.getRecipesByIngredients();
  }

  onChangeSearch(event: string) {
    console.log("onChangeSearch");
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
        this.errorMsg = "Поле пустое.";
      } 
  }

  searchCleared() {
    this.data = [];
    this.isLoadingResult = false;
  }

  selectEvent(item) {}

  onFocused(e) {}

  byId(idRecipe: number) {
    this.router.navigate(['/recipes', idRecipe]);
  }

  ngOnDestroy(): void {
    this.destroy.next(null);
    this.destroy.complete();
  }
}
