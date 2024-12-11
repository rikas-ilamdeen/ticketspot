import { Component, OnDestroy, OnInit } from '@angular/core';
import { WebSocketService } from '../services/websocket/websocket.service';
import { Event } from './event.model'; 
import { Subscription } from 'rxjs';
import { EventService } from '../services/event/event.service';
import { CustomerService } from '../services/customer/customer.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-event',
  standalone: true,
  imports: [],
  templateUrl: './event.component.html',
  styleUrl: './event.component.scss'
})
export class EventComponent  implements OnInit, OnDestroy{
  isSlideOpen: boolean = false; // Track slide panel state
  ticketCount: number = 1; // Track the number of tickets

  event: Event = {
    eventName: '',
    eventDate: '',
    eventTime: '',
    price: 0.0,
    totalTickets: 0
  }; // Initialize the event with default values
  private subscription: Subscription | null = null;

  constructor(
    private webSocketService: WebSocketService,
    private eventService: EventService,
    private customerService: CustomerService,
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

  purchase() {
    const customerSession = JSON.parse(sessionStorage.getItem('userSession') || '{}');
    console.log(customerSession);
    console.log(customerSession.id);
    if (!customerSession || !customerSession.id) {
      alert('Please login to purchase tickets');
      this.router.navigate(['/login']);
      return;
    }
    this.customerService.buyTickets(customerSession.id,this.ticketCount).subscribe({
      next: (response) => {
        console.log('Ticket purchase successful', response);
        alert(response.message);
      },
      error: (err) => {
        console.error('Error purchasing ticket', err);
      },
    });
    this.isSlideOpen = false; // Close the slide panel after purchase
    this.ticketCount = 1; // Reset the ticket count
  }
}