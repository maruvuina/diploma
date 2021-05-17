import { Directive, OnInit, Input, HostBinding } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { DomSanitizer } from '@angular/platform-browser';
import { UserService } from '../../../users/shared/services/user.service';
import { SafeResourceUrl } from '@angular/platform-browser';

@Directive({
  selector: '[appUserImage]'
})
export class UserImageDirective implements OnInit {

  imageData: string;

  @HostBinding('src')
  sanitizedImageData: SafeResourceUrl;
  
  @Input('appUserImage') userId: number;

  constructor(private httpClient: HttpClient, 
  	private sanitizer: DomSanitizer, 
    private userService: UserService) { }

  ngOnInit() {
    this.getUserAvatar();
  }

  getUserAvatar() {
    this.userService.getUserAvatar(this.userId)
      .pipe()
      .subscribe(
        data => {
          this.imageData = 'data:image/png;base64,' + data;
          this.sanitizedImageData = this.sanitizer.bypassSecurityTrustResourceUrl(this.imageData);
        }
      );
  }
}
