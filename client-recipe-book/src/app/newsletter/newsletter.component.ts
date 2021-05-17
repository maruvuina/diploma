import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AuthService } from '../auth/shared/services/auth.service';
import { ToastrService } from 'ngx-toastr';
import { ContactPayload } from '../contact/shared/contact-payload';
import { ContactService } from '../contact/shared/services/contact.service';
import { takeUntil } from 'rxjs/operators';
import { throwError, ReplaySubject } from 'rxjs';

@Component({
  selector: 'app-newsletter',
  templateUrl: './newsletter.component.html',
  styleUrls: ['./newsletter.component.css']
})
export class NewsletterComponent implements OnInit, OnDestroy {

  mailingForm: FormGroup;

  destroy: ReplaySubject<any> = new ReplaySubject<any>(1);

  constructor(private authService: AuthService, 
  	private toastr: ToastrService, 
  	private contactService: ContactService) { }

  ngOnInit(): void {
  	this.mailingForm = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.email])
    });
  }

  mailing() {
  	if (this.authService.isLoggedIn()) {
  		let contactData = new ContactPayload();
	  	contactData.fromEmail = this.mailingForm.get('email').value;
	  	this.contactService.mailing(contactData)
	    .pipe(takeUntil(this.destroy))
	    .subscribe(data => {
	      this.toastr.success("Подписка оформлена.");
	    }, error => {
	      throwError(error);
	    });
	    this.mailingForm.reset();
  	} else {
      this.toastr.warning("Только зарегистрированные пользователи могут подписаться на рассылку.");
    }
  }

  ngOnDestroy(): void {
    this.destroy.next(null);
    this.destroy.complete();
  }
}
