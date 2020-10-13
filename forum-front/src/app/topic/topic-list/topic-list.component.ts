import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { switchMap } from 'rxjs/operators';
import { Topic } from 'src/app/models/topic';
import { TopicsServiceService } from 'src/app/services/topics-service.service';
import { UserGlobalService } from 'src/app/services/user-global.service';

@Component({
  selector: 'app-topic-list',
  templateUrl: './topic-list.component.html',
  styleUrls: ['./topic-list.component.scss']
})
export class TopicListComponent implements OnInit {
  topics: Topic[] = [];
  forumId: number;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private topicService: TopicsServiceService,
    public userGlobal: UserGlobalService
  ) {}

  ngOnInit(): void {
    this.route.paramMap
      .pipe(
        switchMap(params => this.topicService.findTopicsByForumId(this.setId(+params.get('id'))))
      )
      .subscribe(result => {
        this.topics = result;
      });
  }

  setId(id: number): number
  {
    this.forumId = id;
    return id;
  }

}
