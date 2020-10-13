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
export class ModService {

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

  getAllCommentsNoAprobados(): Observable<Comment[]>
  {
    const url = `${environment.RESTServiceBaseUrl}/comments/noaprobados`;
    return this.get<Comment[]>(url);
  }

  getAllTopicsNoAprobados(): Observable<Topic[]>
  {
    const url = `${environment.RESTServiceBaseUrl}/topics/noaprobados`;
    return this.get<Topic[]>(url);
  }

  aprobarTopic(topic: Topic): Observable<any>
  {
    const url = `${environment.RESTServiceBaseUrl}/topics/${topic.id}/aprobar`;
    return this.post<any>(url, {});
  }

  aprobarComment(comment: Comment): Observable<any>
  {
    const url = `${environment.RESTServiceBaseUrl}/comments/${comment.id}/aprobar`;
    return this.post<any>(url, {});
  }

  rechazarTopic(topic: Topic): Observable<any>
  {
    const url = `${environment.RESTServiceBaseUrl}/topics/${topic.id}/rechazar`;
    return this.post<any>(url, {});
  }

  rechazarComment(comment: Comment): Observable<any>
  {
    const url = `${environment.RESTServiceBaseUrl}/comments/${comment.id}/rechazar`;
    return this.post<any>(url, {});
  }
}
