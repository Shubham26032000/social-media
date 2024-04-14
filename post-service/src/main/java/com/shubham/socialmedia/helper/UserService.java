package com.shubham.socialmedia.helper;

import com.shubham.socialmedia.model.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:8089/user-service/api",name = "user-service")
public interface UserService {

    @GetMapping("/user/{userId}")
    UserDto getUser(@PathVariable long userId);
}
