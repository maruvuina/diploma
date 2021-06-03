import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RecipeModel } from '../models/recipe-model';
import { IngredientModel } from '../../../ingredients/shared/models/ingredient-model';
import { RecipePayload } from '../models/recipe-payload';

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

  getRecipesByTag(params: any): Observable<any> {
    return this.httpClient.get<any>(API_RECIPES + 'tags', { params });
  }

  getRecipesByCuisine(params: any): Observable<any> {
    return this.httpClient.get<any>(API_RECIPES + 'cuisines', { params });
  }

  update(id: number, recipePayload: RecipePayload): Observable<RecipeModel> {
    return this.httpClient.patch<RecipeModel>(API_RECIPES + id, recipePayload);
  }

  getRecipeImage(idRecipe: number): Observable<string> {
    return this.httpClient.get<string>(API_RECIPES + 'image/' + idRecipe);
  }

  delete(id: number): Observable<any> {
    return this.httpClient.delete<any>(API_RECIPES + id);
  }

  existsByTitle(title: string): Observable<boolean> {
    return this.httpClient.get<boolean>(API_RECIPES + 'existsByTitle/?title=' + title);
  }
}
