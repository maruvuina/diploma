import { Component, OnInit, OnDestroy } from '@angular/core';
import { TagService } from '../../tags/shared/services/tag.service';
import { TagModel } from '../../tags/shared/models/tag-model';
import { takeUntil } from 'rxjs/operators';
import { throwError, ReplaySubject } from 'rxjs';
import { FormGroup, Validators, FormControl } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-tag-admin-list',
  templateUrl: './tag-admin-list.component.html',
  styleUrls: ['./tag-admin-list.component.css']
})
export class TagAdminListComponent implements OnInit, OnDestroy {

  tagForm: FormGroup;

  tagList: Array<TagModel> = [];

  config: any;

  destroy: ReplaySubject<any> = new ReplaySubject<any>(1);

  constructor(private tagService: TagService, 
    private toastr: ToastrService) {
    this.config = {
      itemsPerPage: 15,
      currentPage: 1,
      totalItems: 0,
      id: 'tagsAdmin'
    }; 
  }

  ngOnInit(): void {
    this.tagForm = new FormGroup({
      tagName: new FormControl('', Validators.required)
    });
    this.getAllTags();
  }

  addTag() {
    let tag = new TagModel();
    tag.tagName = this.tagForm.get('tagName').value;
    this.tagService.save(tag)
    .pipe(takeUntil(this.destroy))
    .subscribe(() => {
      this.toastr.success("Тег добавлен.");
      this.getAllTags();
    }, error => {
      throwError(error);
    });
    this.tagForm.reset();
  }

  getAllTags() {
    this.tagService.getAll()
    .pipe(takeUntil(this.destroy))
    .subscribe(tagList => {
      this.tagList = tagList;
    }, error => {
      throwError(error);
    });
  }

  handlePageChange(event: number): void {
    this.config.currentPage = event;
    this.getAllTags(); 
  }

  ngOnDestroy(): void {
    this.destroy.next(null);
    this.destroy.complete();
  }
}
