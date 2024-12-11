package com.ticketing.controllers;

import com.ticketing.entities.Customer;
import com.ticketing.entities.Vendor;
import com.ticketing.entities.Ticket;
import com.ticketing.services.TicketService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@NoArgsConstructor

public class TicketEventController {

    @Autowired
    private TicketService ticketService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @GetMapping("/api/event")
    public ResponseEntity<Optional<Ticket>> getEvent(){
        Optional<Ticket> event = ticketService.getConfiguration();
        System.out.println("Sending event: " + event); // Log event
        return ResponseEntity.ok(event);
    }
//    @GetMapping("/api/sendEvent")
//    public void sendEventUpdate() {
//        Optional<Ticket> event = ticketService.getConfiguration();
//        System.out.println("Sending event update: " + event); // Log event
//        messagingTemplate.convertAndSend("/topic/event", event.orElse(null));
//    }

}