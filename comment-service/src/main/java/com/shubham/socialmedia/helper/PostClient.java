package com.shubham.socialmedia.helper;

import com.shubham.socialmedia.model.PostDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:8089/post-service/api",name = "post-service")
public interface PostClient {

    @GetMapping("/post/{postid}")
    public PostDto getPost(@PathVariable("postid") long postId);
}
