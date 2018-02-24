import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {HttpService} from '../../common/services/http.service';
import {Subscription} from 'rxjs/Subscription';
import {DateAdapter, MAT_DATE_FORMATS, NativeDateAdapter} from '@angular/material';
import {Router} from '@angular/router';


export class AppDateAdapter extends NativeDateAdapter {

  parse(value: any): Date | null {
    if ((typeof value === 'string') && (value.indexOf('/') > -1)) {
      const str = value.split('/');
      const year = Number(str[2]);
      const month = Number(str[1]) + 1;
      const date = Number(str[0]);
      return new Date(year, month, date);
    }
    const timestamp = typeof value === 'number' ? value : Date.parse(value);
    return isNaN(timestamp) ? null : new Date(timestamp);
  }

  format(date: Date, displayFormat: Object): string {
    if (displayFormat === 'input') {
      const day = date.getDate();
      const month = date.getMonth() + 1;
      const year = date.getFullYear();
      return year + '-' + this._to2digit(month) + '-' + this._to2digit(day);
    } else {
      return date.toDateString();
    }
  }

  private _to2digit(n: number): string {
    return ('00' + n).slice(-2);
  }
}

export const APP_DATE_FORMAT = {
  parse: {
    dateInput: {month: 'short', year: 'numeric', day: 'numeric'}
  },
  display: {
    dateInput: 'input',
    monthYearLabel: {month: 'short', year: 'numeric', day: 'numeric'},
    dateA11yLabel: {year: 'numeric', month: 'long', day: 'numeric'},
    monthYearA11yLabel: {year: 'numeric', month: 'long'},
  }
};



@Component({
  selector: 'app-new-tour',
  templateUrl: './new-tour.component.html',
  styleUrls: ['./new-tour.component.css'],
  providers: [
    {
      provide: DateAdapter, useClass: AppDateAdapter
    },
    {
      provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMAT
    }
  ]
})
export class NewTourComponent implements OnInit {

  newTourForm: FormGroup;
  tourTypes = ['ORDINARY', 'SPECIAL', 'HOT'];
  subscriptions: Subscription[] = [];

  constructor(private formBuilder: FormBuilder,
              private http: HttpService,
             private router: Router) { }

  ngOnInit() {
    // this.newTourForm = new FormGroup({
    this.newTourForm = this.formBuilder.group({
      destination: new FormControl('', [Validators.required]),
      country: new FormControl('', [Validators.required]),
      startDate: new FormControl('', [Validators.required]),
      endDate: new FormControl('', [Validators.required]),
      tourType: new FormControl('', [Validators.required]),
      capacity: new FormControl('', [Validators.required]),
      price: new FormControl('', [Validators.required]),
      agency: new FormControl('', [Validators.required])
    });
  }

  createTour() {
    const subscription = this.http.saveTour(this.newTourForm.value).subscribe();
    this.subscriptions.push(subscription);
  }

  resetForm(): void {
    this.newTourForm.reset();
  }
}


