import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';

@Injectable()
export class HttpAuthService {
  constructor(private http: HttpClient) {}

  getUser(username: string, password: string) {
    const headers = new HttpHeaders({'Content-Type': 'application/json; charset=utf-8',
          'username': username, 'password': password});
/*    headers.append('Content-Type', 'application/json; charset=utf-8');
      headers.append('username', username);
      headers.append('password', password);*/
    /*headers.append('username', username);
    headers.append('password', password);*/
    return this.http.get(environment.API + '/auth/user', {headers: headers});
  }
}
