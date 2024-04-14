package com.shubham.socialmedia.helper;

import com.shubham.socialmedia.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(url = "http://localhost:8089/user-service/api",name = "user-service")
public interface UserService {

    @GetMapping("/user/allUser")
    List<User> getUsers(@RequestParam List<Long> userIds);
}
