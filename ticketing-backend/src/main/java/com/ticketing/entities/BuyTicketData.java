package com.ticketing.entities;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyTicketData {
    private Long customerId;
    @Min(value = 1, message = "Ticket quantity must be greater than zero")
    private int ticketPurchaseCount;
}