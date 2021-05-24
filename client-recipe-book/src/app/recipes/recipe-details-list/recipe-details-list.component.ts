import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { takeUntil } from 'rxjs/operators';
import { ReplaySubject, throwError } from 'rxjs';
import { RecipeDetails } from '../../recipes/shared/models/recipe-details';
import { RecipeService } from '../../recipes/shared/services/recipe.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-recipe-details-list',
  templateUrl: './recipe-details-list.component.html',
  styleUrls: ['./recipe-details-list.component.css']
})
export class RecipeDetailsListComponent implements OnInit, OnDestroy {

  @Input() id: number;

  isRecipes: boolean = false;

  recipesCount: number = 0;

  recipes: Array<RecipeDetails>;

  config: any;

  destroy: ReplaySubject<any> = new ReplaySubject<any>(1);

  constructor(private recipeService: RecipeService, 
    private router: Router) {
    this.config = {
      itemsPerPage: 5,
      currentPage: 1,
      totalItems: 0,
      id: 'paginationRecipeDetailsList'
    };
   }

  ngOnInit(): void {
  	this.getUserRecipes();
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

  getUserRecipes() {
    const params = this.getRequestParams(this.config.currentPage, 
      this.config.itemsPerPage);
    this.recipeService.getRecipesByAuthor(this.id, params)
    .pipe(takeUntil(this.destroy))
    .subscribe(response => {
      const { recipes, totalItems } = response;
      let recipesCount = recipes.length;
      if (recipesCount != 0) {
        this.isRecipes = true;
        this.recipesCount = recipesCount;   
      }
      this.recipes = recipes;
      this.config.totalItems = totalItems;
    }, error => {
      throwError(error);
    });
  }

  handlePageChangeRecipes(event: number): void {
    this.config.currentPage = event;
    this.getUserRecipes(); 
  }

  goToRecipe(idRecipe: number) {
    this.router.navigate(['/recipes', idRecipe]);
  }

  ngOnDestroy(): void {
    this.destroy.next(null);
    this.destroy.complete();
  }
}
