import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Client, Message } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { Event } from '../../event/event.model';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private stompClient: Client;
  private serverUrl = 'http://localhost:8080/ws'; // Update server URL

  constructor() {
    this.stompClient = new Client({
      webSocketFactory: () => new SockJS(this.serverUrl),
      debug: (str) => {
        console.log(str);
      },
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000
    });
  }

  public connect(): Observable<Event> {
    return new Observable<Event>(observer => {
      this.stompClient.onConnect = () => {
        console.log('Connected to WebSocket');
        
        // Subscribe to the event topic from controller
        this.stompClient.subscribe('/topic/event', (message: any) => {
          try {
            const ticket: Event = JSON.parse(message.body);
            console.log('Received message:', ticket);
            if (ticket) {
              observer.next(ticket);
            }
          } catch (error) {
            console.error('Error parsing message:', error);
          }
        });
      };

      this.stompClient.onStompError = (frame) => {
        console.error('STOMP error:', frame);
        observer.error(frame);
      };

      this.stompClient.activate();

      return () => {
        this.disconnect();
      };
    });
  }

  public disconnect() {
    if (this.stompClient) {
      this.stompClient.deactivate();
    }
  }
}