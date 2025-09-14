package com.echoItSolution.booking_service.clients;

import com.echoItSolution.booking_service.dto.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service")
public interface PaymentClient {

    @PostMapping("/payment/doPayment")
    Payment doPayment(@RequestBody Payment payment);

}
