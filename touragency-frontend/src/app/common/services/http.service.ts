import {HttpClient} from '@angular/common/http';
import {Tour} from '../models/tour';
import {environment} from '../../../environments/environment';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class HttpService {
  constructor(private http: HttpClient) {}

  getTours(): Observable<Tour[]> {
    return this.http.get<Tour[]>(environment.API + '/tours');
  }

  getTourById(id) {
    return this.http.get(environment.API + `/tours/${id}`);
  }

  saveTour(o: Tour) {
    o.isActive = true;
    return this.http.post(environment.API + '/tours/new', JSON.parse(JSON.stringify(o)));
  }
}
