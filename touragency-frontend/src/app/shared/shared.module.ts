import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HeaderComponent} from './header/header.component';
import {
  MatButtonModule,
  MatCardModule,
  MatIconModule,
  MatListModule,
  MatMenuModule,
  MatSidenavModule,
  MatSnackBarModule,
  MatTableModule,
  MatToolbarModule
} from '@angular/material';
import {RouterModule} from '@angular/router';
import {SharedComponent} from './shared.component';
import {ToursListComponent} from './tours-list/tours-list.component';
import {NewTourComponent} from './new-tour/new-tour.component';
import {HttpClientModule} from '@angular/common/http';
import {TourDetailsComponent} from './tour-details/tour-details.component';

@NgModule({
  imports: [
    CommonModule,
    HttpClientModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    MatListModule,
    MatMenuModule,
    MatSidenavModule,
    MatSnackBarModule,
    MatTableModule,
    MatToolbarModule,
    RouterModule
  ],
  declarations: [
    HeaderComponent,
    SharedComponent,
    ToursListComponent,
    NewTourComponent,
    TourDetailsComponent
  ],
  exports: [
    HeaderComponent
  ]
})
export class SharedModule { }
