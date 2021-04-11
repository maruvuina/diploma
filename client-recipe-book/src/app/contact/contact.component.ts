import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ContactService } from './shared/services/contact.service';
import { ContactPayload } from './shared/contact-payload';
import { throwError, ReplaySubject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit, OnDestroy {

  contactForm: FormGroup;

  destroy: ReplaySubject<any> = new ReplaySubject<any>(1);

  constructor(private contactService: ContactService) { }

  ngOnInit(): void {
  	this.contactForm = new FormGroup({
      username: new FormControl('', [Validators.required, Validators.email]),
      title: new FormControl('', Validators.required),
      message: new FormControl('', Validators.required)
    });
  }

  sendMessage() {
  	let contactData = new ContactPayload();
  	contactData.fromEmail = this.contactForm.get('username').value;
  	contactData.title = this.contactForm.get('title').value;
  	contactData.message = this.contactForm.get('message').value;
  	this.contactService.sendMessage(contactData)
    .pipe(takeUntil(this.destroy))
    .subscribe(data => {
      console.log('sended message');
    }, error => {
      throwError(error);
    });
  }

  ngOnDestroy(): void {
    this.destroy.next(null);
    this.destroy.complete();
  }

}
