import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CommentPayload } from '../../../comment/shared/models/comment-payload';
import { CommentModel } from '../../../comment/shared/models/comment-model';

const API_COMMENTS = 'http://localhost:8080/api/recipes/';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private httpClient: HttpClient) { }

  create(id: number, commentPayload: CommentPayload): Observable<CommentModel> {
    return this.httpClient
    .post<CommentModel>(API_COMMENTS + id + '/comments', commentPayload);
  }

  getAllCommentsForRecipe(id: number): Observable<Array<CommentModel>> {
  	return this.httpClient
    .get<Array<CommentModel>>(API_COMMENTS + id + '/comments');
  }

  replyOnComment(id: number, commentPayload: CommentPayload): Observable<CommentModel> {
    return this.httpClient
    .post<CommentModel>(API_COMMENTS + id + '/comments/reply', commentPayload);
  }
}
