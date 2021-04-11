import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LikePayload } from '../../../like/shared/models/like-payload';

const API_LIKE = 'http://localhost:8080/api/recipes/likes/';

@Injectable({
  providedIn: 'root'
})
export class LikeService {

  constructor(private httpClient: HttpClient) { }

  like(likePayload: LikePayload): Observable<any> {
    return this.httpClient.post(API_LIKE, likePayload);
  }

  isLiked(idRecipe: number): Observable<boolean> {
  	return this.httpClient.get<boolean>(API_LIKE + idRecipe);
  }
}
