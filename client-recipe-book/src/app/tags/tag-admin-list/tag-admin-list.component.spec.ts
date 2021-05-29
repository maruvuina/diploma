import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TagAdminListComponent } from './tag-admin-list.component';

describe('TagAdminListComponent', () => {
  let component: TagAdminListComponent;
  let fixture: ComponentFixture<TagAdminListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TagAdminListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TagAdminListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
