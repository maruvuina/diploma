import { Component, OnInit, OnDestroy } from '@angular/core';
import { RecipeModel } from '../shared/models/recipe-model';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { RecipeService } from '../shared/services/recipe.service';
import { SortTypeModel } from '../shared/models/sort-type-model';
import { SortType } from '../shared/models/sort-type-enum';
import { switchMap } from 'rxjs/operators';
import { throwError, ReplaySubject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-recipe-list',
  templateUrl: './recipe-list.component.html',
  styleUrls: ['./recipe-list.component.css']
})
export class RecipeListComponent implements OnInit, OnDestroy {
  
  recipes: Array<RecipeModel> = [];

  id: number;	

  config: any;

  sortTypes: Array<SortTypeModel>;

  isNotSorting: boolean = true;

  isSortByCreatedDate: boolean = false;

  isSortByRecipeName: boolean = false;

  destroy: ReplaySubject<any> = new ReplaySubject<any>(1);

  constructor(private router: Router, 
    private route: ActivatedRoute, 
    private recipeService: RecipeService) { 
    this.config = {
      itemsPerPage: 9,
      currentPage: 1,
      totalItems: 0
    };
    this.sortTypes = [{
      description: 'сначала новые',
      sortType: SortType.SortByCreatedDate_DESC
    },
    {
      description: 'сначала старые',
      sortType: SortType.SortByCreatedDate_ASC
    },
    {
      description: 'по названию',
      sortType: SortType.SortByRecipeName
    }];
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

  ngOnInit(): void {
    this.getAllRecipes(SortType.SortByCreatedDate_ASC);
  	this.route.paramMap.pipe(switchMap(params => params.getAll('id')))
    .pipe(takeUntil(this.destroy))
    .subscribe(id => this.id = +id);    
  }

  getAllRecipes(sortType: SortType) {
    const params = this.getRequestParams(this.config.currentPage, 
      this.config.itemsPerPage, sortType);
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
    if (this.isSortByCreatedDate) {
      this.getAllRecipes(SortType.SortByCreatedDate_DESC);
    }
    if (this.isSortByRecipeName) {
      this.getAllRecipes(SortType.SortByRecipeName);
    }
    if (this.isNotSorting) {
      this.getAllRecipes(SortType.SortByCreatedDate_ASC);
    }
  }

  selectHandler(sortType) {
    if (sortType == SortType.SortByCreatedDate_DESC) {
      this.isSortByCreatedDate = true;
      this.isSortByRecipeName = false;
      this.isNotSorting = false;
      this.getAllRecipes(SortType.SortByCreatedDate_DESC);
    } else if (sortType == SortType.SortByCreatedDate_ASC) {
      this.isNotSorting = true;
      this.isSortByRecipeName = false;
      this.isSortByCreatedDate = false;
      this.getAllRecipes(SortType.SortByCreatedDate_ASC);
    } else if (sortType == SortType.SortByRecipeName) {
      this.isSortByRecipeName = true;
      this.isSortByCreatedDate = false;
      this.isNotSorting = false;
      this.getAllRecipes(SortType.SortByRecipeName);
    }
  }

  goToAuthor(idAuthor: number) {
    this.router.navigate(['/users', idAuthor]);
  }

  ngOnDestroy(): void {
    this.destroy.next(null);
    this.destroy.complete();
  }
}
