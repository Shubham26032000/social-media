package com.shubham.apigateway.filter;

import com.shubham.apigateway.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    @Autowired
    private RoutValidator routValidator;

//    @Autowired
//    private RestTemplate restTemplate;

    @Autowired
    private JwtUtils jwtUtils;

    public AuthenticationFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (routValidator.isSecured.test(exchange.getRequest())){
                //headers contains token or not
                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                    throw new SecurityException("Missing authorization header...!!!");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")){
                    authHeader = authHeader.substring(7);
                }

                try{
                    //rest call to identity-service
                    //restTemplate.getForObject("http://identity-service/validate?token"+authHeader,String.class);
                    jwtUtils.validateJwtToken(authHeader);
                    String email = jwtUtils.getUsernameFromJwtToken(authHeader);
                    exchange = addUserEmailToHeader(exchange,email);
                }catch (Exception e){
                    System.out.println("Unauthorize access : "+e.getMessage());
                    throw new RuntimeException("Error in calling or unauthorize access");

                }
            }
            return chain.filter(exchange);
        });
    }

    private ServerWebExchange addUserEmailToHeader(ServerWebExchange exchange, String email) {
        return  exchange.mutate()
                .request(req -> req.build().mutate().headers(httpHeaders -> httpHeaders.add("X-User-Email",email))
                        .build())
                .build();
    }

    public static  class Config{

    }
}
