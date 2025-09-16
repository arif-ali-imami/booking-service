package com.echoItSolution.booking_service.clients;

import com.echoItSolution.booking_service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequiredArgsConstructor
@Component
public class RestClient {

    private final RestTemplate restTemplate;

    public List<UserDTO> getAllUsers(){
        String url = "http://USER-SERVICE/api/v1/account/user/get/all";
        ResponseEntity<List<UserDTO>> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<UserDTO>>() {}
                );

        return response.getBody();
    }
}
