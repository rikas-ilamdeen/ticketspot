import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CustomerService } from '../services/customer/customer.service';
import { VendorService } from '../services/vendor/vendor.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  imports: [CommonModule, FormsModule],
})
export class LoginComponent {
  loginData = {
    email: '',
    password: '',
  };

  selectedRole = 'customer';
  emailError = '';
  passwordError = '';

  constructor(
    private customerService: CustomerService,
    private vendorService: VendorService,
    private router: Router
  ) {}

  validateEmail(): boolean {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!this.loginData.email) {
      this.emailError = 'Email is required.';
      return false;
    } else if (!emailRegex.test(this.loginData.email)) {
      this.emailError = 'Please enter a valid email address.';
      return false;
    }
    this.emailError = '';
    return true;
  }

  validatePassword(): boolean {
    if (!this.loginData.password) {
      this.passwordError = 'Password is required.';
      return false;
      } else if (this.loginData.password.length < 6) {
      this.passwordError = 'Password must be at least 6 characters long.';
      return false;
    }
    this.passwordError = '';
    return true;
  }

  onSubmit() {
    // Validate email and password before making API calls
    const isEmailValid = this.validateEmail();
    const isPasswordValid = this.validatePassword();

    if (!isEmailValid || !isPasswordValid) {
      return; // Stop submission if validation fails
    }

    if (this.selectedRole === 'customer') {
      this.customerService
        .login(this.loginData.email, this.loginData.password)
        .subscribe({
          next: (response) => {
            console.log('Customer Login successful', response);
            // Store login data
            sessionStorage.setItem('userSession', JSON.stringify({ ...response, role: 'customer' }));
            console.log('session' + sessionStorage.getItem('userSession'));
            alert('Customer Login successful!');
            this.router.navigate(['/event']);
          },
          error: (err) => {
            console.error('Error during customer login', err);
            alert('Customer Login failed. Please try again.');
          },
        });

    } else if (this.selectedRole === 'vendor') {
      this.vendorService
        .login(this.loginData.email, this.loginData.password, 2)
        .subscribe({
          next: (response) => {
            console.log('Vendor Login successful', response);
            // Store login data
            sessionStorage.setItem('userSession', JSON.stringify({ ...response, role: 'vendor' }));
            console.log('session' + sessionStorage.getItem('userSession'));
            alert('Vendor Login successful!');
            this.router.navigate(['/dashboard']);
          },
          error: (err) => {
            console.error('Error during vendor login', err);
            alert('Vendor Login failed. Please try again.');
          },
        });
    }
  }
}