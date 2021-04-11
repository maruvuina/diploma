import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecipeSearchByIngredientComponent } from './recipe-search-by-ingredient.component';

describe('RecipeSearchByIngredientComponent', () => {
  let component: RecipeSearchByIngredientComponent;
  let fixture: ComponentFixture<RecipeSearchByIngredientComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RecipeSearchByIngredientComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RecipeSearchByIngredientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
