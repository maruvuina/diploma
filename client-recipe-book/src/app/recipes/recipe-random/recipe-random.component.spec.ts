import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecipeRandomComponent } from './recipe-random.component';

describe('RandomRecipeComponent', () => {
  let component: RecipeRandomComponent;
  let fixture: ComponentFixture<RecipeRandomComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RecipeRandomComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RecipeRandomComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
