import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { Forum } from 'src/app/models/forum';
import { ForumsServiceService } from 'src/app/services/forums-service.service';

@Component({
  selector: 'app-forum-create',
  templateUrl: './forum-create.component.html',
  styleUrls: ['./forum-create.component.scss']
})
export class ForumCreateComponent implements OnInit {
  @ViewChild('createForm', { static: true })
  createForm;

  submitted = false;

  forum: Forum = new Forum(
    undefined,
    undefined,
    undefined,
    undefined
  );

  errorMessage = '';

  constructor(
    private router: Router,
    private forumService: ForumsServiceService
  ){}

  ngOnInit(): void {
  }

  create(): void {
    this.submitted = true;
    console.log(this.forum);
    this.forumService.create(this.forum).subscribe(
      result => {
        console.log(result);
        this.router.navigate([`/forum`]);
      },
      error => {
        console.error(error);
        this.errorMessage = error.toString();
        this.submitted = false;
      }
    );
  }

  cancel(): void {
    this.router.navigate([`/forum`]);
  }

  get canSubmit(): boolean{
    return this.createForm.form.valid && !this.submitted;
  }
}
