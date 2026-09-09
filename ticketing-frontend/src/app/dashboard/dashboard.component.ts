import { Component, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WebSocketService } from '../services/websocket/websocket.service';
import { Event } from '../event/event.model';
import { Subscription } from 'rxjs';
import { EventService } from '../services/event/event.service';
import { VendorService } from '../services/vendor/vendor.service';
import { Router } from '@angular/router'; 


@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit, OnDestroy{
  isSlideOpen: boolean = false; // Track slide panel state
  ticketCount: number = 1; // Track the number of tickets
  ticketSuccess = '';
  ticketError = '';

  event: Event = {
    eventName: "Forest Rail Run 2024 (FRR'24)",
    eventDate: '27–28 April 2024',
    eventTime: '7:00 AM',
    price: 4500,
    totalTickets: 0
  }; // Initialize the event with default values
  private subscription: Subscription | null = null;

  constructor(
    private webSocketService: WebSocketService,
    private eventService: EventService,
    private vendorService: VendorService,
    private router: Router 
  ) {}
  ngOnDestroy() {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
    this.webSocketService.disconnect();
  }

  ngOnInit(): void {
    this.eventService.getEvent().subscribe({
      next: (event) => {
        this.event = event;
        console.log('Event fetched:', event);
      },
      error: (err) => {
        console.error('Error fetching event:', err);
      },
    });

    this.subscription = this.webSocketService.connect().subscribe({
      next: (event) => {
        this.event = event;
      },
      error: (err) => {
        console.error('Error connecting to WebSocket:', err);
      },
    });
  }
  toggleSlide() {
    this.isSlideOpen = !this.isSlideOpen; // Toggle the panel's visibility
    this.ticketSuccess = '';
    this.ticketError = '';
  }

  increaseCount() {
    this.ticketCount++;
  }

  decreaseCount() {
    if (this.ticketCount > 1) {
      this.ticketCount--;
    }
  }

  cancel() {
    this.isSlideOpen = false; // Close the slide panel
    this.ticketCount = 1; // Reset the ticket count
  }

  addTicket() {
    this.ticketSuccess = '';
    this.ticketError = '';
    const vendorSession = JSON.parse(sessionStorage.getItem('userSession') || '{}');
    if (!vendorSession || !vendorSession.id) {
      alert('Please login to add tickets');
      this.router.navigate(['/login']);
      return;
    }
    this.vendorService.addTickets(vendorSession.id,this.ticketCount).subscribe({
      next: (response) => {
        console.log('Ticket added successful', response);
        this.ticketSuccess = response.message || 'Tickets added successfully.';
        this.ticketCount = 1;
        alert(response.message);
      },
      error: (err) => {
        console.error('Error adding ticket', err);
        this.ticketError = 'Tickets could not be added. Please try again.';
      },
    });
  }
}