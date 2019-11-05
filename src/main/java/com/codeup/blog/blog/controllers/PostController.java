package com.codeup.blog.blog.controllers;

import com.codeup.blog.blog.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
public class PostController {

    @GetMapping("/posts")
    public String getAllPosts(Model vModel) {
        ArrayList<Post> postsList = new ArrayList<>();
        postsList.add(new Post("My first post", "Very cool content."));
        postsList.add(new Post("A more recent post", "Even cooler content."));
        vModel.addAttribute("posts", postsList);
        return "posts/index";
    }

    @GetMapping("/posts/{id}")
    public String getPostById(@PathVariable long id, Model vModel) {
        Post postToView = new Post("Title of my post", "Super-engaging content");
        vModel.addAttribute("post", postToView);
        return "posts/show";
    }

    @GetMapping("/posts/create")
    @ResponseBody
    public String getCreatePostForm() {
        return "view the form for creating a post";
    }

    @PostMapping("/posts/create")
    @ResponseBody
    public String submitCreatePostForm() {
        return "create a new post";
    }
}
