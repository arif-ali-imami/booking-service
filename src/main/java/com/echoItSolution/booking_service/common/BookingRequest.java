package com.echoItSolution.booking_service.common;

import com.echoItSolution.booking_service.entity.Booking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {
    private Booking bookOrder;
}
