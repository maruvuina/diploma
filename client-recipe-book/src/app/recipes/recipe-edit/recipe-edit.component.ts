import { Component, OnInit, OnDestroy } from '@angular/core';
import { CategoryModel } from 'src/app/categories/shared/category-model';
import { CategoryService } from 'src/app/categories/shared/services/category.service';
import { RecipeService } from '../shared/services/recipe.service';
import { RecipePayload } from '../shared/models/recipe-payload';
import { FormGroup, Validators, FormControl, FormArray, FormBuilder } from '@angular/forms';
import { throwError, Observable, ReplaySubject } from 'rxjs';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { takeUntil } from 'rxjs/operators';
import { ComponentCanDeactivate } from '../../auth/exit.guard';


@Component({
  selector: 'app-recipe-edit',
  templateUrl: './recipe-edit.component.html',
  styleUrls: ['./recipe-edit.component.css']
})
export class RecipeEditComponent implements OnInit, OnDestroy, ComponentCanDeactivate {

  id: number;

  recipeForm: FormGroup;

  categories: Observable<Array<CategoryModel>>;

  saved: boolean = false;

  destroy: ReplaySubject<any> = new ReplaySubject<any>(1);

  constructor(private categoryService: CategoryService, 
    private recipeService: RecipeService, 
    private route: ActivatedRoute, 
    private router: Router) { 
    this.id = +this.route.snapshot.paramMap.get('id');
  }

  ngOnInit(): void {
  	this.categories = this.getAllCategories();
  	this.recipeForm = new FormGroup({
      recipeName: new FormControl('', Validators.required),
      categoryName: new FormControl('', Validators.required),
      cookingTime: new FormControl('', Validators.required),
      yield: new FormControl('', Validators.required),
      announce: new FormControl('', Validators.required)
    });
  	this.getOriginalRecipeData();
  }

  getOriginalRecipeData() {
    this.recipeService.getRecipeById(this.id).subscribe(recipe => {
      this.recipeForm.patchValue({
        recipeName: recipe.recipeName,
        cookingTime: recipe.cookingTime,
        yield: recipe.yield,
        announce: recipe.announce
      })
      this.recipeForm.controls['categoryName'].setValue(recipe.categoryName, {onlySelf: true});
    });
  }

  getAllCategories() {
  	return this.categoryService.getAll();
  }

  updateRecipe() {
    this.saved = true;
    let recipePayload = new RecipePayload();
    for (const field in this.recipeForm.controls) {
      if (this.recipeForm.controls[field].value === '') {
        this.recipeForm.controls[field].setValue(null);
      }
    }
    recipePayload.recipeName = this.recipeForm.get('recipeName').value;
    recipePayload.categoryName = this.recipeForm.get('categoryName').value;
    recipePayload.yield = this.recipeForm.get('yield').value;
    recipePayload.cookingTime = this.recipeForm.get('cookingTime').value;
    recipePayload.announce = this.recipeForm.get('announce').value;
    this.recipeService.update(this.id, recipePayload)
    .pipe(takeUntil(this.destroy))
    .subscribe(recipe => {
      this.router.navigate(['/recipes', recipe.idRecipe]);
    }, error => {
      throwError(error);
    });
    this.recipeForm.reset();
  }

  canDeactivate() : boolean | Observable<boolean> {
    if(!this.saved) {
      return confirm("Вы хотите покинуть страницу? Данные не сохранятся.");
    } else {
      return true;
    }
  }

  ngOnDestroy(): void {
    this.destroy.next(null);
    this.destroy.complete();
  }
}
