package com.echoItSolution.booking_service.controller;

import com.echoItSolution.booking_service.clients.RestTemplateClient;
import com.echoItSolution.booking_service.clients.UserFeignClient;
import com.echoItSolution.booking_service.clients.UserHttpInterface;
import com.echoItSolution.booking_service.dto.UserDTO;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/booking/inter-communication")
public class InterCommunicationController {

    private final RestTemplateClient restTemplateClient;
    private final UserFeignClient userFeignClient;
    private final RestClient.Builder restClientBuilder;
    private final WebClient webClient;
    private final UserHttpInterface userHttpInterface;

    private Integer retryCount = 1;


    @GetMapping(value = "restCall/get-all-users")
    public List<UserDTO> getAllUsersViaRestCall() {
        List<UserDTO> userDTOList = restTemplateClient.getAllUsers();
        userDTOList.forEach(System.out::println);
        return userDTOList;
    }

    @GetMapping(value = "feignCall/get-all-users")
    public List<UserDTO> getAllUsersViaFeignCall() {
        List<UserDTO> userDTOList = userFeignClient.getAllUsers();
        userDTOList.forEach(System.out::println);
        return userDTOList;
    }

    @GetMapping(value = "restClient/get-all-users")
    public List<UserDTO> getAllUsersViaRestClient() {
        // 1st approach
//        String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
//        RestClient restClient = RestClient.create();
//        List<UserDTO> userDTOList = restClient.get()
//                .uri("hhtp://localhost:9090/api/v1/account/user/get/all")
//                .header("Authorization"," Bearer "+token)
//                .retrieve()
//                .body(new ParameterizedTypeReference<List<UserDTO>>() {
//                });

        // 2nd approach with baseUrl and fetching service instance from eureka
//        List<UserDTO> userDTOList = restClient.get().uri("/user/get/all")
//                .retrieve()
//                .body(new ParameterizedTypeReference<List<UserDTO>>() {
//                });
        // 3rd approach with load balancing
        RestClient client = restClientBuilder
                // moved baseUrl and requestInterceptor to bean definition
//                .baseUrl("http://USER-SERVICE")
//                .requestInterceptor((request, body, execution) -> {
//                    String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
//                    request.getHeaders().setBearerAuth(token);
//                    return execution.execute(request, body);
//                })
                .build();
        List<UserDTO> userDTOList = client.get()
                .uri("/api/v1/account/user/get/all")
                .retrieve()
                .body(new ParameterizedTypeReference<List<UserDTO>>() {});
        userDTOList.forEach(System.out::println);
        return userDTOList;
    }


    @GetMapping(value = "webClient/get-all-users")
    public List<UserDTO> getAllUsersViaWebClient() {
        String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
//        WebClient webClient = webClientBuilder
//                .baseUrl("http://USER-SERVICE/api/v1/account")
                // moved requestInterceptor to bean definition
//                .filter((request, next) -> {
//                    ClientRequest clientRequest = ClientRequest.from(request)
//                            .header("Authorization", "Bearer " + token)
//                            .build();
//                    return next.exchange(clientRequest);
//                })
//                .build();
        List<UserDTO> userDTOList = webClient.get().uri("/user/get/all")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<UserDTO>>() {})
                .block();
        userDTOList.forEach(System.out::println);
        return userDTOList;
    }

    @GetMapping(value = "httpInterface/get-all-users")
//    @CircuitBreaker(name = "userServiceCircuitBreaker", fallbackMethod = "fallbackMethod")
//    @Retry(name = "userServiceRetry", fallbackMethod = "fallbackMethod")
//    @RateLimiter(name = "userServiceRateLimiter"/*, fallbackMethod = "fallbackMethod"*/)
//    @Bulkhead(name = "userServiceSemaphoreBulkhead", type = Bulkhead.Type.SEMAPHORE/*, fallbackMethod = "fallbackMethod"*/)
    @Bulkhead(name = "userServiceBulkhead", type = Bulkhead.Type.THREADPOOL/*, fallbackMethod = "fallbackMethod"*/)
    public List<UserDTO> getAllUsersViaHttpInterface(@RequestParam(required = false) String data) {
        System.out.println("Retry count: " + retryCount);
        System.out.println("Current timestamp: " + new Date().toInstant().toString());
        retryCount += 1;
        List<UserDTO> userDTOList = userHttpInterface.getAllUsers();
        userDTOList.forEach(System.out::println);
        return userDTOList;
    }

    // fallback method signature should be same as original method
    // with additional parameter of type Throwable
    public List<UserDTO> fallbackMethod(String data, Throwable throwable) {
        System.out.println("DATA from fallbackMethod "+data);
        System.out.println("Fallback executed: " + throwable.getMessage());
        retryCount = 1;
        return List.of(UserDTO.builder()
                .userId(123L)
                .userName("Dummy user").build());
    }

}
