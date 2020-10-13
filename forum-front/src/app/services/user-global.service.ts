import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class UserGlobalService {
  user: User = null;
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
          Accept: 'application/json'
        }),
        withCredentials: true
      })
      .pipe(
        // retry(5),
        catchError(this.handleError)
      );
  }

  getUser(): void{
    const url = `${environment.RESTServiceBaseUrl}/user`;
    this.get(url).subscribe(
      (data: any) => {
        const authorities: string[] = [];
        for (const auth of data.authorities) {
          authorities.push(auth.authority);
        }
        this.user = new User(data.name, authorities);
      }, error => {
        console.error(error);
      }
    );
  }
}
