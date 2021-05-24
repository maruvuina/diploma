import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  id: number;

  isProfile: boolean = true;

  isUsers: boolean = false;

  isRecipes: boolean = false;

  isTags: boolean = false;

  isIngredients: boolean = false;

  isCuisines: boolean = false;

  switchCondition: string;

  constructor(private route: ActivatedRoute) { 
    this.id = +this.route.snapshot.paramMap.get('id');
  }

  ngOnInit(): void {
  }

  changeAdminAppearance(value) {
    switch (value) {
      case "users":
        this.isUsers = true;
        this.isProfile = false;
        this.isRecipes = false;
        this.isTags = false;
        this.isIngredients = false;
        this.isCuisines = false;
        break;
      case "recipes":
        this.isRecipes = true;
        this.isUsers = false;
        this.isProfile = false;
        this.isTags = false;
        this.isIngredients = false;
        this.isCuisines = false;
        break;
      case "tags":
        this.isTags = true;
        this.isRecipes = false;
        this.isUsers = false;
        this.isProfile = false;
        this.isIngredients = false;
        this.isCuisines = false;
        break;
      case "ingredients":
        this.isIngredients = true;
        this.isRecipes = false;
        this.isUsers = false;
        this.isProfile = false;
        this.isTags = false;
        this.isCuisines = false;
        break;
      case "cuisines":
        this.isCuisines = true;
        this.isRecipes = false;
        this.isUsers = false;
        this.isProfile = false;
        this.isTags = false;
        this.isIngredients = false;
        break;
      default:
        this.isProfile = true;
        this.isUsers = false;
        this.isRecipes = false;
        this.isTags = false;
        this.isIngredients = false;
        this.isCuisines = false;
        break;
    }
  }
}
