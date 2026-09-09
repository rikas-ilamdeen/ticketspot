export interface Event {
  eventName?: string;
  totalTickets?: number;
  eventDate?: string; // This can be null or a string
  eventTime?: string; // This can be null or a string
  price?: number;
}

export interface AdventureEvent {
  id: string;
  title: string;
  organizer?: string;
  dates: string;
  startTime?: string;
  location: string;
  category: string;
  distance?: string;
  status: 'featured' | 'upcoming' | 'past' | 'future';
  statusLabel: string;
  isPast: boolean;
  bookingEnabled: boolean;
  image: string;
  gallery?: string[];
  personalPhotos?: string[];
  description: string;
  routeHighlights?: string[];
  entryTypes?: string[];
  awards?: string[];
  features?: string[];
  personalAchievement?: string;
  displayNotes?: string[];
  price?: number;
}

export const ADVENTURE_EVENTS: AdventureEvent[] = [
  {
    id: 'frr-2024',
    title: "Forest Rail Run 2024 (FRR'24)",
    organizer: 'Crosswinds Adventures',
    dates: '27–28 April 2024',
    startTime: '27 April 2024 at 7:00 AM',
    location: 'Sri Lankan hill country / Forest Rail Run route',
    category: 'Competition Hike / Trail Running / Adventure',
    distance: '30 km',
    status: 'featured',
    statusLabel: 'Main Featured Event',
    isPast: false,
    bookingEnabled: true,
    price: 4500,
    image: 'assets/frr/frr6.jpg',
    gallery: [
      'assets/frr/frr.jpg',
      'assets/frr/frr1.jpg',
      'assets/frr/frr2.jpg',
      'assets/frr/frr3.jpg',
      'assets/frr/frr4.jpg',
      'assets/frr/frr5-closing-soon.jpg',
      'assets/frr/frr7.jpg'
    ],
    description: "A 30 km adventure combining railway tracks, forest trails, rugged roads, stream crossings, wetlands, steep climbs and descents, finishing around Devil's Staircase.",
    routeHighlights: [
      '7 km rail run',
      '15 km forest trail',
      'Udaweriya Peak',
      'Horton Plains road',
      "Devil's Staircase",
      'Forest and mountain terrain'
    ],
    entryTypes: [
      'Individual Male',
      'Individual Female',
      'Duo — 2 participants',
      'Triplet — 3 participants'
    ],
    awards: [
      'Extreme Team: 1st, 2nd and 3rd place',
      'Extreme Individual: 1st, 2nd and 3rd place',
      'Children & Family: 1st, 2nd and 3rd place',
      'Special awards'
    ],
    features: [
      'GPS/Wikiloc route tracking',
      'Marked trail',
      'Event marshals',
      'First-aid and medical support',
      'Meals',
      'Camping/tent facilities',
      'Transportation from Colombo and return',
      'Event T-shirt',
      'Limited hotel accommodation'
    ]
  },
  {
    id: 'riverston-2023',
    title: 'A Walk to Riverston 2023',
    organizer: 'Crosswinds Adventures',
    dates: 'October 28–29, 2023',
    location: 'Riverston Mountain Range, Matale, Sri Lanka',
    category: 'Hiking / Trail Running / Adventure',
    distance: '22 km',
    status: 'past',
    statusLabel: 'Past Event',
    isPast: true,
    bookingEnabled: false,
    image: 'assets/walk-to-riverstone/crosswinds-rikas-third-place.jpg',
    gallery: [
      'assets/walk-to-riverstone/crosswinds3.jpg',
      'assets/walk-to-riverstone/crosswinds4.jpg'
    ],
    personalPhotos: [
      'assets/walk-to-riverstone/crosswinds-rikas-third-place.jpg',
      'assets/walk-to-riverstone/crosswinds-rikas-third-place-0.jpg',
      'assets/walk-to-riverstone/crosswinds-rikas-third-place-1.jpg'
    ],
    description: "A challenging adventure through the Riverston mountain range, including Mini World's End and Bambarakiri Ella waterfall.",
    personalAchievement: '2nd place in the Extreme Solo Trail Running category.'
  },
  {
    id: 'surf-championships-2023',
    title: 'National Surf Championships 2023',
    organizer: 'Surfing Federation of Sri Lanka',
    dates: '7–8 April 2023',
    location: 'Hikkaduwa, Sri Lanka',
    category: 'Surfing / Water Sports',
    status: 'past',
    statusLabel: 'Past Event',
    isPast: true,
    bookingEnabled: false,
    image: 'assets/surfing/surf.jpeg',
    gallery: [
      'assets/surfing/surf1.jpeg',
      'assets/surfing/surf3.jpeg'
    ],
    description: 'National surfing championship gathering top surfers from across Sri Lanka at Hikkaduwa.'
  },
  {
    id: 'baththalangunduwa-camping',
    title: 'Baththalangunduwa Beach Camping',
    dates: 'Adventure Travel Experience',
    location: 'Baththalangunduwa Island, Sri Lanka',
    category: 'Beach Camping / Adventure / Travel',
    status: 'upcoming',
    statusLabel: 'Adventure Event',
    isPast: false,
    bookingEnabled: false,
    image: 'assets/camping/camp.jpg',
    description: 'Beach camping and outdoor adventure travel experience across the scenic coastal sandspit of Baththalangunduwa with limited seats.',
    displayNotes: [
      'Beach camping',
      'Limited seats',
      'Adventure/travel experience'
    ]
  },
  {
    id: 'pekoe-trail-13-14',
    title: 'Pekoe Trail — Stages 13 & 14',
    dates: 'Date to be announced',
    location: 'Pekoe Trail (Stages 13 & 14), Central Highlands, Sri Lanka',
    category: 'Hiking / Trekking / Camping',
    status: 'future',
    statusLabel: 'Future Adventure Event',
    isPast: false,
    bookingEnabled: false,
    image: 'assets/hiking/hike.jpeg',
    gallery: [
      'assets/hiking/hike1.jpeg',
      'assets/hiking/hike2.jpeg'
    ],
    description: "A two-day adventure through Sri Lanka's tea country, misty valleys and mountain landscapes along Stages 13 and 14 of the Pekoe Trail.",
    routeHighlights: [
      'Mountain and tea-country scenery',
      'Camping',
      'Sightseeing and photography',
      'Professional guides',
      'First-aid support',
      'Insurance',
      'Meals',
      'Transport',
      'Experienced adventure team'
    ]
  }
];
  