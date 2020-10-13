import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { switchMap } from 'rxjs/operators';
import { Forum } from 'src/app/models/forum';
import { ForumsServiceService } from 'src/app/services/forums-service.service';

@Component({
  selector: 'app-forum-edit',
  templateUrl: './forum-edit.component.html',
  styleUrls: ['./forum-edit.component.scss']
})
export class ForumEditComponent implements OnInit {
  forum: Forum = null;

  errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private forumService: ForumsServiceService
  ) {}

  ngOnInit(): void {
    this.route.paramMap
      .pipe(
        switchMap(params => this.forumService.findById(+params.get('id')))
      )
      .subscribe(result => {
        this.forum = result;
      });
  }

  update(): void {
    console.log(this.forum);
    this.forumService.update(this.forum).subscribe(
      result => {
        console.log(result);
        this.router.navigate([`/forum`]);
      },
      error => {
        console.error(error);
        this.errorMessage = error.toString();
      }
    );
  }

  cancel(): void {
    this.router.navigate([`/forum`]);
  }

}
