import {Component} from '@angular/core';
import {HttpAuthService} from './common/services/http.auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  title = 'app';

  currentUser;

  constructor(private authService: HttpAuthService) {
    authService.getUser('user1', 'password')
      .subscribe(res => {
        this.currentUser = res;
        sessionStorage.setItem('userId', this.currentUser.id);
        sessionStorage.setItem('username', this.currentUser.username);
        sessionStorage.setItem('password', this.currentUser.password);
        sessionStorage.setItem('email', this.currentUser.email);
      });
  }
}
