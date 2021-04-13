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

  subscribe(id: number, userDetails: UserDetails): Observable<any> {
    return this.httpClient.post(API_USER + 'subscribe/' + id, userDetails);
  }

  unsubscribe(id: number): Observable<any> {
    return this.httpClient.delete(API_USER + 'unsubscribe/' + id);
  }

  getFollowings(id: number, params: any): Observable<any> {
  	return this.httpClient.get<any>(API_USER + 'followings/' + id, { params });
  }

  getFollowers(id: number, params: any): Observable<any> {
  	return this.httpClient.get<any>(API_USER + 'followers/' + id, { params });
  }

  update(id: number, formData: FormData): Observable<any> {
    console.log('SERVICE UPDATE USER');
    return this.httpClient.post(API_USER + id, formData);
  }
}
