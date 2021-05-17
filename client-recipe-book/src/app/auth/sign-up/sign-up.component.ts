import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { SignupRequestPayload } from '../shared/models/signup-request.payload';
import { AuthService } from '../../auth/shared/services/auth.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { throwError, ReplaySubject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit, OnDestroy {

  signupForm: FormGroup;

  signupRequestPayload: SignupRequestPayload;

  destroy: ReplaySubject<any> = new ReplaySubject<any>(1);

  constructor(private authService: AuthService, 
    private router: Router, 
    private toastr: ToastrService) { 
  	this.signupRequestPayload = {
      fullname: '',
      email: '',
      password: ''
    };
  }

  ngOnInit(): void {
  	this.signupForm = new FormGroup({
      fullname: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required, 
        Validators.pattern("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\S+$).{8,}")])
    });
  }

  signup() {
  	this.signupRequestPayload.fullname = this.signupForm.get('fullname').value;
    this.signupRequestPayload.email = this.signupForm.get('email').value;
    this.signupRequestPayload.password = this.signupForm.get('password').value;
    this.authService.signup(this.signupRequestPayload)
    .pipe(takeUntil(this.destroy))
    .subscribe(response => {
      if (!response) {
        this.toastr.info('Пожалуйста, проверьте свою почту, чтобы активировать учетную запись.');
      }
      this.router.navigate(['/login'], { queryParams: { registered: 'true' } });
    }, error => {
      this.toastr.error('Регистрация прошла неудачно. Пожалуйста попробуйте ещё раз.');
      throwError(error);
    });
    this.signupForm.reset();
  }

  ngOnDestroy(): void {
    this.destroy.next(null);
    this.destroy.complete();
  }
}
