import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { Forum } from '../models/forum';

@Injectable({
  providedIn: 'root'
})
export class ForumsServiceService {

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

  findAll(): Observable<Forum[]>
  {
    const url = `${environment.RESTServiceBaseUrl}/forums`;
    return this.get<Forum[]>(url);
  }

  create(forum: Forum): Observable<any>{
    const url = `${environment.RESTServiceBaseUrl}/forums`;
    return this.post(url, {
      descripcion: forum.descripcion,
      moderado: forum.moderado,
      titulo: forum.titulo
    });
  }

  update(forum: Forum): Observable<any> {
    const url = `${environment.RESTServiceBaseUrl}/forums/${forum.id}`;
    return this.put(url, {
      descripcion: forum.descripcion,
      moderado: forum.moderado,
      titulo: forum.titulo
    });
  }

  findById(
    id: number // : Observable<Forum>
  ): Observable<Forum> {
    const url = `${environment.RESTServiceBaseUrl}/forums/${id}`;
    return this.get<Forum>(url);
  }

  deleteForum(id: number): Observable<any>{
    const url = `${environment.RESTServiceBaseUrl}/forums/${id}`;
    return this.delete(url);
  }






}
