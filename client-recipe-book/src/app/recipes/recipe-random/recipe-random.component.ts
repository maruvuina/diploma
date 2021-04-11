import { Component, OnInit, OnDestroy } from '@angular/core';
import { RecipeModel } from '../shared/models/recipe-model';
import { RecipeService } from '../shared/services/recipe.service';
import { throwError, ReplaySubject } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-recipe-random',
  templateUrl: './recipe-random.component.html',
  styleUrls: ['./recipe-random.component.css']
})
export class RecipeRandomComponent implements OnInit, OnDestroy {

  recipe: RecipeModel;

  isRecipeLoaded: boolean;
  
  destroy: ReplaySubject<any> = new ReplaySubject<any>(1);

  constructor(private recipeService: RecipeService, 
    private route: ActivatedRoute) {
    this.recipe = new RecipeModel();
    this.isRecipeLoaded = false;  
  }

  ngOnInit(): void {
  	this.getRandomRecipe();
  }

  getRandomRecipe() {
    this.recipeService.getRandomRecipe()
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
