///<reference path="../../../../node_modules/rxjs/Observable.d.ts"/>
import {Component, OnDestroy, OnInit} from '@angular/core';
import {ErrorStateMatcher} from '@angular/material';
import {FormControl, FormGroup, FormGroupDirective, NgForm, Validators} from '@angular/forms';
import {HttpUserService} from '../../common/services/http.user.service';
import {Router} from '@angular/router';
import 'rxjs/add/operator/first';
import {Subscription} from 'rxjs/Subscription';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {

  loginForm: FormGroup;
  user;
  userSubscription: Subscription;

  constructor(private http: HttpUserService, private router: Router) {
    this.createForm();
  }

  ngOnInit() {
  }

  ngOnDestroy(): void {

  }

  private createForm() {
    this.loginForm = new FormGroup({
      username: new FormControl('', [Validators.required,
        Validators.minLength(3), Validators.maxLength(50)]),
      password: new FormControl('', [Validators.required]),
    });
  }

  login() {
    const username: string = this.loginForm.controls['username'].value;
    const password: string = this.loginForm.controls['password'].value;

    this.userSubscription = this.http.getUser(username, password).subscribe(
      res => {
        this.user = res;
      });

    this.userSubscription.unsubscribe();
  }

  register() {
    this.router.navigate([`auth/register`]);
  }
}

export class LoginFormErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }

}
