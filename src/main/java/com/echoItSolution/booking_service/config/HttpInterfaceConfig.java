package com.echoItSolution.booking_service.config;

import com.echoItSolution.booking_service.clients.UserHttpInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpInterfaceConfig {

    // This bean is already added in common-app so No need to create here
//    @Bean
//    @LoadBalanced
//    public WebClient.Builder webClientBuilderLoadBalanced() {
//        return WebClient.builder();
//    }

//    @Bean
//    public UserHttpInterface webClientHttpInterface(WebClient.Builder webClientBuilder) {
//        WebClient webClient = webClientBuilder
//                .baseUrl("http://USER-SERVICE/api/v1/account")
//                .filter(((request, next) -> {
//                    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//                    if(auth != null && auth.getCredentials() != null) {
//                        ClientRequest clientRequest = ClientRequest.from(request)
//                                .header("Authorization", "Bearer " + auth.getCredentials())
//                                .build();
//                        return next.exchange(clientRequest);
//                    }
//                    return next.exchange(request);
//                }))
//                .build();
//        WebClientAdapter adapter = WebClientAdapter.create(webClient);
//        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
//        return factory.createClient(UserHttpInterface.class);
//    }

    // This bean is already added in common-app so No need to create here
//    @Bean
//    @LoadBalanced
//    public RestClient.Builder restClientBuilderLoadBalanced() {
//        return RestClient.builder();
//    }

    @Bean
    public UserHttpInterface restClientHttpInterface(RestClient.Builder clientBuilder) {
        RestClient restClient = clientBuilder
                .baseUrl("http://USER-SERVICE/api/v1/account")
                .requestInterceptor((request, body, execution) -> {
                    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                    request.getHeaders().add("Authorization", "Bearer " + auth.getCredentials());
                    return execution.execute(request, body);
                })
                .build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(UserHttpInterface.class);
    }
}
