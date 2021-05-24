import { Directive, OnInit, OnDestroy, Input, HostBinding } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { DomSanitizer } from '@angular/platform-browser';
import { UserService } from '../../../users/shared/services/user.service';
import { SafeResourceUrl } from '@angular/platform-browser';
import { takeUntil } from 'rxjs/operators';
import { throwError, ReplaySubject } from 'rxjs';


@Directive({
  selector: '[appUserImage]'
})
export class UserImageDirective implements OnInit, OnDestroy {

  imageData: string;

  @HostBinding('src')
  sanitizedImageData: SafeResourceUrl;
  
  @Input('appUserImage') userId: number;

  destroy: ReplaySubject<any> = new ReplaySubject<any>(1);

  constructor(private httpClient: HttpClient, 
  	private sanitizer: DomSanitizer, 
    private userService: UserService) { }

  ngOnInit() {
    this.getUserAvatar();
  }

  getUserAvatar() {
    this.userService.getUserAvatar(this.userId)
      .pipe(takeUntil(this.destroy))
      .subscribe(
        data => {
          this.imageData = 'data:image/png;base64,' + data;
          this.sanitizedImageData = this.sanitizer.bypassSecurityTrustResourceUrl(this.imageData);
        }
      );
  }

  ngOnDestroy(): void {
    this.destroy.next(null);
    this.destroy.complete();
  }
}
