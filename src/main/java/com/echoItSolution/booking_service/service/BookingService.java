package com.echoItSolution.booking_service.service;

import com.echoItSolution.booking_service.common.BookingRequest;
import com.echoItSolution.booking_service.common.BookingResponse;
import com.echoItSolution.booking_service.common.Payment;
import com.echoItSolution.booking_service.entity.Booking;
import com.echoItSolution.booking_service.repository.BookingRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
@Slf4j
public class BookingService {
    private final BookingRespository bookingRespository;
    private final RestTemplate restTemplate;


    public BookingResponse bookOder(BookingRequest bookingRequest){
        String url = "http://PAYMENT-SERVICE/payment/doPayment";
        Payment payment = new Payment();
        Booking bookOrder = bookingRequest.getBookOrder();
        payment.setAmount(bookOrder.getPrice());
        payment.setOrderId(bookOrder.getId());
        Payment paymentResponse = restTemplate.postForObject(url, payment, Payment.class);
        String response = paymentResponse.getPaymentStatus().equals("Success")?"Payment processed Successful":"Payment Failure";
        bookingRespository.save(bookOrder);
        return new BookingResponse(bookOrder,paymentResponse.getAmount(),paymentResponse.getTransactionId(),response);
    }
}
