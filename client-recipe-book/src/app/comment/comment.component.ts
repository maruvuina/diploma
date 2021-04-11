import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { CommentModel } from './shared/models/comment-model';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { CommentPayload } from './shared/models/comment-payload';
import { CommentService } from './shared/services/comment.service';
import { RecipeModel } from '../recipes/shared/models/recipe-model';
import { throwError, ReplaySubject } from 'rxjs';
import { AuthService } from '../auth/shared/services/auth.service';
import { ToastrService } from 'ngx-toastr';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent implements OnInit, OnDestroy {

  isComments: boolean = false;

  comments: Array<CommentModel>;

  commentForm: FormGroup;

  commentPayload: CommentPayload;

  _recipe: RecipeModel;

  isReply: boolean = false;

  isAddComment: boolean = true;

  currentIdComment: number = null;

  visibility:boolean = true;

  destroy: ReplaySubject<any> = new ReplaySubject<any>(1);

  constructor(private commentService: CommentService, 
    private authService: AuthService, 
    private toastr: ToastrService) { 
  	this.comments = [];
    this.commentForm = new FormGroup({
      text: new FormControl('', Validators.required)
    });
    this.commentPayload = {
      content: '',
      idParent: null
    };
  }

  @Input()
  set recipe(recipe: RecipeModel) {
    this._recipe = recipe;
  }

  get recipe() { return this._recipe; }

  ngOnInit(): void {
  	this.comments = this.recipe.comments
    .filter(comment => comment.idParent == null);
  	if (this.comments.length != 0) {
  		this.isComments = true;		
  	}
  }

  addComment() {
    if (this.authService.isLoggedIn()) {
      this.commentPayload.content = this.commentForm.get('text').value;
      this.commentService.create(this.recipe.idRecipe, this.commentPayload)
      .pipe(takeUntil(this.destroy))
      .subscribe(comment => {
        this.commentForm.get('text').setValue('');
        this.getCommentsForRecipe();
      }, error => {
        throwError(error);
      });
    } else {
      this.toastr.warning("Только зарегистрированные пользователи могут комментировать.");
    }
  }

  getCommentsForRecipe() {
    this.commentService.getAllCommentsForRecipe(this.recipe.idRecipe)
    .pipe(takeUntil(this.destroy))
    .subscribe(comments => {
      this.comments = comments;
    }, error => {
      throwError(error);
    });
  }

  reply(idComment: number) {
    this.isReply = true;
    this.isAddComment = false;
    this.currentIdComment = idComment;
    this.visibility = false;
    
    // if (this.authService.isLoggedIn()) {
    //   this.isReply = true;
    // } else {
    //   this.toastr.warning("Только зарегистрированные пользователи могут комментировать.");
    // }
  }

  commentReply(idComment: number) {
    this.commentPayload.content = this.commentForm.get('text').value;
    this.commentPayload.idParent = idComment;
    this.commentService.replyOnComment(this.recipe.idRecipe, this.commentPayload)
    .pipe(takeUntil(this.destroy))
    .subscribe(comment => {
        this.commentForm.get('text').setValue('');
        this.getCommentsForRecipe();
      }, error => {
        throwError(error);
      });
  }

  add() { 
    this.isAddComment = true;
    this.isReply = false;
    this.visibility = true;
  }

  ngOnDestroy(): void {
    this.destroy.next(null);
    this.destroy.complete();
  }


}
