import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { UserDetails } from '../shared/models/user-details';
import { UserService } from '../shared/services/user.service';
import { takeUntil } from 'rxjs/operators';
import { ReplaySubject, throwError } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-followings',
  templateUrl: './followings.component.html',
  styleUrls: ['./followings.component.css']
})
export class FollowingsComponent implements OnInit, OnDestroy {

  @Input() id: number;

  followings: Array<UserDetails>;

  isFollowings: boolean = false;

  followingsCount: number = 0;

  config: any;

  destroy: ReplaySubject<any> = new ReplaySubject<any>(1);

  constructor(private userService: UserService, 
    private router: Router) {
  	this.config = {
      itemsPerPage: 3,
      currentPage: 1,
      totalItems: 0
    }; 
  }

  ngOnInit(): void {
  	this.getFollowings();
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

  getFollowings() {
    const params = this.getRequestParams(this.config.currentPage, 
      this.config.itemsPerPage);
    this.userService.getFollowings(this.id, params)
    .pipe(takeUntil(this.destroy))
    .subscribe(response => {
      const { followings, totalItems } = response;
      let followingsCount = followings.length;
      if (followingsCount != 0) {
        this.isFollowings = true;
      }
      this.followings = followings;
      this.config.totalItems = totalItems;      
    }, error => {
      throwError(error);
    });
  }

  handlePageChangeFollowings(event: number): void {
    this.config.currentPage = event;
    this.getFollowings(); 
  }

  goToAuthor(idAuthor: number) {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.router.onSameUrlNavigation = 'reload';
    this.router.navigate(['/users/' + idAuthor]);
  }

  ngOnDestroy(): void {
    this.destroy.next(null);
    this.destroy.complete();
  }
}
