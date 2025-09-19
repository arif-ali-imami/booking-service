package com.echoItSolution.booking_service.clients;

import com.echoItSolution.booking_service.dto.UserDTO;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange("/user")
public interface UserHttpInterface {

    @GetExchange("/get/all")
    public List<UserDTO> getAllUsers();
}

