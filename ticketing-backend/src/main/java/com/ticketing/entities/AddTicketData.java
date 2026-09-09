package com.ticketing.entities;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddTicketData {
    private Long vendorId;
    @Min(value = 1, message = "Number of tickets must be greater than zero")
    private int numberOfTickets;
}
