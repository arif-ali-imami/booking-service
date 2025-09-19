package com.echoItSolution.booking_service.clients;

import com.echoItSolution.booking_service.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "user-service")
public interface UserFeignClient {

    @GetMapping(value = "/api/v1/account/user/get/all")
    public List<UserDTO> getAllUsers();
}
