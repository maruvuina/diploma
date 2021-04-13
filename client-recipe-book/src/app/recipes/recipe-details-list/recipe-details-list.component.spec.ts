import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecipeDetailsListComponent } from './recipe-details-list.component';

describe('RecipeDetailsListComponent', () => {
  let component: RecipeDetailsListComponent;
  let fixture: ComponentFixture<RecipeDetailsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RecipeDetailsListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RecipeDetailsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
