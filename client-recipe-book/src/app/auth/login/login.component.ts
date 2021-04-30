import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { LoginRequestPayload } from '../shared/models/login-request.payload';
import { AuthService } from '../../auth/shared/services/auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Observable, throwError, ReplaySubject } from 'rxjs';
import { takeUntil, map } from 'rxjs/operators';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {

  loginForm: FormGroup;

  loginRequestPayload: LoginRequestPayload;
  
  isError: boolean;

  active: boolean;

  destroy: ReplaySubject<any> = new ReplaySubject<any>(1);

  constructor(private authService: AuthService, 
  	private activatedRoute: ActivatedRoute,
    private router: Router, 
    private toastr: ToastrService) {
    this.loginRequestPayload = {
      username: '',
      password: ''
    };
  }

  ngOnInit(): void {
  	this.loginForm = new FormGroup({
      username: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', Validators.required)
    });

    this.activatedRoute.queryParams
    .pipe(takeUntil(this.destroy))
    .subscribe(params => {
      if (params.registered !== undefined && params.registered === 'true') {
        this.toastr.success('Вы успешно зарегистрировались');
      }
    });
  }

  login() {
    let username = this.loginForm.get('username').value;
    this.loginRequestPayload.username = username;
    this.loginRequestPayload.password = this.loginForm.get('password').value;

    this.authService.isUserActive(username)
    .pipe(takeUntil(this.destroy))
    .subscribe(response => {
      if (response) {
        this.authService.login(this.loginRequestPayload)
        .pipe(takeUntil(this.destroy))
        .subscribe(() => {
          this.isError = false;
          this.toastr.success('Вы успешно авторизовались');
          this.router.navigateByUrl('');
        }, error => {
          this.isError = true;
          throwError(error);
        });
        this.loginForm.reset();
      } else {
        this.toastr.info('Пожалуйста, проверьте свою почту, чтобы активировать учетную запись.');
      }
    }, error => {
        throwError(error);
      });
  }

  ngOnDestroy(): void {
    this.destroy.next(null);
    this.destroy.complete();
  }
}
