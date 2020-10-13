import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Forum } from 'src/app/models/forum';
import { Topic } from 'src/app/models/topic';
import { TopicsServiceService } from 'src/app/services/topics-service.service';

@Component({
  selector: 'app-topic-create',
  templateUrl: './topic-create.component.html',
  styleUrls: ['./topic-create.component.scss']
})
export class TopicCreateComponent implements OnInit {
  @ViewChild('createForm', { static: true })
  createForm;

  submitted = false;
  idForo: number = undefined;

  topic: Topic = undefined;

  errorMessage = '';

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private topicService: TopicsServiceService
  ){}

  ngOnInit(): void {
    this.idForo = +this.route.snapshot.paramMap.get('id');
    this.topic = new Topic(
      undefined,
      undefined,
      undefined,
      undefined,
      undefined,
      undefined,
      new Forum(
        this.idForo,
        undefined,
        undefined,
        undefined,
      ),
      undefined,
      undefined,
      undefined
    );
  }

  create(): void {
    this.submitted = true;
    console.log(this.topic);
    this.topicService.create(this.topic).subscribe(
      result => {
        console.log(result);
        this.router.navigate(['../'], { relativeTo: this.route });
      },
      error => {
        console.error(error);
        this.errorMessage = error.toString();
        this.submitted = false;
      }
    );
  }

  cancel(): void {
    this.router.navigate(['../'], { relativeTo: this.route });
  }

  get canSubmit(): boolean{
    return this.createForm.form.valid && !this.submitted;
  }

}
