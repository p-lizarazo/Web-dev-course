import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { Comment } from '../models/comment';
import { Topic } from '../models/topic';

@Injectable({
  providedIn: 'root'
})
export class TopicsServiceService {

  constructor(private http: HttpClient) {}

  private handleError(error: HttpErrorResponse): Observable<any> {
    console.log(error);
    return throwError('An error has occurred');
  }

  private get<T>(url): Observable<T> {
    console.log('get:', url);
    return this.http
      .get<T>(url, {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          Accept : 'application/json'
        }),
        withCredentials: true
      })
      .pipe(
        // retry(5),
        catchError(this.handleError)
      );
  }

  private post<T>(url, data: T): Observable<T> {
    console.log('post:', url);
    return this.http
      .post<T>(url, data, {
        headers: new HttpHeaders({
          'Content-Type': 'application/json'
        }),
        withCredentials: true
      })
      .pipe(
        // retry(5),
        catchError(this.handleError)
      );
  }

  private put<T>(url, data: T): Observable<T> {
    console.log('put:', url);
    return this.http.put<T>(url, data, {
      withCredentials: true
    }).pipe(
      // retry(5),
      catchError(this.handleError)
    );
  }

  private delete<T>(url): Observable<T> {
    console.log('delete: ', url);
    return this.http.delete<T>(url, {
      withCredentials: true
    }).pipe(
      catchError(this.handleError)
    );
  }

  findTopicsByForumId(id: number): Observable<Topic[]>
  {
    const url = `${environment.RESTServiceBaseUrl}/forums/${id}/topics`;
    return this.get<Topic[]>(url);
  }

  create(topic: Topic): Observable<any>{
    const url = `${environment.RESTServiceBaseUrl}/topics`;
    return this.post(url, {
      titulo: topic.titulo,
      contenido: topic.contenido,
      foro: topic.foro
    });
  }

  findById(
    id: number // : Observable<Topic>
  ): Observable<Topic> {
    const url = `${environment.RESTServiceBaseUrl}/topics/${id}`;
    return this.get<Topic>(url);
  }

  findTopicComments(
    topic: Topic
  ): Observable<Comment[]> {
    const url = `${environment.RESTServiceBaseUrl}/topics/${topic.id}/comments`;
    return this.get<Comment[]>(url);
  }

  deleteTopic(id: number): Observable<any>{
    const url = `${environment.RESTServiceBaseUrl}/topics/${id}`;
    return this.delete(url);
  }

  deleteComment(comment: Comment): Observable<any>{
    const url = `${environment.RESTServiceBaseUrl}/comments/${comment.id}`;
    return this.delete(url);
  }

  update(topic: Topic): Observable<any> {
    const url = `${environment.RESTServiceBaseUrl}/topics/${topic.id}`;
    return this.put(url, {
      titulo: topic.titulo,
      contenido: topic.contenido,
      foro: topic.foro
    });
  }

  editarComment(comment: Comment): Observable<any> {
    const url = `${environment.RESTServiceBaseUrl}/comments/${comment.id}`;
    return this.put(url, {
      contenido: comment.contenido
    });
  }

  likeTopic(topic: Topic): Observable<any> {
    const url = `${environment.RESTServiceBaseUrl}/topics/${topic.id}/likes`;
    return this.post(url, {});
  }

  likeComment(comment: Comment): Observable<any> {
    const url = `${environment.RESTServiceBaseUrl}/comments/${comment.id}/likes`;
    return this.post(url, {});
  }

  dislikeComment(comment: Comment): Observable<any> {
    const url = `${environment.RESTServiceBaseUrl}/comments/${comment.id}/dislikes`;
    return this.post(url, {});
  }

  dislikeTopic(topic: Topic): Observable<any> {
    const url = `${environment.RESTServiceBaseUrl}/topics/${topic.id}/dislikes`;
    return this.post(url, {});
  }

  findTopicLikes(topic: Topic): Observable<any> {
    const url = `${environment.RESTServiceBaseUrl}/topics/${topic.id}/likes`;
    return this.get(url);
  }

  findCommentLikes(comment: Comment): Observable<any> {
    const url = `${environment.RESTServiceBaseUrl}/comments/${comment.id}/likes`;
    return this.get(url);
  }

  findCommentDislikes(comment: Comment): Observable<any> {
    const url = `${environment.RESTServiceBaseUrl}/comments/${comment.id}/dislikes`;
    return this.get(url);
  }

  findCommentRanking(comment: Comment): Observable<any> {
    const url = `${environment.RESTServiceBaseUrl}/comments/${comment.id}/ranking`;
    return this.get(url);
  }

  findMyCommentVote(comment: Comment): Observable<any> {
    const url = `${environment.RESTServiceBaseUrl}/comments/${comment.id}/myvote`;
    return this.get(url);
  }

  findTopicDislikes(topic: Topic): Observable<any> {
    const url = `${environment.RESTServiceBaseUrl}/topics/${topic.id}/dislikes`;
    return this.get(url);
  }

  findTopicRanking(topic: Topic): Observable<any> {
    const url = `${environment.RESTServiceBaseUrl}/topics/${topic.id}/ranking`;
    return this.get(url);
  }

  findMyVote(topic: Topic): Observable<any> {
    const url = `${environment.RESTServiceBaseUrl}/topics/${topic.id}/myvote`;
    return this.get(url);
  }

  createComment(comment: Comment): Observable<any>
  {
    const url = `${environment.RESTServiceBaseUrl}/comments`;
    return this.post(url, {
      contenido: comment.contenido,
      tema: {
        id: comment.tema.id
      }
    });
  }

  createReplyComment(comment: Comment): Observable<any>
  {
    const url = `${environment.RESTServiceBaseUrl}/comments`;
    return this.post(url, {
      contenido: comment.contenido,
      tema: {
        id: comment.tema.id
      },
      padre: {
        id: comment.padre.id
      }
    });
  }

}
