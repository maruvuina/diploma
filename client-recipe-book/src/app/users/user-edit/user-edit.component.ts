import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormControl, FormArray, FormBuilder } from '@angular/forms';
import { UserService } from '../shared/services/user.service';
import { UserUpdate } from '../shared/models/user-update';
import { ActivatedRoute, ParamMap } from '@angular/router';

function fileUploaded() {
  document.getElementById("uploadButton").addEventListener("click", function(){
  document.getElementById("file").click();
  });

  let file = <HTMLInputElement>document.getElementById("file");
  file.addEventListener("change", function(){
    var fullPath = this.value;
    var fileName = fullPath.split(/(\\|\/)/g).pop();
    document.getElementById("fileName").innerHTML = fileName;
  }, false);
}

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.css']
})
export class UserEditComponent implements OnInit {

  selectedFile: File = null;
  
  userEditForm: FormGroup;

  id: number;

  constructor(private userService: UserService, 
    private route: ActivatedRoute) { 
    this.id = +this.route.snapshot.paramMap.get('id');
  }

  ngOnInit(): void {
  	fileUploaded();

    this.userEditForm = new FormGroup({
      username: new FormControl('', Validators.required)
    });
  }

  onUploadFile(event) {
    this.selectedFile = <File>event.target.files[0];
  }

  edit() {
    let userUpdate = new UserUpdate();
    userUpdate.fullName = this.userEditForm.get('username').value;
    let formData = new FormData();
    formData.append("file", this.selectedFile);
    formData.append("userDto", JSON.stringify(userUpdate));
    this.userService.update(this.id, formData).subscribe(data => {
      console.log("GGGG");
    });
  }

}
