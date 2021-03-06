import { Component, OnInit, Input, Output, EventEmitter, OnDestroy } from '@angular/core';
import { UserDetails } from '../shared/models/user-details';
import { UserService } from '../shared/services/user.service';
import { takeUntil } from 'rxjs/operators';
import { ReplaySubject, throwError } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-followers',
  templateUrl: './followers.component.html',
  styleUrls: ['./followers.component.css']
})
export class FollowersComponent implements OnInit, OnDestroy {

  @Input() id: number;

  followers: Array<UserDetails>;

  isFollowers: boolean = false;

  config: any;

  followersCount: number = 0;

  destroy: ReplaySubject<any> = new ReplaySubject<any>(1);

  constructor(private userService: UserService, 
    private router: Router) {
  	this.config = {
      itemsPerPage: 3,
      currentPage: 1,
      totalItems: 0,
      id: 'paginationFollowers'
    }; 
  }

  ngOnInit(): void {
    this.getFollowers();
  }

  updateFollowers() {
    this.getFollowers();
  }

  getFollowersCount() {
    return this.followersCount;
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

  getFollowers() {
    const params = this.getRequestParams(this.config.currentPage, 
      this.config.itemsPerPage);
    this.userService.getFollowers(this.id, params)
    .pipe(takeUntil(this.destroy))
    .subscribe(response => {
      const { followers, totalItems } = response;
      let followersCount = followers.length;
      if (followersCount != 0) {
        this.isFollowers = true;
        this.followersCount = followersCount;
      }
      this.followers = followers;
      this.config.totalItems = totalItems;   
    }, error => {
      throwError(error);
    });
  }

  handlePageChange(event: number): void {
    this.config.currentPage = event;
    this.getFollowers(); 
  }

  goToAuthor(idAuthor: number) {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.router.onSameUrlNavigation = 'reload';
    this.router.navigate(['/users', idAuthor]);
  }

  ngOnDestroy(): void {
    this.destroy.next(null);
    this.destroy.complete();
  }
}
