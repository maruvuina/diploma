import { Component, OnInit, OnDestroy } from '@angular/core';
import { CuisineService } from '../../cuisines/shared/services/cuisine.service';
import { CuisineModel } from '../../cuisines/shared/models/cuisine-model';
import { takeUntil } from 'rxjs/operators';
import { throwError, ReplaySubject } from 'rxjs';
import { FormGroup, Validators, FormControl } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-cuisine-admin-list',
  templateUrl: './cuisine-admin-list.component.html',
  styleUrls: ['./cuisine-admin-list.component.css']
})
export class CuisineAdminListComponent implements OnInit, OnDestroy {

  cuisineForm: FormGroup;

  cuisineList: Array<CuisineModel> = [];

  config: any;

  destroy: ReplaySubject<any> = new ReplaySubject<any>(1);

  constructor(private cuisineService: CuisineService, 
    private toastr: ToastrService) {
    this.config = {
      itemsPerPage: 45,
      currentPage: 1,
      totalItems: 0,
      id: 'cuisinesAdmin'
    }; 
  }

  ngOnInit(): void {
    this.cuisineForm = new FormGroup({
      cuisineName: new FormControl('', Validators.required)
    });
    this.getAllCuisines();
  }

  addCuisine() {
    let cuisine = new CuisineModel();
    cuisine.cuisineName = this.cuisineForm.get('cuisineName').value;
    this.cuisineService.save(cuisine)
    .pipe(takeUntil(this.destroy))
    .subscribe(() => {
      this.toastr.success("Кухня добавлена.");
      this.getAllCuisines();
    }, error => {
      throwError(error);
    });
    this.cuisineForm.reset();
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

  handlePageChange(event: number): void {
    this.config.currentPage = event;
    this.getAllCuisines(); 
  }

  ngOnDestroy(): void {
    this.destroy.next(null);
    this.destroy.complete();
  }
}
