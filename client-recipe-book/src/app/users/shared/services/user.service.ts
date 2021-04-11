import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserModel } from '../models/user-model';
import { UserDetails } from '../models/user-details';
import { UserUpdate } from '../models/user-update';

const API_USER = 'http://localhost:8080/api/users/';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) { }

  getById(id: number): Observable<UserModel> {
  	return this.httpClient.get<UserModel>(API_USER + id);
  }

  subscribe(id: number, userModel: UserModel): Observable<any> {
    return this.httpClient.post(API_USER + 'subscribe/' + id, userModel);
  }

  unsubscribe(id: number): Observable<any> {
    return this.httpClient.delete(API_USER + 'unsubscribe/' + id);
  }

  getFollowings(id: number): Observable<Array<UserDetails>> {
  	return this.httpClient.get<Array<UserDetails>>(API_USER + 'following/' + id);
  }

  getFollowers(id: number): Observable<Array<UserDetails>> {
  	return this.httpClient.get<Array<UserDetails>>(API_USER + 'follower/' + id);
  }

  update(id: number, formData: FormData): Observable<any> {
    console.log('SERVICE UPDATE USER');
    return this.httpClient.post(API_USER + id, formData);
  }
}
