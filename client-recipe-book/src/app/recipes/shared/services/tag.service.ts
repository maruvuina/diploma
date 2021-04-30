import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TagModel } from '../models/tag-model';

const API_TAGS = 'http://localhost:8080/api/tags/';

@Injectable({
  providedIn: 'root'
})
export class TagService {

  constructor(private httpClient: HttpClient) { }

  getAll(): Observable<Array<TagModel>> {
    return this.httpClient.get<Array<TagModel>>(API_TAGS);
  }
}
