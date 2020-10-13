import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { switchMap } from 'rxjs/operators';
import { Comment } from 'src/app/models/comment';
import { Topic } from 'src/app/models/topic';
import { TopicsServiceService } from 'src/app/services/topics-service.service';
import { UserGlobalService } from 'src/app/services/user-global.service';

@Component({
  selector: 'app-topic-view',
  templateUrl: './topic-view.component.html',
  styleUrls: ['./topic-view.component.scss']
})
export class TopicViewComponent implements OnInit {
  topic: Topic = null;
  comments: Comment[] = null;
  myVote: boolean = null;
  errorMessage = '';
  idForum: number;
  idTopic: number;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    public userGlobal: UserGlobalService,
    private topicService: TopicsServiceService
  ) {}

  ngOnInit(): void {
    this.route.paramMap.pipe(
      switchMap(params => {
        this.idForum = +params.get('id');
        this.idTopic = +params.get('idTopic');
        return this.topicService.findById(+params.get('idTopic'));
      })
    )
    .subscribe( (result: Topic) => {
      console.log(result);
      this.topic = result;
      this.topicService.findTopicComments(this.topic).subscribe(
        (cResult: any) => {
          console.log(cResult);
          this.comments = cResult;
          this.updateLikesComments();
      });
      this.updateLikes();
    });
  }

  backToList(): void {
    this.router.navigate(['../'], { relativeTo: this.route });
  }

  delete(id: number): void
  {
    this.topicService.deleteTopic(id).subscribe(
      results => {
        this.router.navigate(['../'], { relativeTo: this.route });
      },
      error => console.error(error)
    );
  }

  likeComment(comment: Comment): void
  {
    this.topicService.likeComment(comment).subscribe(
      result => {
        this.updateLikesComments();
      },
      error => console.error(error)
    );
  }

  dislikeComment(comment: Comment): void {
    this.topicService.dislikeComment(comment).subscribe(
      result => {
        this.updateLikesComments();
      },
      error => console.error(error)
    );
  }

  likeTopic(): void {
    this.topicService.likeTopic(this.topic).subscribe(
      result => {
        this.updateLikes();
      },
      error => console.error(error)
    );
  }

  dislikeTopic(): void {
    this.topicService.dislikeTopic(this.topic).subscribe(
      result => {
        this.updateLikes();
      },
      error => console.error(error)
    );
  }

  deleteComment(comment: Comment): void
  {
    this.topicService.deleteComment(comment).subscribe(
      results => {
        this.topicService.findTopicComments(this.topic).subscribe(
          (cResult: any) => {
            console.log(cResult);
            this.comments = cResult;
            this.updateLikesComments();
        });
      },
      error => console.error(error)
    );
  }

  updateLikes(): void {
    this.topicService.findTopicLikes(this.topic).subscribe(
      cResult => {
        console.log(cResult);
        this.topic.likes = cResult;
      });
    this.topicService.findTopicDislikes(this.topic).subscribe(
      cResult => {
        console.log(cResult);
        this.topic.dislikes = cResult;
      });
    this.topicService.findTopicRanking(this.topic).subscribe(
        cResult => {
          console.log(cResult);
          this.topic.ranking = cResult;
        });
    this.topicService.findMyVote(this.topic).subscribe(
      cResult => {
        console.log(cResult);
        this.myVote = cResult;
      },
      error => {
        console.log(error);
        this.myVote = null;
      });
  }

  updateLikesComments(): void
  {
    this.comments.forEach(element => {
      this.topicService.findCommentLikes(element).subscribe(
        result => {
          element.likes = result;
        }
      );
      this.topicService.findCommentDislikes(element).subscribe(
        result => {
          element.dislikes = result;
        }
      );
      this.topicService.findCommentRanking(element).subscribe(
        result => {
          element.ranking = result;
          this.comments.sort(
            (a: Comment, b: Comment) => {
              return b.ranking - a.ranking;
            }
          );
        }
      );
      this.topicService.findMyCommentVote(element).subscribe(
        result => {
          element.myLike = result;
        }, error => {
          element.myLike = null;
        }
      );
    });
  }

  createComment(contenido: string): void
  {
    const myComment = new Comment(
      undefined,
      contenido,
      undefined,
      undefined,
      undefined,
      this.topic,
      undefined,
      undefined,
      undefined,
      undefined,
      undefined,
      undefined
    );
    this.topicService.createComment(myComment).subscribe(
      response => {
        this.topicService.findTopicComments(this.topic).subscribe(
          (cResult: any) => {
            console.log(cResult);
            this.comments = cResult;
            this.updateLikesComments();
        });
      }
    );
  }

  editarComment(comment: Comment): void
  {
    this.topicService.editarComment(comment).subscribe(
      result => {
        console.log('Edited OK');
      }
    );
  }

  createCommentReply(padre: Comment, contenido: string): void
  {
    const myComment = new Comment(
      undefined,
      contenido,
      undefined,
      undefined,
      undefined,
      this.topic,
      undefined,
      padre,
      undefined,
      undefined,
      undefined,
      undefined
    );
    this.topicService.createReplyComment(myComment).subscribe(
      response => {
        this.topicService.findTopicComments(this.topic).subscribe(
          (cResult: any) => {
            console.log(cResult);
            this.comments = cResult;
            this.updateLikesComments();
        });
      }
    );
  }


}
