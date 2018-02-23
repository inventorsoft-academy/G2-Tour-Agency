import {Component, OnDestroy, OnInit} from '@angular/core';
import {Tour} from '../../common/models/tour';
import {Subscription} from 'rxjs/Subscription';
import {ActivatedRoute} from '@angular/router';
import {HttpService} from '../../common/services/http.service';
import {MatSnackBar} from '@angular/material';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {SimpleBooking} from '../../common/models/simple.booking';

@Component({
  selector: 'app-tour-details',
  templateUrl: './tour-details.component.html',
  styleUrls: ['./tour-details.component.css']
})
export class TourDetailsComponent implements OnInit, OnDestroy {

  tour: Tour;
  subscriptions: Subscription[] = [];
  tourTypes = ['ORDINARY', 'SPECIAL', 'HOT'];
  status = 'Available';

  newCapacity;
  newPrice;
  typeSelectedValue: string;

  updateTourTypeForm: FormGroup;
  updateTourPriceForm: FormGroup;

  constructor(private route: ActivatedRoute,
              private http: HttpService,
              private snackbar: MatSnackBar,
              private builder: FormBuilder) {
  }

  ngOnInit() {
    this.getTourById(this.route.snapshot.params.id);
    this.initializeForms();
  }

  getTourById(id: number) {
    const getTourSubscriptions = this.http.getTourById(id)
      .subscribe(res => {
        this.tour = res;
        console.log(this.tour);
      });
    this.subscriptions.push(getTourSubscriptions);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => {
      subscription.unsubscribe();
    });
  }

  public updateTourType() {
    if (this.typeSelectedValue !== undefined) {
      const updateTourSubscription = this.http.updateTourType(this.tour.id,
        this.typeSelectedValue).subscribe(res => {
        this.snackbar.open('Successfully updated tour type', '', {duration: 2500});
        setTimeout({}, 3000);
        window.location.reload();
      }, error => {
        this.snackbar.open('Failed to update tour type', '', {duration: 2500});
      });

      this.subscriptions.push(updateTourSubscription);
    }
  }

  public updateTourCapacity() {
    const newTourCapacity = (document.getElementById('new-tour-capacity') as HTMLInputElement).value;
    console.log(newTourCapacity);
  }

  public updateTourPrice() {
    const newTourPrice = (document.getElementById('tour-price') as HTMLInputElement).value;

    const updateSubscription = this.http.updateTourPrice(this.tour.id, newTourPrice)
      .subscribe(res => {
      this.snackbar.open('Tour price was successfully updated', '', {duration: 2500});
      this.newPrice = newTourPrice;
      setTimeout({}, 3000);
      window.location.reload();
    }, error => {
      this.snackbar.open('Failed to update tour price', '', {duration: 2500});
    });

    this.subscriptions.push(updateSubscription);
  }

  public cancelTour() {
    const cancelSubscription = this.http.cancelTour(this.tour.id).subscribe(res => {
      this.snackbar.open('Tour was successfully cancelled', '', {duration: 2500});
      this.status = 'Cancelled';
    });
    this.subscriptions.push(cancelSubscription);
  }

  public bookTour() {
    const userId: number = parseInt(sessionStorage.getItem('userId'), 10);
    console.log(this.tour);
    const booking = new SimpleBooking(userId, this.tour.id, true);

    const bookSubscription = this.http.bookTour(booking).subscribe(res => {
      this.snackbar.open('Tour was successfully booked', '', {duration: 2500});
      }, error => {
      this.snackbar.open('Failed to perform tour booking', '', {duration: 2500});
    });
    this.subscriptions.push(bookSubscription);
  }

  private initializeForms(): void {
    this.updateTourTypeForm = this.builder.group({
      newType: new FormControl('', [Validators.required])
    });

    this.updateTourPriceForm = this.builder.group({
      newPrice: new FormControl('', [Validators.required])
    });
  }
}
