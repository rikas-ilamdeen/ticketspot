import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// Define vendor type (can be similar to customer)
export interface signupInput {
  name: string;
  email: string;
  password: string;
  phoneNumber: string;
}
export interface loginInput {
  email: string;
  password: string;
}
@Injectable({
  providedIn: 'root',
})
export class VendorService {
  private apiUrl = 'http://localhost:8080/api'; // Update with your vendor API URL

  constructor(private http: HttpClient) {}

  createVendor(vendor: signupInput): Observable<signupInput> {
    return this.http.post<signupInput>(`${this.apiUrl}/vendor/signup`, vendor);
  }
  login(email: string, password: string, usertype:number): Observable<any> {
    const body = { email, password,usertype };
    return this.http.post(`${this.apiUrl}/vendor/login`, body);
  }
  addTickets(vendorId: number, numberOfTickets: number): Observable<any> {
    const body = { vendorId, numberOfTickets };
    return this.http.post(`${this.apiUrl}/vendor/addTickets`, body);
  }
}