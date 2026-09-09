import { Component, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { WebSocketService } from '../services/websocket/websocket.service';
import { Event, AdventureEvent, ADVENTURE_EVENTS } from './event.model';
import { Subscription } from 'rxjs';
import { EventService } from '../services/event/event.service';
import { CustomerService } from '../services/customer/customer.service';

@Component({
  selector: 'app-event',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './event.component.html',
  styleUrl: './event.component.scss'
})
export class EventComponent implements OnInit, OnDestroy {
  isSlideOpen: boolean = false;
  ticketCount: number = 1;

  // Curated adventure event metadata
  frrDetails: AdventureEvent = ADVENTURE_EVENTS.find(e => e.id === 'frr-2024') || ADVENTURE_EVENTS[0];

  // Active section tab
  activeTab: 'overview' | 'route' | 'entries' | 'awards' | 'features' | 'gallery' = 'overview';

  // Active image in gallery
  activeGalleryImage: string = 'assets/frr/frr6.jpg';

  event: Event = {
    eventName: "Forest Rail Run 2024 (FRR'24)",
    eventDate: '27–28 April 2024',
    eventTime: '7:00 AM',
    price: 4500,
    totalTickets: 0
  };

  private subscription: Subscription | null = null;

  isVendor(): boolean {
    const session = JSON.parse(sessionStorage.getItem('userSession') || '{}');
    return session.role === 'vendor';
  }

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
        if (event) {
          this.event = {
            ...this.event,
            totalTickets: event.totalTickets ?? this.event.totalTickets,
            price: event.price ?? this.event.price,
            eventName: event.eventName ?? this.event.eventName,
            eventDate: event.eventDate ?? this.event.eventDate,
            eventTime: event.eventTime ?? this.event.eventTime
          };
        }
        console.log('Event fetched:', event);
      },
      error: (err) => {
        console.error('Error fetching event:', err);
      },
    });

    this.subscription = this.webSocketService.connect().subscribe({
      next: (event) => {
        if (event) {
          this.event = {
            ...this.event,
            ...event
          };
        }
      },
      error: (err) => {
        console.error('Error connecting to WebSocket:', err);
      },
    });
  }

  setTab(tab: 'overview' | 'route' | 'entries' | 'awards' | 'features' | 'gallery') {
    this.activeTab = tab;
  }

  setGalleryImage(img: string) {
    this.activeGalleryImage = img;
  }

  toggleSlide() {
    this.isSlideOpen = !this.isSlideOpen;
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
    this.isSlideOpen = false;
    this.ticketCount = 1;
  }

  purchase() {
    const customerSession = JSON.parse(sessionStorage.getItem('userSession') || '{}');
    if (!customerSession || !customerSession.id) {
      alert('Please login to purchase tickets');
      this.router.navigate(['/login']);
      return;
    }
    this.customerService.buyTickets(customerSession.id, this.ticketCount).subscribe({
      next: (response) => {
        console.log('Ticket purchase successful', response);
        alert(response.message);
      },
      error: (err) => {
        console.error('Error purchasing ticket', err);
      },
    });
    this.isSlideOpen = false;
    this.ticketCount = 1;
  }
}