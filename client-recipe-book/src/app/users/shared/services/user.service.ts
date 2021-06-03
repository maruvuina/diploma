import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserModel } from '../models/user-model';
import { UserDetails } from '../models/user-details';
import { UserUpdate } from '../models/user-update';
import { ContactPayload } from '../../../contact/shared/contact-payload';

const API_USERS = 'http://localhost:8080/api/users/';

const API_MAIL = 'http://localhost:8080/api/sending/';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) { }

  getUserAvatar(id: number): Observable<string> {
    return this.httpClient.get<string>(API_USERS + 'image/' + id);
  }

  getById(id: number): Observable<UserModel> {
  	return this.httpClient.get<UserModel>(API_USERS + id);
  }

  subscribe(id: number, userDetails: UserDetails): Observable<any> {
    return this.httpClient.post(API_USERS + 'subscribe/' + id, userDetails);
  }

  getFollowings(id: number, params: any): Observable<any> {
  	return this.httpClient.get<any>(API_USERS + 'followings/' + id, { params });
  }

  getFollowers(id: number, params: any): Observable<any> {
  	return this.httpClient.get<any>(API_USERS + 'followers/' + id, { params });
  }

  update(id: number, formData: FormData): Observable<any> {
    return this.httpClient.post(API_USERS + id, formData);
  }

  isSubscribed(idFollowing: number): Observable<boolean>  {
    return this.httpClient.get<boolean>(API_USERS + 'subscribe/' + idFollowing);
  }

  isMailing(): Observable<boolean>  {
    return this.httpClient.get<boolean>(API_USERS + 'mailing/');
  }

  subscribeToNewsletter(contactPayload: ContactPayload): Observable<any> {
    return this.httpClient.post<boolean>(API_MAIL + 'mailing/', contactPayload);
  }

  unsubscribeToNewsletter(contactPayload: ContactPayload): Observable<any> {
    return this.httpClient.post<boolean>(API_MAIL + 'unmailing/', contactPayload);
  }

  getAll(params: any): Observable<any> {
    return this.httpClient.get<any>(API_USERS, { params });
  }

  delete(id: number): Observable<any> {
    return this.httpClient.delete<any>(API_USERS + id);
  }

  existsByEmail(email: string): Observable<boolean> {
    return this.httpClient.get<boolean>(API_USERS + 'existsByEmail/?email=' + email);
  }
}
