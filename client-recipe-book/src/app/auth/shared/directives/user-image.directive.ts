import { Directive, OnInit, Input } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { DomSanitizer } from '@angular/platform-browser';

const API_GET_AVATAR = 'http://localhost:8080/api/users/image/';

@Directive({
  selector: '[appUserImage]',
  host: {
    '[src]': 'sanitizedImageData'
  }
})
export class UserImageDirective implements OnInit {

  imageData: any;

  sanitizedImageData: any;
  
  @Input('appUserImage') userId: number;

  constructor(private httpClient: HttpClient, 
  	private sanitizer: DomSanitizer) { }

  ngOnInit() {
    this.getUserAvatar();
  }

  getUserAvatar() {
    this.httpClient.get(API_GET_AVATAR + this.userId)
      .pipe()
      .subscribe(
        data => {
          this.imageData = 'data:image/png;base64,' + data;
          this.sanitizedImageData = this.sanitizer.bypassSecurityTrustResourceUrl(this.imageData);
        }
      );
  }

}
