import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ContactPayload } from '../contact-payload';

const API_CONTACT = 'http://localhost:8080/api/sending/';

@Injectable({
  providedIn: 'root'
})
export class ContactService {

  constructor(private httpClient: HttpClient) { }

  sendMessage(contactPayload: ContactPayload): Observable<any> {
    return this.httpClient.post(API_CONTACT + 'contact', contactPayload);
  }

  mailing(contactPayload: ContactPayload): Observable<any> {
  	return this.httpClient.post(API_CONTACT + 'mailing', contactPayload);
  }

  unmailing(contactPayload: ContactPayload): Observable<any> {
  	return this.httpClient.post(API_CONTACT + 'unmailing', contactPayload);
  }
}
