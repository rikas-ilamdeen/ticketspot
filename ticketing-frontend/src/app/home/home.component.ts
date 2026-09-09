import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { AdventureEvent, ADVENTURE_EVENTS } from '../event/event.model';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
  activeCategory = 'All Adventures';

  categories = [
    { label: 'All Adventures', icon: 'bi-compass' },
    { label: 'Trail Running', icon: 'bi-lightning-charge' },
    { label: 'Hiking / Trekking', icon: 'bi-signpost-split' },
    { label: 'Surfing / Water Sports', icon: 'bi-water' },
    { label: 'Beach Camping', icon: 'bi-tent' }
  ];

  allEvents: AdventureEvent[] = ADVENTURE_EVENTS;

  featuredEvent: AdventureEvent = ADVENTURE_EVENTS.find(e => e.id === 'frr-2024') || ADVENTURE_EVENTS[0];

  selectedEvent: AdventureEvent | null = null;
  activeGalleryImage: string | null = null;

  constructor(private router: Router) {}

  isVendor(): boolean {
    const session = JSON.parse(sessionStorage.getItem('userSession') || '{}');
    return session.role === 'vendor';
  }

  selectCategory(category: string) {
    this.activeCategory = category;
  }

  isCategoryMatch(event: AdventureEvent): boolean {
    if (this.activeCategory === 'All Adventures') return true;
    if (this.activeCategory === 'Trail Running') {
      return event.category.toLowerCase().includes('trail running');
    }
    if (this.activeCategory === 'Hiking / Trekking') {
      return event.category.toLowerCase().includes('hiking') || event.category.toLowerCase().includes('trekking');
    }
    if (this.activeCategory === 'Surfing / Water Sports') {
      return event.category.toLowerCase().includes('surfing') || event.category.toLowerCase().includes('water sports');
    }
    if (this.activeCategory === 'Beach Camping') {
      return event.category.toLowerCase().includes('camping') || event.category.toLowerCase().includes('beach');
    }
    return true;
  }

  get upcomingEvents(): AdventureEvent[] {
    return this.allEvents.filter(e => !e.isPast && this.isCategoryMatch(e));
  }

  get pastEvents(): AdventureEvent[] {
    return this.allEvents.filter(e => e.isPast && this.isCategoryMatch(e));
  }

  openEventDetails(event: AdventureEvent, ev?: MouseEvent) {
    if (ev) {
      ev.preventDefault();
      ev.stopPropagation();
    }
    this.selectedEvent = event;
    this.activeGalleryImage = event.image;
  }

  closeEventDetails() {
    this.selectedEvent = null;
    this.activeGalleryImage = null;
  }

  setGalleryImage(img: string) {
    this.activeGalleryImage = img;
  }

  navigateToBooking(event: AdventureEvent) {
    if (event.bookingEnabled) {
      this.router.navigate(['/event']);
    }
  }
}