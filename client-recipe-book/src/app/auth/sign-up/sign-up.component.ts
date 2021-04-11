import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { SignupRequestPayload } from '../shared/models/signup-request.payload';
import { AuthService } from '../../auth/shared/services/auth.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {

  signupForm: FormGroup;

  signupRequestPayload: SignupRequestPayload;

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
      password: new FormControl('', Validators.required)
    });
  }

  signup() {
  	this.signupRequestPayload.fullname = this.signupForm.get('fullname').value;
    this.signupRequestPayload.email = this.signupForm.get('email').value;
    this.signupRequestPayload.password = this.signupForm.get('password').value;
    this.authService.signup(this.signupRequestPayload)
      .subscribe(() => {
        this.router.navigate(['/login'], { queryParams: { registered: 'true' } });
      }, () => {
        this.toastr.error('Регистрация прошла неудачно. Пожалуйста попробуйте ещё раз.');
      });
  }

}
