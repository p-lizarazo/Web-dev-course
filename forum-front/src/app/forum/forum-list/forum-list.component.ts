import { Component, OnInit } from '@angular/core';
import { Forum } from 'src/app/models/forum';
import { ForumsServiceService } from 'src/app/services/forums-service.service';
import { UserGlobalService } from 'src/app/services/user-global.service';

@Component({
  selector: 'app-forum-list',
  templateUrl: './forum-list.component.html',
  styleUrls: ['./forum-list.component.scss']
})
export class ForumListComponent implements OnInit {
  forums: Forum[] = [];
  constructor(private forumService: ForumsServiceService, public userGlobal: UserGlobalService) {
  }

  ngOnInit(): void {
    this.findForums();
  }

  findForums(): void
  {
    this.forumService.findAll().subscribe(
      results => {
        console.log(results);
        this.forums = results;
      },
      error => console.error(error)
    );
  }

  delete(id: number): void
  {
    this.forumService.deleteForum(id).subscribe(
      results => {
        this.findForums();
      },
      error => console.error(error)
    );
  }

}
