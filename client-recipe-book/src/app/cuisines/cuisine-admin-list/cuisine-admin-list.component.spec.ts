import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CuisineAdminListComponent } from './cuisine-admin-list.component';

describe('CuisineAdminListComponent', () => {
  let component: CuisineAdminListComponent;
  let fixture: ComponentFixture<CuisineAdminListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CuisineAdminListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CuisineAdminListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
