import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CuisineModel } from '../models/cuisine-model';
import { Observable } from 'rxjs';

const API_CUISINES = 'http://localhost:8080/api/cuisines/';

@Injectable({
  providedIn: 'root'
})
export class CuisineService {

  constructor(private httpClient: HttpClient) { }

  getAll(): Observable<Array<CuisineModel>> {
    return this.httpClient.get<Array<CuisineModel>>(API_CUISINES);
  }
}

