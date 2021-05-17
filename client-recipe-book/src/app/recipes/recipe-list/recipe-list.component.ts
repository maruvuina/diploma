import { Component, OnInit, ViewEncapsulation, Input } from '@angular/core';
import { RecipeModel } from '../shared/models/recipe-model';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { RecipeService } from '../shared/services/recipe.service';
import { SortTypeModel } from '../shared/models/sort-type-model';
import { SortType } from '../shared/models/sort-type-enum';
import { switchMap } from 'rxjs/operators';
import { throwError } from 'rxjs';

@Component({
  selector: 'app-recipe-list',
  templateUrl: './recipe-list.component.html',
  styleUrls: ['./recipe-list.component.css']
})
export class RecipeListComponent implements OnInit {
  
  recipes: Array<RecipeModel> = [];

  id: number;	

  config: any;

  sortTypes: Array<SortTypeModel>;

  isNotSorting: boolean = true;

  isSortByCreatedDate: boolean = false;

  isSortByRecipeName: boolean = false;

  constructor(private router: Router, 
    private route: ActivatedRoute, 
    private recipeService: RecipeService) { 
    this.config = {
      itemsPerPage: 9,
      currentPage: 1,
      totalItems: 0
    };
    this.sortTypes = [{
      idSortType: 1,
      description: 'последние'
    },
    {
      idSortType: 2,
      description: 'по названию'
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
    this.getAllRecipes(SortType.NotSort);
  	this.route.paramMap.pipe(switchMap(params => params.getAll('id')))
    .subscribe(id => this.id = +id);    
  }

  getAllRecipes(sortType: SortType) {
    const params = this.getRequestParams(this.config.currentPage, 
      this.config.itemsPerPage, sortType);
      this.recipeService.getAll(params).subscribe(response => {
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
      this.getAllRecipes(SortType.SortByCreatedDate);
    }
    if (this.isSortByRecipeName) {
      this.getAllRecipes(SortType.SortByRecipeName);
    }
    if (this.isNotSorting) {
      this.getAllRecipes(SortType.NotSort);
    }
  }

  selectHandler(id: any) {
    if (id == 1) {
      this.isSortByCreatedDate = true;
      this.isSortByRecipeName = false;
      this.isNotSorting = false;
      this.getAllRecipes(SortType.SortByCreatedDate);
    } else if (id == 2) {
      this.isSortByRecipeName = true;
      this.isSortByCreatedDate = false;
      this.isNotSorting = false;
      this.getAllRecipes(SortType.SortByRecipeName);
    } else if (JSON.parse(id) === null) {
      this.isNotSorting = true;
      this.isSortByRecipeName = false;
      this.isSortByCreatedDate = false;
      this.getAllRecipes(SortType.NotSort);
    }
  }

  goToAuthor(idAuthor: number) {
    this.router.navigate(['/users', idAuthor]);
  }
}
