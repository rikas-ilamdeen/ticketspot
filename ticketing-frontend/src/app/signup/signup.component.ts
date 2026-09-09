import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CustomerService, signupInput } from '../services/customer/customer.service';
import { VendorService } from '../services/vendor/vendor.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-signup',
  standalone: true,
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss'],
  imports: [CommonModule, FormsModule],
})
export class SignUpComponent {
  signup: signupInput = {
    name: '',
    email: '',
    password: '',
    phoneNumber: '',
  };

  selectedRole = 'customer'; // default value

  // Error messages
  nameError = '';
  emailError = '';
  passwordError = '';
  phoneError = '';
  signupSuccess = '';
  signupError = '';
  passwordVisible = false;

  constructor(
    private customerService: CustomerService,
    private vendorService: VendorService
  ) {}

  validateName(): boolean {
    if (!this.signup.name) {
      this.nameError = 'Name is required.';
      return false;
    } else if (this.signup.name.length < 3) {
      this.nameError = 'Name must be at least 3 characters long.';
      return false;
    }
    this.nameError = '';
    return true;
  }

  validateEmail(): boolean {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!this.signup.email) {
      this.emailError = 'Email is required.';
      return false;
    } else if (!emailRegex.test(this.signup.email)) {
      this.emailError = 'Please enter a valid email address.';
      return false;
    }
    this.emailError = '';
    return true;
  }

  validatePassword(): boolean {
    if (!this.signup.password) {
      this.passwordError = 'Password is required.';
      return false;
      } else if (this.signup.password.length < 6) {
      this.passwordError = 'Password must be at least 6 characters long.';
      return false;
    }
    this.passwordError = '';
    return true;
  }

  validatePhoneNumber(): boolean {
    const phoneRegex = /^\d{10}$/;
    if (!this.signup.phoneNumber) {
      this.phoneError = 'Phone number is required.';
      return false;
    } else if (!phoneRegex.test(this.signup.phoneNumber)) {
      this.phoneError = 'Please enter a valid 10-digit phone number.';
      return false;
    }
    this.phoneError = '';
    return true;
  }

  onSubmit() {
    this.signupSuccess = '';
    this.signupError = '';
    // Perform all validations before proceeding
    const isNameValid = this.validateName();
    const isEmailValid = this.validateEmail();
    const isPasswordValid = this.validatePassword();
    const isPhoneValid = this.validatePhoneNumber();

    if (!isNameValid || !isEmailValid || !isPasswordValid || !isPhoneValid) {
      return; // Stop submission if any validation fails
    }

    if (this.selectedRole === 'customer') {
      this.customerService.createCustomer(this.signup).subscribe({
        next: (response) => {
          console.log('Customer sign-up successful', response);
          this.signupSuccess = 'Customer account created successfully. You can log in now.';
        },
        error: (err) => {
          console.error('Error during customer sign-up', err);
          this.signupError = 'Customer sign-up failed. Please check your details and try again.';
        },
      });
    } else if (this.selectedRole === 'vendor') {
      this.vendorService.createVendor(this.signup).subscribe({
        next: (response) => {
          console.log('Vendor sign-up successful', response);
          this.signupSuccess = 'Vendor account created successfully. You can log in now.';
        },
        error: (err) => {
          console.error('Error during vendor sign-up', err);
          this.signupError = 'Vendor sign-up failed. Please check your details and try again.';
        },
      });
    }
  }
}