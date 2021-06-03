import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AbstractControl, AsyncValidatorFn, ValidationErrors } from "@angular/forms";
import { SignupRequestPayload } from '../shared/models/signup-request.payload';
import { AuthService } from '../../auth/shared/services/auth.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { throwError, ReplaySubject, Observable } from 'rxjs';
import { takeUntil, map } from 'rxjs/operators';
import { UserService } from '../../users/shared/services/user.service';

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
    private toastr: ToastrService, 
    private userService: UserService) { 
  	this.signupRequestPayload = {
      fullname: '',
      email: '',
      password: ''
    };
  }

  ngOnInit(): void {
  	this.signupForm = new FormGroup({
      fullname: new FormControl('', Validators.required),
      email: new FormControl('', {
        validators: [Validators.required, Validators.email], 
        asyncValidators: [this.existingEmailValidator()], 
        updateOn: 'blur' 
      }),
      password: new FormControl('', [Validators.required, 
        Validators.pattern("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}")])
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

  existingEmailValidator(): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      return this.userService.existsByEmail(control.value).pipe(
        map(response => {
          return response ? { emailExists: true } : null;
        })
      );
    };
  }

  ngOnDestroy(): void {
    this.destroy.next(null);
    this.destroy.complete();
  }
}
