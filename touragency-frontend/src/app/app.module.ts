import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {HttpService} from './common/services/http.service';
import {SharedModule} from './shared/shared.module';
import {RouterModule} from '@angular/router';
import {SharedComponent} from './shared/shared.component';
import {ToursListComponent} from './shared/tours-list/tours-list.component';
import {NewTourComponent} from './shared/new-tour/new-tour.component';
import {TourDetailsComponent} from './shared/tour-details/tour-details.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HttpAuthService} from './common/services/http.auth.service';
import {BookingsListComponent} from './shared/bookings-list/bookings-list.component';

const routes = [
  {
    path: 'app',
    component: SharedComponent,
    children: [
      {
        path: '',
        redirectTo: 'tours',
        pathMatch: 'full'
      },
      {
        path: 'tours',
        component: ToursListComponent
      },
      {
        path: 'new',
        component: NewTourComponent
      },
      {
        path: 'tour-details/:id',
        component: TourDetailsComponent
      },
      {
        path: 'bookings',
        component: BookingsListComponent
      }
    ]
  },
  {
    path: '',
    redirectTo: 'app/tours',
    pathMatch: 'full'
  }
];

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    RouterModule.forRoot(routes),
    SharedModule
  ],
  providers: [HttpService, HttpAuthService],
  bootstrap: [AppComponent]
})
export class AppModule { }
