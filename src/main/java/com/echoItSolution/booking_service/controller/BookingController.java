package com.echoItSolution.booking_service.controller;

import com.echoItSolution.booking_service.common.BookingRequest;
import com.echoItSolution.booking_service.common.BookingResponse;
import com.echoItSolution.booking_service.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @PostMapping("/create")
    public BookingResponse bookOrder(@RequestBody BookingRequest bookingRequest){
        return bookingService.bookOder(bookingRequest);
    }
}
