import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// Define customer type
export interface signupInput {
  name: string;
  email: string;
  password: string;
  phoneNumber: string;
  
}
// export interface loginInput {
//   email: string;
//   password: string;
// }
@Injectable({
  providedIn: 'root',
})
export class CustomerService {
  private apiUrl = 'http://localhost:8080/api'; // Spring Boot API URL

  constructor(private http: HttpClient) {}

  // Method to create a new customer (sign up)
  createCustomer(customer: signupInput): Observable<signupInput> {
    return this.http.post<signupInput>(`${this.apiUrl}/customer/signup`, customer);
  }
  login(email: string, password: string): Observable<any> {
    const body = { email, password };
    return this.http.post(`${this.apiUrl}/customer/login`, body);
  }
  buyTickets(customerId: number, ticketPurchaseCount: number): Observable<any> {
    console.log('customerId',customerId);
    const body = { customerId, ticketPurchaseCount };
    return this.http.post(`${this.apiUrl}/customer/buy`, body);
  }
  
}