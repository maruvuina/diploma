import { Component, OnInit, OnDestroy } from '@angular/core';
import { IngredientService } from '../../ingredients/shared/services/ingredient.service';
import { IngredientModel } from '../../ingredients/shared/models/ingredient-model';
import { takeUntil } from 'rxjs/operators';
import { throwError, ReplaySubject } from 'rxjs';
import { FormGroup, Validators, FormControl } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-ingredient-admin-list',
  templateUrl: './ingredient-admin-list.component.html',
  styleUrls: ['./ingredient-admin-list.component.css']
})
export class IngredientAdminListComponent implements OnInit, OnDestroy {

  ingredientForm: FormGroup;

  ingredientList: Array<IngredientModel> = [];

  config: any;

  destroy: ReplaySubject<any> = new ReplaySubject<any>(1);

  constructor(private ingredientService: IngredientService, 
    private toastr: ToastrService) {
    this.config = {
      itemsPerPage: 45,
      currentPage: 1,
      totalItems: 0,
      id: 'ingredientsAdmin'
    }; 
  }

  ngOnInit(): void {
    this.ingredientForm = new FormGroup({
      ingredientName: new FormControl('', Validators.required)
    });
    this.getAllIngredients();
  }

  addIngredient() {
    let ingredient = new IngredientModel();
    ingredient.ingredientName = this.ingredientForm.get('ingredientName').value;
    this.ingredientService.save(ingredient)
    .pipe(takeUntil(this.destroy))
    .subscribe(() => {
      this.toastr.success("Ингредиент добавлен.");
      this.getAllIngredients();
    }, error => {
      throwError(error);
    });
    this.ingredientForm.reset();
  }

  getAllIngredients() {
    this.ingredientService.getAll()
    .pipe(takeUntil(this.destroy))
    .subscribe(ingredientList => {
      this.ingredientList = ingredientList;
    }, error => {
      throwError(error);
    });
  }

  handlePageChange(event: number): void {
    this.config.currentPage = event;
    this.getAllIngredients(); 
  }

  ngOnDestroy(): void {
    this.destroy.next(null);
    this.destroy.complete();
  }
}
