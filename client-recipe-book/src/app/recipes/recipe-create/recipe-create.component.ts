import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormGroup, Validators, FormControl, FormArray, FormBuilder } from '@angular/forms';
import { CategoryModel } from 'src/app/categories/shared/category-model';
import { CategoryService } from 'src/app/categories/shared/services/category.service';
import { RecipePayload } from '../shared/models/recipe-payload';
import { RecipeService } from '../shared/services/recipe.service';
import { IngredientModel } from '../../ingredients/shared/models/ingredient-model';
import { InstructionModel } from '../shared/models/instruction-model';
import { Router } from '@angular/router';
import { TagService } from '../../tags/shared/services/tag.service';
import { TagModel } from '../../tags/shared/models/tag-model';
import { CuisineService } from '../../cuisines/shared/services/cuisine.service';
import { CuisineModel } from '../../cuisines/shared/models/cuisine-model';
import { IngredientService } from '../../ingredients/shared/services/ingredient.service';
import { throwError, Observable, of, ReplaySubject } from 'rxjs';
import { AbstractControl, AsyncValidatorFn, ValidationErrors } from "@angular/forms";
import { takeUntil, map } from 'rxjs/operators';
import { ComponentCanDeactivate } from '../../auth/exit.guard';


function fileUploaded() {
  document.getElementById("uploadButton").addEventListener("click", function(){
  document.getElementById("file").click();
  });

  let file = <HTMLInputElement>document.getElementById("file");
  file.addEventListener("change", function(){
    var fullPath = this.value;
    var fileName = fullPath.split(/(\\|\/)/g).pop();
    document.getElementById("fileName").innerHTML = fileName;
  }, false);
}

@Component({
  selector: 'app-recipe-create',
  templateUrl: './recipe-create.component.html',
  styleUrls: ['./recipe-create.component.css']
})
export class RecipeCreateComponent implements OnInit, OnDestroy, ComponentCanDeactivate {

  recipeForm: FormGroup;

  recipePayload: RecipePayload;

  categories: Observable<Array<CategoryModel>>;

  selectedFile: File = null;

  ingredientList: Array<IngredientModel> = [];

  keyword = 'ingredientName';

  data: any;

  errorMsg: string;
  
  isLoadingResult: boolean;

  tagList: Array<TagModel> = [];

  cuisineList: Array<CuisineModel> = [];

  saved: boolean = false;

  destroy: ReplaySubject<any> = new ReplaySubject<any>(1);

  constructor(private recipeService: RecipeService,
    private categoryService: CategoryService, 
    private formBuilder: FormBuilder, 
    private router: Router, 
    private ingredientService: IngredientService, 
    private tagService: TagService, 
    private cuisineService: CuisineService) { 
    this.recipePayload = {
      recipeName: '',
      cookingTime: 0,
      yield: 0,
      announce: '',
      categoryName: '',
      ingredients: [],
      instructions: [],
      tags: [],
      cuisines: []
    }
  }

  ngOnInit(): void {
  	fileUploaded();
    this.recipeForm = new FormGroup({
      recipeName: new FormControl('', { 
        validators: [Validators.required], 
        asyncValidators: [this.existingRecipeValidator()], 
        updateOn: 'blur' 
      }),
      cookingTime: new FormControl('', Validators.required),
      yield: new FormControl('', Validators.required),
      announce: new FormControl('', Validators.required),
      categoryName: new FormControl('', Validators.required),
      ingredients: new FormArray([]),
      instructions: new FormArray([]),
      tags: new FormControl('', Validators.required),
      cuisines: new FormControl('', Validators.required)
    });
    this.categories = this.getAllCategories();
    this.getAllIngredients();
    this.getAllTags();
    this.getAllCuisines();
  }

  getAllCategories() {
    return this.categoryService.getAll();
  }

  getAllIngredients() {
    return this.ingredientService.getAll()
    .pipe(takeUntil(this.destroy))
    .subscribe(ingredientList => {
      this.ingredientList = ingredientList;
    }, error => {
      throwError(error);
    });
  }

  getAllTags() {
    this.tagService.getAll()
    .pipe(takeUntil(this.destroy))
    .subscribe(tagList => {
      this.tagList = tagList;
    }, error => {
      throwError(error);
    });
  }

  getAllCuisines() {
    this.cuisineService.getAll()
    .pipe(takeUntil(this.destroy))
    .subscribe(cuisineList => {
      this.cuisineList = cuisineList;
    }, error => {
      throwError(error);
    });
  }

