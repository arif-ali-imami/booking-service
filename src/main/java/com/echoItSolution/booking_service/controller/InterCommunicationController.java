package com.echoItSolution.booking_service.controller;

import com.echoItSolution.booking_service.clients.RestClient;
import com.echoItSolution.booking_service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/inter-communication")
public class InterCommunicationController {

    private final RestClient restClient;

    @GetMapping(value = "/get-all-users")
    public List<UserDTO> InterCommunicationController() {
        List<UserDTO> userDTOList = restClient.getAllUsers();
        userDTOList.forEach(System.out::println);
        return userDTOList;
    }
}
