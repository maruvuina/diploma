import { Component, OnInit } from '@angular/core';
import { CategoryModel } from 'src/app/categories/shared/category-model';
import { CategoryService } from 'src/app/categories/shared/services/category.service';
import { FormGroup, Validators, FormControl, FormArray, FormBuilder } from '@angular/forms';
import { throwError, Observable } from 'rxjs';

@Component({
  selector: 'app-recipe-edit',
  templateUrl: './recipe-edit.component.html',
  styleUrls: ['./recipe-edit.component.css']
})
export class RecipeEditComponent implements OnInit {

  recipeForm: FormGroup;

  categories: Observable<Array<CategoryModel>>;

  constructor(private categoryService: CategoryService) { }

  ngOnInit(): void {
  	this.categories = this.getAllCategories();

  	this.recipeForm = new FormGroup({
      recipeName: new FormControl('', Validators.required),
      categoryName: new FormControl('', Validators.required),
      cookingTime: new FormControl('', Validators.required),
      yield: new FormControl('', Validators.required)
    });
  	
  }

  getAllCategories() {
  	return this.categoryService.getAllCategories();
  }

  updateRecipe() {

  }

}
