package com.shubham.apigateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

/***
 * This class to validate request of end points
 */
@Component
public class RoutValidator {

    public static final List<String> openApiEndPoints =
            List.of(
              "/auth/register",
                    "/auth/validate",
                    "/auth/token",
                    "/eureka"
            );

    public Predicate<ServerHttpRequest> isSecured =  request -> openApiEndPoints
            .stream()
            .noneMatch(uri-> request.getURI().getPath().contains(uri));
}
