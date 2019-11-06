package com.codeup.blog.blog.controllers;

import com.codeup.blog.blog.models.Post;
import com.codeup.blog.blog.repositories.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {

    private final PostRepository postDao;

    public PostController(PostRepository postDao) {
        this.postDao = postDao;
    }

    @GetMapping("/posts")
    public String getAllPosts(Model vModel) {
        vModel.addAttribute("posts", postDao.findAll());
        return "posts/index";
    }

    @GetMapping("/posts/{id}")
    public String getPostById(@PathVariable long id, Model vModel) {
        Post postToView = postDao.getOne(id);
        vModel.addAttribute("post", postToView);
        vModel.addAttribute("titleMsg", "Post - " + postToView.getTitle());
        return "posts/show";
    }

    @GetMapping("/posts/create")
    public String getCreatePostForm() {
        return "posts/create";
    }

    @PostMapping("/posts/create")
    public String submitCreatePostForm(@RequestParam("title") String title, @RequestParam("body") String body) {
        long newPostId = postDao.save(new Post(title, body)).getId();
        return "redirect:/posts/" + newPostId;
    }

    @PostMapping("/posts/delete")
    public String deletePost(@RequestParam("id") String id) {
        long deletePostId = Long.parseLong(id);
        postDao.deleteById(deletePostId);
        return "redirect:/posts";
    }

    @GetMapping("/posts/edit")
    public String getEditPostForm(@RequestParam("id") String id, Model vModel) {
        long editPostId = Long.parseLong(id);
        vModel.addAttribute("post", postDao.getOne(editPostId));
        return "posts/edit";
    }

    @PostMapping("/posts/edit")
    public String editPost(@RequestParam("id") String id, @RequestParam("title") String title,
                           @RequestParam("body") String body) {
        long editPostId = Long.parseLong(id);
        Post posttoEdit = postDao.getOne(editPostId);
        posttoEdit.setBody(body);
        posttoEdit.setTitle(title);
        postDao.save(posttoEdit);
        return "redirect:/posts/" + editPostId;
    }


}
