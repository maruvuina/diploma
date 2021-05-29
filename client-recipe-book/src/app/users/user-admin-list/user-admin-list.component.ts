import { Component, OnInit, OnDestroy } from '@angular/core';
import { UserModel } from '../shared/models/user-model';
import { UserService } from '../shared/services/user.service';
import { Router } from '@angular/router';
import { throwError, ReplaySubject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-user-admin-list',
  templateUrl: './user-admin-list.component.html',
  styleUrls: ['./user-admin-list.component.css']
})
export class UserAdminListComponent implements OnInit, OnDestroy {

  users: Array<UserModel> = [];

  config: any;

  destroy: ReplaySubject<any> = new ReplaySubject<any>(1);

  constructor(private userService: UserService, 
    private router: Router) { 
    this.config = {
      itemsPerPage: 9,
      currentPage: 1,
      totalItems: 0,
      id: 'usersAdmin'
    };
  }

  ngOnInit(): void {
    this.getAllUsers();
  }

  getRequestParams(page: number, pageSize: number): any {
    let params: any = {};
    if (page) {
      params[`page`] = page - 1;
    }
    if (pageSize) {
      params[`size`] = pageSize;
    }
    return params;
  }

  getAllUsers() {
    const params = this.getRequestParams(this.config.currentPage, 
      this.config.itemsPerPage);
      this.userService.getAll(params)
      .pipe(takeUntil(this.destroy))
      .subscribe(response => {
      const { users, totalItems } = response;
      this.users = users;
      this.config.totalItems = totalItems;
    }, error => {
      throwError(error);
    });
  }

  handlePageChange(event: number): void {
    this.config.currentPage = event;
    this.getAllUsers();
  }

  goToUser(idUser: number) {
    this.router.navigate(['/users', idUser]);
  }

  ngOnDestroy(): void {
    this.destroy.next(null);
    this.destroy.complete();
  }
}
