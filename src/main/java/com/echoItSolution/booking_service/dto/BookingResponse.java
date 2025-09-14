package com.echoItSolution.booking_service.dto;

import com.echoItSolution.booking_service.entity.Booking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private Booking bookOrder;
    private Double amount;
    private String transactionId;
    private String response;
}
