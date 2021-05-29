import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IngredientAdminListComponent } from './ingredient-admin-list.component';

describe('IngredientAdminListComponent', () => {
  let component: IngredientAdminListComponent;
  let fixture: ComponentFixture<IngredientAdminListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IngredientAdminListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(IngredientAdminListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
