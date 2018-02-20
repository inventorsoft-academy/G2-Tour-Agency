import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {User} from '../models/user';
import {environment} from '../../../environments/environment';

@Injectable()
export class HttpUserService {
  constructor(private http: HttpClient) {}

  getUser(username: string, password: string): Observable<User> {
    const headers = new HttpHeaders();
    headers.set('Content-Type', 'application/json; charset=utf-8')
      .set('username', username).set('password', password);
    /*headers.append('username', username);
    headers.append('password', password);*/
    return this.http.get<User>(environment.API + '/auth/user', {headers: headers});
  }
}
