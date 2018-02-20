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
import {AuthModule} from './auth/auth.module';
import {LoginComponent} from './auth/login/login.component';
import {RegistrationComponent} from './auth/registration/registration.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AuthComponent} from './auth/auth.component';

const routes = [
  {
    path: 'app',
    component: SharedComponent,
    children: [
      {
        path: 'tours-list',
        component: ToursListComponent
      },
      {
        path: 'new-tour',
        component: NewTourComponent
      },
      {
        path: 'tour-details/:id',
        component: TourDetailsComponent
      }
    ]
  },
  {
    path: 'auth',
    component: AuthComponent,
    children: [
      {
        path: '',
        redirectTo: 'login',
        pathMatch: 'full'
      },
      {
        path: 'login',
        component: LoginComponent
      },
      {
        path: 'register',
        component: RegistrationComponent
      },
    ]
  },
  {
    path: '',
    redirectTo: 'auth',
    pathMatch: 'full'
  }
];

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    AuthModule,
    BrowserModule,
    BrowserAnimationsModule,
    RouterModule.forRoot(routes),
    SharedModule
  ],
  providers: [HttpService],
  bootstrap: [AppComponent]
})
export class AppModule { }
