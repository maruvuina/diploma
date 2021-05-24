import { Directive, OnInit, Input, OnDestroy, HostBinding } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { DomSanitizer } from '@angular/platform-browser';
import { takeUntil } from 'rxjs/operators';
import { throwError, ReplaySubject } from 'rxjs';
import { SafeResourceUrl } from '@angular/platform-browser';
import { RecipeService } from '../../../recipes/shared/services/recipe.service';


@Directive({
  selector: '[appRecipeImage]'
})
export class RecipeImageDirective implements OnInit, OnDestroy {

  imageData: string;

  @HostBinding('src')
  sanitizedImageData: SafeResourceUrl;

  @Input('appRecipeImage') recipeId: number;

  destroy: ReplaySubject<any> = new ReplaySubject<any>(1);

  constructor(private httpClient: HttpClient, 
  	private sanitizer: DomSanitizer, 
    private recipeService: RecipeService) { }

  ngOnInit() {        
    this.recipeService.getRecipeImage(this.recipeId)
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
