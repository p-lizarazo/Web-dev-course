import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LoginRESTService {

  constructor(private http: HttpClient) {}

  login(username: string, password: string) {
    const formHeaders = new HttpHeaders();
    formHeaders.append('Content-Type', 'application/x-www-form-urlencoded');
    const formParams = new HttpParams()
      .set('username', username)
      .set('password', password);
    return this.http.post('http://localhost:8080/login', null, {
      headers: formHeaders,
      params: formParams,
      withCredentials: true
    });
  }

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
          'Accept': 'application/json'
        }),
        withCredentials: true
      })
      .pipe(
        // retry(5),
        catchError(this.handleError)
      );
  }

  logout(): Observable<any> {
    const url = `${environment.RESTServiceBaseUrl}/logout`;
    return this.get(url);
  }

}
