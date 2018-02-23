import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs/Subscription';
import {HttpService} from '../../common/services/http.service';
import {DataSource} from '@angular/cdk/collections';
import {Observable} from 'rxjs/Observable';
import {Tour} from '../../common/models/tour';
import {Router} from '@angular/router';
import {NotificationService} from '../../common/services/notification.service';

@Component({
  selector: 'app-tours-list',
  templateUrl: './tours-list.component.html',
  styleUrls: ['./tours-list.component.css']
})
export class ToursListComponent implements OnInit, OnDestroy {

  toursList;
  subscriptions: Subscription[] = [];

  displayedColumns = ['id', 'destination', 'country', 'startDate', 'endDate',
    'tourType', 'capacity', 'price', 'agency'];
  dataSource = new TourDataSource(this.http);

  constructor(private http: HttpService, private router: Router,
              private notificationService: NotificationService) {
  }

  ngOnInit() {
    this.getTours();
  }

  getTours() {
    const getToursSubscription = this.http.getTours().subscribe(
      res => {
        this.toursList = res;
        this.notificationService.notify('Successfully loaded the list of tours');
      });
    this.subscriptions.push(getToursSubscription);
  }

  getTourById(id) {
    this.router.navigate([`app/tour-details/${id}`]);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => {
      subscription.unsubscribe();
    });
  }
}

export class TourDataSource extends DataSource<any> {

  constructor(private http: HttpService) {
    super();
  }

  connect(): Observable<Tour[]> {
    return this.http.getTours();
  }

  disconnect(): void {
  }
}
