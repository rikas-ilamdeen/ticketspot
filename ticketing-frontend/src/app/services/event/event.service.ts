import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Event } from '../../event/event.model';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  private baseUrl = 'http://localhost:8080'; // server URL

  constructor(private http: HttpClient) {}

  getEvent(): Observable<Event> {
    return this.http.get<Event>(`${this.baseUrl}/api/event`);
  }
}