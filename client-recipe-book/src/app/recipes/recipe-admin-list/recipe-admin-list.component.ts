import { Component, OnInit, OnDestroy } from '@angular/core';
import { RecipeModel } from '../shared/models/recipe-model';
import { RecipeService } from '../shared/services/recipe.service';
import { Router } from '@angular/router';
import { SortType } from '../shared/models/sort-type-enum';
import { throwError, ReplaySubject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-recipe-admin-list',
  templateUrl: './recipe-admin-list.component.html',
  styleUrls: ['./recipe-admin-list.component.css']
})
export class RecipeAdminListComponent implements OnInit, OnDestroy {

  recipes: Array<RecipeModel> = [];

  config: any;

  keyword = 'recipeName';

  data: any;

  errorMsg: string;

  isLoadingResult: boolean;

  destroy: ReplaySubject<any> = new ReplaySubject<any>(1);

  constructor(private recipeService: RecipeService, 
  	private router: Router, 
    private toastr: ToastrService) { 
    this.config = {
      itemsPerPage: 10,
      currentPage: 1,
      totalItems: 0,
      id: 'recipesAdmin'
    };
   }

  ngOnInit(): void {
  	this.getAllRecipes();
  }

  getRequestParams(page: number, pageSize: number, sortType: SortType): any {
    let params: any = {};
    if (page) {
      params[`page`] = page - 1;
    }
    if (pageSize) {
      params[`size`] = pageSize;
    }
    if (sortType) {
      params[`sort`] = sortType;
    }
    return params;
  }

  getAllRecipes() {
    const params = this.getRequestParams(this.config.currentPage, 
      this.config.itemsPerPage, SortType.SortByCreatedDate_ASC);
      this.recipeService.getAll(params)
      .pipe(takeUntil(this.destroy))
      .subscribe(response => {
      const { recipes, totalItems } = response;
      this.recipes = recipes;
      this.config.totalItems = totalItems;
    }, error => {
      throwError(error);
    });
  }

  handlePageChange(event: number): void {
    this.config.currentPage = event;
    this.getAllRecipes();
  }

  goToAuthor(idAuthor: number) {
    this.router.navigate(['/users', idAuthor]);
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
        this.errorMsg = "Такого рецепта нету.";
      } 
  }

  searchCleared() {
    this.data = [];
    this.isLoadingResult = false;
  }

  selectEvent(item) {}

  onFocused(e) {}

  goToRecipe(idRecipe: number) {
    this.router.navigate(['/recipes', idRecipe]);
  }

  deleteRecipe(id: number) {
    this.recipeService.delete(id)
    .pipe(takeUntil(this.destroy))
    .subscribe(() => {
      this.toastr.success("Рецепт удален.");
      this.getAllRecipes();
    });
  }

  ngOnDestroy(): void {
    this.destroy.next(null);
    this.destroy.complete();
  }
}
