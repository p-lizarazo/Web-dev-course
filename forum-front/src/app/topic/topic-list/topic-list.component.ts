import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { forkJoin, Observable } from 'rxjs';
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
        this.topics.forEach(el => {
          this.topicService.findTopicRanking(el).subscribe(
            mresult => {
              el.ranking = mresult;
              this.topics.sort(
                (a: Topic, b: Topic) => {
                  return b.ranking - a.ranking;
                }
              );
            }
          );
        });
      });
  }

  setId(id: number): number
  {
    this.forumId = id;
    return id;
  }

}
