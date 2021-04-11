import { Directive, OnInit, Input, OnDestroy } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { DomSanitizer } from '@angular/platform-browser';
import { takeUntil } from 'rxjs/operators';
import { throwError, ReplaySubject } from 'rxjs';

const API_RECIPES = 'http://localhost:8080/api/recipes/';

@Directive({
  selector: '[appRecipeImage]',
  host: {
    '[src]': 'sanitizedImageData'
  }
})
export class RecipeImageDirective implements OnInit, OnDestroy {

  imageData: any;

  sanitizedImageData: any;

  @Input('appRecipeImage') recipeId: number;

  destroy: ReplaySubject<any> = new ReplaySubject<any>(1);

  constructor(private httpClient: HttpClient, 
  	private sanitizer: DomSanitizer) { }

  ngOnInit() {        
    this.httpClient.get(API_RECIPES + 'image/' + this.recipeId)
      .pipe(takeUntil(this.destroy))
      .subscribe(
        data => {
          this.imageData = 'data:image/png;base64,' + data;
          this.sanitizedImageData = this.sanitizer.bypassSecurityTrustResourceUrl(this.imageData);
        }, error => {
          throwError(error);
      });
  }

  ngOnDestroy(): void {
    this.destroy.next(null);
    this.destroy.complete();
  }

}
