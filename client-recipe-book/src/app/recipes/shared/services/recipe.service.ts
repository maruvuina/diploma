import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RecipeModel } from '../models/recipe-model';
import { IngredientModel } from '../models/ingredient-model';

const API_RECIPES = 'http://localhost:8080/api/recipes/';

@Injectable({
  providedIn: 'root'
})
export class RecipeService {

  constructor(private httpClient: HttpClient) { }

  create(formData: FormData): Observable<RecipeModel> {
    return this.httpClient.post<RecipeModel>(API_RECIPES, formData);
  }

  getRecipeById(id: number): Observable<RecipeModel> {
    return this.httpClient.get<RecipeModel>(API_RECIPES + id);
  }

  getRandomRecipe(): Observable<RecipeModel> {
    return this.httpClient.get<RecipeModel>(API_RECIPES + 'random');
  }

  getAll(params: any): Observable<any> {
    return this.httpClient.get<any>(API_RECIPES, { params });
  }

  getRecipeByRecipeNamePattern(pattern: string): Observable<Array<RecipeModel>> {
    return this.httpClient.get<Array<RecipeModel>>(API_RECIPES + 'search/' + pattern);
  }

  getRecipeByName(name: string): Observable<RecipeModel> {
    return this.httpClient.get<RecipeModel>(API_RECIPES + 'name/?name=' + name);
  }

  getRecipesByIngredients(params: any, searchedIngredients: Array<IngredientModel>): Observable<any> {
    return this.httpClient.post<any>(API_RECIPES + 'ingredients?page=' + 
      params.page + '&size=' + params.size, searchedIngredients);
  }

  getRecipesByCategory(params: any): Observable<any> {
    return this.httpClient.get<any>(API_RECIPES + 'categories', { params });
  }

  getRecipesByAuthor(idAuthor: number, params: any): Observable<any> {
    return this.httpClient.get<any>(API_RECIPES + 'authors/' + idAuthor, { params });
  }

}
