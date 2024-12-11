import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { SignUpComponent } from './signup/signup.component';
import { EventComponent } from './event/event.component';
import { DashboardComponent } from './dashboard/dashboard.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },  // Home route
  { path: 'login', component: LoginComponent },  // Login route
  { path: 'signup', component: SignUpComponent },  // SignUp route
  { path: 'event', component: EventComponent },  // Event route
  { path: 'dashboard', component: DashboardComponent },  // dashboard route
  { path: '**', redirectTo: '' }  // Wildcard route to redirect invalid URLs to Home
];