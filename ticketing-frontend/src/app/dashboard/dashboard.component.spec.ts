import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';

import { DashboardComponent } from './dashboard.component';
import { EventService } from '../services/event/event.service';
import { VendorService } from '../services/vendor/vendor.service';
import { WebSocketService } from '../services/websocket/websocket.service';
import { provideHttpClient } from '@angular/common/http';

const eventServiceMock = { getEvent: () => of({ eventName: 'Test Event', totalTickets: 2 }) };
const webSocketServiceMock = { connect: () => of(), disconnect: () => undefined };
const vendorServiceMock = { addTickets: () => of({ message: 'ok' }) };

describe('DashboardComponent', () => {
  let component: DashboardComponent;
  let fixture: ComponentFixture<DashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashboardComponent],
      providers: [
        provideHttpClient(),
        { provide: EventService, useValue: eventServiceMock },
        { provide: VendorService, useValue: vendorServiceMock },
        { provide: WebSocketService, useValue: webSocketServiceMock }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