  onChangeSearch(event: string) {
    this.isLoadingResult = true;
    if (event != '' && event != undefined && event != null) {
      this.ingredientService.getIngredientByIngredientNamePattern(event)
      .pipe(takeUntil(this.destroy))
        .subscribe(ingredients => {
          this.data = ingredients as any[];
          this.isLoadingResult = false;
        }, error => {
          this.isLoadingResult = false;
          throwError(error);
      });
    } else {
        this.data = [];
        this.errorMsg = "???????? ????????????";
      } 
  }

  searchCleared() {
    this.data = [];
    this.isLoadingResult = false;
  }

  selectEvent(item) {}

  onFocused(e) {}

  get ingredients() {
    return this.recipeForm.get('ingredients') as FormArray;
  }

  get instructions() {
    return this.recipeForm.get('instructions') as FormArray;
  }

  addIngredient() {
    const group = new FormGroup({
      ingredientName: new FormControl('', Validators.required),
      ingredientAmount: new FormControl('', Validators.required)
    });
    this.ingredients.push(group);
  }

  removeIngredient(index: number): void {
    this.ingredients.removeAt(index);
  }

  addInstruction() {
    const group = new FormGroup({
      instructionStep: new FormControl('', Validators.required)
    });
    this.instructions.push(group);
  }

  removeInstruction(index: number): void {
    this.instructions.removeAt(index);
  }

  addRecipe() {
    this.saved = true;
    this.setMainRecipeInfo();
    this.setIngredients();
    this.setInstructions();
    this.setTags();
    this.setCuisines();
    let formData = new FormData();
    formData.append("file", this.selectedFile, this.selectedFile.name);
    formData.append("recipeDto", JSON.stringify(this.recipePayload));
    this.recipeService.create(formData)
    .pipe(takeUntil(this.destroy))
    .subscribe(recipe => {
      this.router.navigate(['/recipes', recipe.idRecipe]);
    }, error => {
      throwError(error);
    });
  }

  private setMainRecipeInfo() {
    this.recipePayload.recipeName = this.recipeForm.get('recipeName').value;
    this.recipePayload.categoryName = this.recipeForm.get('categoryName').value;
    this.recipePayload.yield = this.recipeForm.get('yield').value;
    this.recipePayload.cookingTime = this.recipeForm.get('cookingTime').value;
    this.recipePayload.announce = this.recipeForm.get('announce').value;
  }

  private setIngredients() {
    let jsonIngredientStringify = JSON.stringify(this.recipeForm.get('ingredients').value);
    let jsonIngredientObject = JSON.parse(jsonIngredientStringify);
    for(let i = 0; i < jsonIngredientObject.length; i++) {
      let ingredientModel = new IngredientModel();
      ingredientModel.ingredientName = jsonIngredientObject[i]['ingredientName'].ingredientName;
      ingredientModel.measureAmount = jsonIngredientObject[i]['ingredientAmount'];
      this.recipePayload.ingredients.push(ingredientModel);
    }
  }

  private setInstructions() {
    let jsonInstructionStringify = JSON.stringify(this.recipeForm.get('instructions').value);
    let jsonInstructionObject = JSON.parse(jsonInstructionStringify);
    for(let i = 0; i < jsonInstructionObject.length; i++) {
      let instructionModel = new InstructionModel();
      instructionModel.stepNumber = i + 1;
      instructionModel.description = jsonInstructionObject[i]['instructionStep'];
      this.recipePayload.instructions.push(instructionModel);
    }
  }

  private setTags() {
    for (var i = 0; i < this.recipeForm.get('tags').value.length; i++) {
      let tag = new TagModel();
      tag.tagName = this.recipeForm.get('tags').value[i];
      this.recipePayload.tags.push(tag);
    }
  }

  private setCuisines() {
    for (var i = 0; i < this.recipeForm.get('cuisines').value.length; i++) {
      let cuisine = new CuisineModel();
      cuisine.cuisineName = this.recipeForm.get('cuisines').value[i];
      this.recipePayload.cuisines.push(cuisine);
    }
  }

  onUploadFile(event) {
    this.selectedFile = <File>event.target.files[0];
  }

  private isEmptyInputValue(value: any): boolean {
    return value === null || value.length === 0;
  }

  existingRecipeValidator(): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      return this.recipeService.existsByTitle(control.value).pipe(
        map(response => {
          return response ? { recipeExists: true } : null;
        })
      );
    };
  }

  canDeactivate() : boolean | Observable<boolean> {
    if(!this.saved) {
      return confirm("???? ???????????? ???????????????? ????????????????? ???????????? ???? ????????????????????.");
    } else {
      return true;
    }
  }

  ngOnDestroy(): void {
    this.destroy.next(null);
    this.destroy.complete();
  }
}
