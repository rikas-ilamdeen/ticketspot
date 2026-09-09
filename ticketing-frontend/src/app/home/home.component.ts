import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
  activeCategory = 'All adventures';

  categories = [
    { label: 'All adventures', icon: 'bi-compass' },
    { label: 'Trail running', icon: 'bi-lightning-charge' },
    { label: 'Hiking', icon: 'bi-signpost-split' },
    { label: 'Water & coast', icon: 'bi-water' },
    { label: 'Camping', icon: 'bi-tent' }
  ];

  events = [
    { title: 'HIGHLANDERS Ella Run 2026', location: 'Ella, Uva Province', date: '19 Sep 2026', type: 'Trail running', image: 'assets/ella-run.jpg', accent: 'lime' },
    { title: 'Knuckles Mist Trail', location: 'Kandy, Central Province', date: '03 Oct 2026', type: 'Hiking', image: 'assets/mvv.jpg', accent: 'clay' },
    { title: 'Galle Coastline Paddle', location: 'Galle, Southern Province', date: '11 Oct 2026', type: 'Water & coast', image: 'assets/avatar.jpeg', accent: 'ocean' }
  ];

  destinations = [
    { name: 'Ella', note: 'Cloud forests & trails', image: 'assets/ella-run.jpg' },
    { name: 'Kandy', note: 'Mountain escapes', image: 'assets/mvv.jpg' },
    { name: 'Galle', note: 'Ocean-side adventures', image: 'assets/avatar.jpeg' }
  ];

  constructor(private router: Router) {}

  startSignup() {
    this.router.navigate(['/signup']);
  }

  selectCategory(category: string) {
    this.activeCategory = category;
  }

  filteredEvents() {
    return this.activeCategory === 'All adventures'
      ? this.events
      : this.events.filter((event) => event.type === this.activeCategory);
  }
}