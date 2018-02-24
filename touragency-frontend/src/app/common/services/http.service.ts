import {HttpClient, HttpParams} from '@angular/common/http';
import {Tour} from '../models/tour';
import {environment} from '../../../environments/environment';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {SimpleBooking} from '../models/simple.booking';
import {Booking} from '../models/booking';

@Injectable()
export class HttpService {
  constructor(private http: HttpClient) {}

  getTours(): Observable<Tour[]> {
    return this.http.get<Tour[]>(environment.API + '/tours');
  }

  getTourById(id): Observable<Tour> {
    return this.http.get<Tour>(environment.API + `/tours/${id}`);
  }

  getToursByType(type: string): Observable<Tour[]> {
    const params = new HttpParams().set('type', type);
    return this.http.get<Tour[]>(environment.API + '/tours', {params: params});
  }

  saveTour(o: Tour) {
    o.isActive = true;
    return this.http.post(environment.API + '/tours/new', JSON.parse(JSON.stringify(o)));
  }

  cancelTour(id: number) {
    return this.http.put(environment.API + `/tours/cancel/${id}`, null);
  }

  updateTourPrice(id: number, price) {
    const param = new HttpParams().set('price', price);
    return this.http.put(environment.API + `/tours/update/price/${id}`, null, { params: param });
  }

  updateTourType(id: number, type: string) {
    const param = new HttpParams().set('type', type);
    return this.http.put(environment.API + `/tours/update/type/${id}`, null, {params: param});
  }

  getBookings(username: string): Observable<Booking[]> {
    const param = new HttpParams().set('username', username);
    return this.http.get<Booking[]>(environment.API + '/booking/user', {params: param});
  }

  bookTour(o: SimpleBooking) {
    return this.http.post(environment.API + '/booking/book', JSON.parse(JSON.stringify(o)));
  }
}
