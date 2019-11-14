package com.codeup.blog.blog.controllers;

import com.codeup.blog.blog.models.Post;
import com.codeup.blog.blog.models.User;
import com.codeup.blog.blog.repositories.PostRepository;
import com.codeup.blog.blog.repositories.UserRepository;
import com.codeup.blog.blog.services.EmailService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {

    private final PostRepository postDao;
    private final UserRepository userDao;
    private final EmailService emailService;

    public PostController(PostRepository postDao, UserRepository userDao, EmailService emailService) {
        this.postDao = postDao;
        this.userDao = userDao;
        this.emailService = emailService;
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

    // View all of a logged-in user's posts
    @GetMapping("/posts/viewmine")
    public String getAllMyPosts(Model vModel) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Change the findAll method
        vModel.addAttribute("posts", postDao.findByUserId(loggedInUser.getId()));
        return "posts/viewmine";
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
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        postFromForm.setUser(userDao.findByUsername(loggedInUser.getUsername()));
        postDao.save(postFromForm);
        long newPostId = postFromForm.getId();
        emailService.prepareAndSend(
                postFromForm,
                "New Post on Spring in November",
                "Your post '" + postFromForm.getTitle() + "' is now viewable on Spring in November."
        );
        return "redirect:/posts/" + newPostId;
    }

    // Delete a post via a "Delete" Button
    @PostMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable("id") long id) {
        postDao.deleteById(id);
        return "redirect:/posts";
    }

    // See the form to edit a post
    @GetMapping("/posts/{id}/edit")
    public String getEditPostForm(@PathVariable long id, Model vModel) {
        vModel.addAttribute("post", postDao.getOne(id));
        return "posts/edit";
    }

    // Submit the form to edit a post
    @PostMapping("/posts/{id}/edit")
    public String editPost(@ModelAttribute Post editedPost) {
        editedPost.setUser(userDao.getOne(3L));
        postDao.save(editedPost);
        return "redirect:/posts/" + editedPost.getId();
    }

}
