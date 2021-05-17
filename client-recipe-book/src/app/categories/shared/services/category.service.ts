import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CategoryModel } from '../category-model';
import { Observable } from 'rxjs';

const API_CATEGORIES = 'http://localhost:8080/api/categories/';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private httpClient: HttpClient) { }

  getAll(): Observable<Array<CategoryModel>> {
    return this.httpClient.get<Array<CategoryModel>>(API_CATEGORIES);
  }
}
