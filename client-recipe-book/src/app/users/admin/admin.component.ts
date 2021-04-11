import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  isProfile: boolean = true;

  isUsers: boolean = false;

  isRecipes: boolean = false;

  constructor() { }

  ngOnInit(): void {
  }

  changeAdminAppearance(value) {
  	if (value == 'profile') {
  		console.log("this.isProfile");
  		console.log(value);
  		this.isProfile = true;
  		this.isUsers = false;
  		this.isRecipes = false;
  	} else if (value == 'users') {
  		console.log("this.isUsers");
  		console.log(value);
  		this.isUsers = true;
  		this.isProfile = false;
  		this.isRecipes = false;
  	} else if (value == 'recipes') {
  		console.log("this.isRecipes");
  		console.log(value);
  		this.isRecipes = true;
  		this.isUsers = false;
  		this.isProfile = false;	
  	}
  }

}
