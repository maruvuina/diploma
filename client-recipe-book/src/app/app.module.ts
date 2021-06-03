import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgxWebstorageModule } from 'ngx-webstorage';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { TokenInterceptor } from './token-interceptor';
import { AutocompleteLibModule } from 'angular-ng-autocomplete';
import { NgxPaginationModule } from 'ngx-pagination';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NgSelectModule } from '@ng-select/ng-select';
import { AuthGuard } from './auth/auth.guard';
import { ExitGuard } from './auth/exit.guard';

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { FooterComponent } from './footer/footer.component';
import { NavComponent } from './nav/nav.component';
import { LoginComponent } from './auth/login/login.component';
import { SignUpComponent } from './auth/sign-up/sign-up.component';
import { RecipeCreateComponent } from './recipes/recipe-create/recipe-create.component';
import { RecipeComponent } from './recipes/recipe/recipe.component';
import { RecipeListComponent } from './recipes/recipe-list/recipe-list.component';
import { RecipeImageDirective } from './recipes/shared/directives/recipe-image.directive';
import { UserImageDirective } from './auth/shared/directives/user-image.directive';
import { CategoryComponent } from './categories/category/category.component';
import { RecipeRandomComponent } from './recipes/recipe-random/recipe-random.component';
import { ContactComponent } from './contact/contact.component';
import { AboutComponent } from './about/about.component';
import { UserComponent } from './users/user/user.component';
import { AdminComponent } from './users/admin/admin.component';
import { UserEditComponent } from './users/user-edit/user-edit.component';
import { RecipeEditComponent } from './recipes/recipe-edit/recipe-edit.component';
import { RecipeSearchByIngredientComponent } from './recipes/recipe-search-by-ingredient/recipe-search-by-ingredient.component';
import { LikeComponent } from './like/like.component';
import { CommentComponent } from './comment/comment.component';
import { UserShowComponent } from './users/user-show/user-show.component';
import { UserListComponent } from './users/user-list/user-list.component';
import { AdminProfileComponent } from './users/admin-profile/admin-profile.component';
import { RecipeAdminListComponent } from './recipes/recipe-admin-list/recipe-admin-list.component';
import { FollowersComponent } from './users/followers/followers.component';
import { FollowingsComponent } from './users/followings/followings.component';
import { RecipeDetailsListComponent } from './recipes/recipe-details-list/recipe-details-list.component';
import { TagComponent } from './tags/tag/tag.component';
import { NewsletterComponent } from './newsletter/newsletter.component';
import { CuisineComponent } from './cuisines/cuisine/cuisine.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { TagAdminListComponent } from './tags/tag-admin-list/tag-admin-list.component';
import { CuisineAdminListComponent } from './cuisines/cuisine-admin-list/cuisine-admin-list.component';
import { IngredientAdminListComponent } from './ingredients/ingredient-admin-list/ingredient-admin-list.component';
import { UserAdminListComponent } from './users/user-admin-list/user-admin-list.component';

const appRoutes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignUpComponent },
  { path: 'recipes', 
    component: RecipeCreateComponent, 
    canActivate: [AuthGuard],
    canDeactivate: [ExitGuard] },
  { path: 'recipes/:id', component: RecipeComponent },
  { path: 'random-recipe', component: RecipeRandomComponent },
  { path: 'contact', component: ContactComponent },
  { path: 'about', component: AboutComponent },
  { path: 'users/account/user/:id', 
    component: UserComponent, 
    canActivate: [AuthGuard], 
    data: {
      role: 'ROLE_USER'
    } 
  },
  { path: 'users/account/admin/:id', 
    component: AdminComponent, 
    canActivate: [AuthGuard], 
    data: {
      role: 'ROLE_ADMIN'
    } 
  },
  { path: 'users/edit/:id', 
    component: UserEditComponent, 
    canActivate: [AuthGuard], 
    data: {
      role: 'ROLE_USER'
    },
    canDeactivate: [ExitGuard] 
  },
  { path: 'recipes/edit/:id', 
    component: RecipeEditComponent, 
    canActivate: [AuthGuard],
    canDeactivate: [ExitGuard] 
  },
  { path: 'recipes/search/ingredient', component: RecipeSearchByIngredientComponent },
  { path: 'users/:id', component: UserShowComponent },
  { path: 'user-list', component: UserListComponent },
  { path: 'categories', component: CategoryComponent },
  { path: 'tags', component: TagComponent },
  { path: 'cuisines', component: CuisineComponent },
  { path: '**', component: NotFoundComponent }
]

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    FooterComponent,
    NavComponent,
    LoginComponent,
    SignUpComponent,
    RecipeCreateComponent,
    RecipeComponent,
    RecipeListComponent,
    RecipeImageDirective,
    UserImageDirective,
    CategoryComponent,
    RecipeRandomComponent,
    ContactComponent,
    AboutComponent,
    UserComponent,
    AdminComponent,
    UserEditComponent,
    RecipeEditComponent,
    RecipeSearchByIngredientComponent,
    LikeComponent,
    CommentComponent,
    UserShowComponent,
    UserListComponent,
    AdminProfileComponent,
    RecipeAdminListComponent,
    FollowersComponent,
    FollowingsComponent,
    RecipeDetailsListComponent,
    TagComponent,
    NewsletterComponent,
    CuisineComponent,
    NotFoundComponent,
    TagAdminListComponent,
    CuisineAdminListComponent,
    IngredientAdminListComponent,
    UserAdminListComponent
  ],
  imports: [
    BrowserModule, 
    FormsModule, 
    RouterModule.forRoot(appRoutes), 
    ReactiveFormsModule, 
    HttpClientModule, 
    NgxWebstorageModule.forRoot(), 
    BrowserAnimationsModule, 
    ToastrModule.forRoot(), 
    AutocompleteLibModule, 
    NgxPaginationModule, 
    FontAwesomeModule, 
    NgSelectModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
