import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IngredientModel } from '../models/ingredient-model';

const API_INGREDIENTS = 'http://localhost:8080/api/ingredients/';

@Injectable({
  providedIn: 'root'
})
export class IngredientService {

  constructor(private httpClient: HttpClient) { }

  getAll(): Observable<Array<IngredientModel>> {
    return this.httpClient.get<Array<IngredientModel>>(API_INGREDIENTS);
  }

  getIngredientByIngredientNamePattern(pattern: string): Observable<Array<IngredientModel>> {
  	return this.httpClient.get<Array<IngredientModel>>(API_INGREDIENTS + 'name/' + pattern);
  }

  save(ingredient: IngredientModel): Observable<any> {
    return this.httpClient.post(API_INGREDIENTS, ingredient);
  }
}
