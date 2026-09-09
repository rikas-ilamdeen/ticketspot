import { Component } from '@angular/core';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive, CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'EventTicketManagement';

  constructor(private router: Router) { }

  isUserLoggedIn(): boolean {
    return !!sessionStorage.getItem('userSession'); // Check if user is logged in
  }
  isNameAvailable(): string {
    const user = JSON.parse(sessionStorage.getItem('userSession') || '{}');
    console.log(user);
    return user.name ? `Welcome, ${user.name}` : '';   }

  isEventPage(): boolean {
    return this.router.url === '/event' || this.router.url === '/dashboard';
  }

  isVendor(): boolean {
    const user = JSON.parse(sessionStorage.getItem('userSession') || '{}');
    return user.role === 'vendor';
  }
   // Method to log out
  logout() {
    sessionStorage.clear(); // Clear the session storage
    this.router.navigate(['/']);
  }
}