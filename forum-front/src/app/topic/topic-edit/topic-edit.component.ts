import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { switchMap } from 'rxjs/operators';
import { Topic } from 'src/app/models/topic';
import { TopicsServiceService } from 'src/app/services/topics-service.service';

@Component({
  selector: 'app-topic-edit',
  templateUrl: './topic-edit.component.html',
  styleUrls: ['./topic-edit.component.scss']
})
export class TopicEditComponent implements OnInit {
  topic: Topic = null;
  errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private topicService: TopicsServiceService
  ) {}

  ngOnInit(): void {
    this.route.paramMap
      .pipe(
        switchMap(params => this.topicService.findById(+params.get('idTopic')))
      )
      .subscribe(result => {
        this.topic = result;
      });
  }

  update(): void {
    console.log(this.topic);
    this.topicService.update(this.topic).subscribe(
      result => {
        console.log(result);
        this.router.navigate([`../../`, this.topic.id], { relativeTo : this.route});
      },
      error => {
        console.error(error);
        this.errorMessage = error.toString();
      }
    );
  }

  cancel(): void {
    this.router.navigate([`../../`, this.topic.id], { relativeTo : this.route});
  }

}
