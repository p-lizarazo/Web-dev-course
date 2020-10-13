import { Component, OnInit } from '@angular/core';
import { Comment } from 'src/app/models/comment';
import { Topic } from 'src/app/models/topic';
import { ModService } from 'src/app/services/mod.service';
import { UserGlobalService } from 'src/app/services/user-global.service';

@Component({
  selector: 'app-mod',
  templateUrl: './mod.component.html',
  styleUrls: ['./mod.component.scss']
})
export class ModComponent implements OnInit {
  topics: Topic[] = [];
  comments: Comment[] = [];
  constructor(private modService: ModService, public userGlobal: UserGlobalService) {
  }
  ngOnInit(): void {
    this.findTopics();
    this.findComments();
  }

  findTopics(): void
  {
    this.modService.getAllTopicsNoAprobados().subscribe(
      results => {
        this.topics = results;
      },
      error => console.error(error)
    );
  }

  findComments(): void
  {
    this.modService.getAllCommentsNoAprobados().subscribe(
      results => {
        this.comments = results;
      },
      error => console.error(error)
    );
  }

  approveComment(comment: Comment): void
  {
    this.modService.aprobarComment(comment).subscribe(
      result => {
        console.log(result);
        this.findComments();
      }
    );
  }

  approveTopic(topic: Topic): void
  {
    this.modService.aprobarTopic(topic).subscribe(
      result => {
        console.log(result);
        this.findTopics();
      }
    );
  }

  denyComment(comment: Comment): void
  {
    this.modService.rechazarComment(comment).subscribe(
      result => {
        console.log(result);
        this.findComments();
      }
    );
  }

  denyTopic(topic: Topic): void
  {
    this.modService.rechazarTopic(topic).subscribe(
      result => {
        console.log(result);
        this.findTopics();
      }
    );
  }
}
