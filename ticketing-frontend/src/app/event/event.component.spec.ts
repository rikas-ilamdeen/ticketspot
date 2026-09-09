import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { provideRouter } from '@angular/router';

import { EventComponent } from './event.component';
import { EventService } from '../services/event/event.service';
import { WebSocketService } from '../services/websocket/websocket.service';
import { provideHttpClient } from '@angular/common/http';

const eventServiceMock = { getEvent: () => of({ eventName: 'Test Event', totalTickets: 2 }) };
const webSocketServiceMock = { connect: () => of(), disconnect: () => undefined };

describe('EventComponent', () => {
  let component: EventComponent;
  let fixture: ComponentFixture<EventComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EventComponent],
      providers: [
        provideHttpClient(),
        provideRouter([]),
        { provide: EventService, useValue: eventServiceMock },
        { provide: WebSocketService, useValue: webSocketServiceMock }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EventComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
