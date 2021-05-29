import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { RecipeModel } from '../../recipes/shared/models/recipe-model';
import { RecipeService } from '../../recipes/shared/services/recipe.service';
import { throwError, ReplaySubject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';

@Component({
  selector: 'app-tag',
  templateUrl: './tag.component.html',
  styleUrls: ['./tag.component.css']
})
export class TagComponent implements OnInit, OnDestroy {

  tagName: string;

  recipes: Array<RecipeModel> = [];

  config: any;

  isFounded: boolean;

  destroy: ReplaySubject<any> = new ReplaySubject<any>(1);

  constructor(private recipeService: RecipeService, 
  	private route: ActivatedRoute, 
  	private router: Router) {
  	this.tagName = this.route.snapshot.queryParams['tag']; 
  	this.config = {
	    itemsPerPage: 5,
	    currentPage: 1,
	    totalItems: 0
	  };
	}

  ngOnInit(): void {
  	this.getRecipesByTag();
  }

  getRequestParams(page: number, pageSize: number): any {
    let params: any = {};
    if (page) {
      params[`page`] = page - 1;
    }
    if (pageSize) {
      params[`size`] = pageSize;
    }
    if (this.tagName) {
    	params[`tagName`] = this.tagName;
    }
    return params;
  }

  getRecipesByTag() {
    const params = this.getRequestParams(this.config.currentPage, 
      this.config.itemsPerPage);
      this.recipeService.getRecipesByTag(params)
      .pipe(takeUntil(this.destroy))
      .subscribe(response => {
      const { recipes, totalItems } = response;
      if (recipes.length != 0) {
        this.isFounded = true;
        this.recipes = recipes;
        this.config.totalItems = totalItems;
      } else {
        this.isFounded = false;
      }
    }, error => {
      throwError(error);
    });
  }

  handlePageChange(event: number): void {
    this.config.currentPage = event;
    this.getRecipesByTag();
  }

  goToRecipe(idRecipe: number) {
    this.router.navigate(['/recipes', idRecipe]);
  }

  goToAuthor(idAuthor: number) {
    this.router.navigate(['/users', idAuthor]);
  }

  ngOnDestroy(): void {
    this.destroy.next(null);
    this.destroy.complete();
  }
}
