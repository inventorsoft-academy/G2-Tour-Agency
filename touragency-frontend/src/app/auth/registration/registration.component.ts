import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Subscription} from 'rxjs/Subscription';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit, OnDestroy {

  registerForm: FormGroup;
  user;
  userSubscription: Subscription;

  constructor() { }

  ngOnInit() {
  }

  ngOnDestroy(): void {
    this.createForm();
    console.log('constructor: createForm()');
  }

  private createForm() {
    this.registerForm = new FormGroup({
      username: new FormControl('', [Validators.required,
        Validators.minLength(3), Validators.maxLength(50)]),
      password: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
      // isAdminCheckbox: new FormControl(false),
      agency: new FormControl('', [Validators.minLength(2),
        Validators.maxLength(50)])
    });
    console.log(this.registerForm);
  }

  register() {

  }

}
