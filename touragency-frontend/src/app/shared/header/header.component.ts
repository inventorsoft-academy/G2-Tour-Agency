import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor() { }

  navigation = [
    { link: 'tours', label: 'Tours' },
    { link: 'bookings', label: 'Bookings' },
    { link: 'reviews', label: 'Reviews' }
  ];

  username;
  email;

  ngOnInit() {
    this.username = sessionStorage.getItem('username');
    this.email = sessionStorage.getItem('email');
  }

}
