import {Component, OnInit} from '@angular/core';
import {CollectionViewer, DataSource} from '@angular/cdk/collections';
import {Observable} from 'rxjs/Observable';
import {HttpService} from '../../common/services/http.service';
import {Router} from '@angular/router';
import {Subscription} from 'rxjs/Subscription';
import {NotificationService} from '../../common/services/notification.service';
import {Booking} from '../../common/models/booking';

@Component({
  selector: 'app-bookings-list',
  templateUrl: './bookings-list.component.html',
  styleUrls: ['./bookings-list.component.css']
})
export class BookingsListComponent implements OnInit {

  bookingsList: Booking[] = [];
  subscriptions: Subscription[] = [];
  dataSource = new BookingsDataSource(this.http);
  displayedColumns = ['bookingId', 'destination', 'country', 'startDate', 'endDate',
    'price', 'agency', 'status'];

  constructor(private http: HttpService, private router: Router,
              private notificationService: NotificationService) { }

  ngOnInit() {
  }

  getBookings() {
    const getBookingsSubscription = this.http.getBookings(sessionStorage.getItem('username'))
      .subscribe(res => {
        this.bookingsList = res;
        this.notificationService.notify('Imported list of bookings');
      }, error => {
        this.notificationService.notify('Failed to import list of bookings');
        console.log(error.message);
      });
    this.subscriptions.push(getBookingsSubscription);
  }

}


export class BookingsDataSource extends DataSource<any> {

  constructor(private http: HttpService) {
    super();
  }

  connect(collectionViewer: CollectionViewer): Observable<any[]> {
    return this.http.getBookings(sessionStorage.getItem('username'));
  }

  disconnect(collectionViewer: CollectionViewer): void {
  }

}
