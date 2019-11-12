package com.codeup.blog.blog.controllers;

import com.codeup.blog.blog.models.Post;
import com.codeup.blog.blog.repositories.PostRepository;
import com.codeup.blog.blog.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {

    private final PostRepository postDao;
    private final UserRepository userDao;

    public PostController(PostRepository postDao, UserRepository userDao) {
        this.postDao = postDao;
        this.userDao = userDao;
    }

    // View all posts
    @GetMapping("/posts")
    public String getAllPosts(Model vModel) {
        vModel.addAttribute("posts", postDao.findAll());
        return "posts/index";
    }

    // View a particular post by id
    @GetMapping("/posts/{id}")
    public String getPostById(@PathVariable long id, Model vModel) {
        Post postToView = postDao.getOne(id);
        vModel.addAttribute("post", postToView);
        vModel.addAttribute("titleMsg", "Post - " + postToView.getTitle());
        return "posts/show";
    }

    // View the "Create Post" form
    @GetMapping("/posts/create")
    public String getCreatePostForm(Model vModel) {
        vModel.addAttribute("post", new Post());
        return "posts/create";
    }

    // Submit the "Create Post" form
    @PostMapping("/posts/create")
    public String submitCreatePostForm(@ModelAttribute Post postFromForm) {
//        long newPostId = postDao.save(new Post(title, body, userDao.getOne(3L))).getId();

//        long newPostId = postDao.save(postFromForm.setUser(userDao.getOne(3L))).getId();
        postFromForm.setUser(userDao.getOne(3L));
        postDao.save(postFromForm);
        long newPostId = postFromForm.getId();
        return "redirect:/posts/" + newPostId;
    }

    // Delete a post via a "Delete" Button
    @PostMapping("/posts/delete")
    public String deletePost(@RequestParam("id") String id) {
        long deletePostId = Long.parseLong(id);
        postDao.deleteById(deletePostId);
        return "redirect:/posts";
    }

    // See the form to edit a post
    @GetMapping("/posts/edit")
    public String getEditPostForm(@RequestParam("id") String id, Model vModel) {
        long editPostId = Long.parseLong(id);
        vModel.addAttribute("post", postDao.getOne(editPostId));
        return "posts/edit";
    }

    // Submit the form to edit a post
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
