import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common'; // Import CommonModule


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,CommonModule],
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
    return this.router.url === '/event' || this.router.url === '/dashboard'; // Adjust '/event' based on your route
  }
   // Method to log out
  logout() {
    sessionStorage.clear(); // Clear the session storage
    this.router.navigate(['/home']);
  }
}