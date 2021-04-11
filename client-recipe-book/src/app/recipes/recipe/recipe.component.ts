import { Component, OnInit, OnDestroy } from '@angular/core';
import { RecipeService } from '../shared/services/recipe.service';
import { RecipeModel } from '../shared/models/recipe-model';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { ReplaySubject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-recipe',
  templateUrl: './recipe.component.html',
  styleUrls: ['./recipe.component.css']
})
export class RecipeComponent implements OnInit, OnDestroy {

  recipe: RecipeModel;

  id: number;

  isRecipeLoaded: boolean;

  destroy: ReplaySubject<any> = new ReplaySubject<any>(1);

  constructor(private recipeService: RecipeService, 
  	private route: ActivatedRoute) { 
    this.id = +this.route.snapshot.paramMap.get('id');
    this.recipe = new RecipeModel();
    this.isRecipeLoaded = false;
  }

  ngOnInit(): void {
    this.getRecipeById();
  }

  getRecipeById() {
    this.recipeService.getRecipeById(this.id)
    .pipe(takeUntil(this.destroy))
    .subscribe(recipe => {
      this.recipe = recipe;
      this.isRecipeLoaded = true;
    });
  }

  ngOnDestroy(): void {
    this.destroy.next(null);
    this.destroy.complete();
  }
}
