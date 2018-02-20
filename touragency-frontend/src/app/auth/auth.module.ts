import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginComponent} from './login/login.component';
import {RegistrationComponent} from './registration/registration.component';
import {MatButtonModule, MatCardModule, MatCheckboxModule, MatInputModule} from '@angular/material';
import {ReactiveFormsModule} from '@angular/forms';
import {SharedModule} from '../shared/shared.module';
import {HttpUserService} from '../common/services/http.user.service';
import {AuthComponent} from './auth.component';
import {RouterModule} from '@angular/router';

@NgModule({
  imports: [
    CommonModule,
    MatButtonModule, MatCardModule, MatCheckboxModule, MatInputModule,
    ReactiveFormsModule, RouterModule,
    SharedModule
  ],
  declarations: [LoginComponent, RegistrationComponent, AuthComponent],
  providers: [HttpUserService],
  exports: [LoginComponent, RegistrationComponent]
})
export class AuthModule { }
