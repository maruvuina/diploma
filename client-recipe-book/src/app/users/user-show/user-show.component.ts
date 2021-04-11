import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormControl, FormArray, FormBuilder } from '@angular/forms';
import { UserModel } from '../shared/models/user-model';
import { UserService } from '../shared/services/user.service';


@Component({
  selector: 'app-user-show',
  templateUrl: './user-show.component.html',
  styleUrls: ['./user-show.component.css']
})
export class UserShowComponent implements OnInit {

 	
  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  subscribe() {
  	let followign = new UserModel();
  	//followign.id = ;
  	//this.userService.subscribe(followign);
  }

}
