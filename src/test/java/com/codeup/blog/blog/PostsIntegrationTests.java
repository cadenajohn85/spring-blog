package com.codeup.blog.blog;

import com.codeup.blog.blog.models.Post;
import com.codeup.blog.blog.models.User;
import com.codeup.blog.blog.repositories.PostRepository;
import com.codeup.blog.blog.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpSession;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogApplication.class)
@AutoConfigureMockMvc
public class PostsIntegrationTests {

    private User testUser;
    private HttpSession httpSession;

    @Autowired
    private MockMvc mvc;

    @Autowired
    UserRepository userDao;

    @Autowired
    PostRepository postDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void setup() throws Exception {

        testUser = userDao.findByUsername("testUser");

        // Creates testUser if not existing in the database

        if(testUser == null) {
            User newUser = new User();
            newUser.setUsername("testUser");
            newUser.setPassword(passwordEncoder.encode("pass"));
            newUser.setEmail("testUser@codeup.com");
            testUser = userDao.save(newUser);
        }

        // Throws a Post request to /login and expects redirection to the Posts index page after log-in
        httpSession = this.mvc.perform(post("/login").with(csrf())
                .param("username", "testUser")
                .param("password", "pass"))
                .andExpect(status().is(HttpStatus.FOUND.value()))
                .andExpect(redirectedUrl("/posts"))
                .andReturn()
                .getRequest()
                .getSession();
    }

    // Confirm the MVC bean is working
    @Test
    public void contextLoads() {
        assertNotNull(mvc);
    }

    // Confirm returned session is not null
    @Test
    public void testIfUserSessionIsActive() throws Exception {
        assertNotNull(httpSession);
    }

    // Test CRUD functionality: Create a Post
    @Test
    public void testCreatePost() throws Exception {
        // Make a post request to /posts/create and expect redirection to the post
        this.mvc.perform(
                post("/posts/create").with(csrf())
                    .session((MockHttpSession) httpSession)
                    .param("title", "Integration Test Title")
                    .param("body", "Integration Test Body"))
                .andExpect(status().is3xxRedirection());
    }

    // Test CRUD functionality: Read a post within its individual (show) view
    @Test
    public void testShowPost() throws Exception {

        Post existingPost = postDao.findAll().get(0);

        // Make a get request to /posts/{id} and expect redirection to that post's show page
        this.mvc.perform(get("/posts/" + existingPost.getId()))
                .andExpect(status().isOk())
                // Test dynamic page content
                .andExpect(content().string(containsString(existingPost.getBody())));
    }

    // Test CRUD functionality: Read from the index (all posts) view
    @Test
    public void testPostsIndex() throws Exception {
        Post existingPost = postDao.findAll().get(0);

        // Make a get request to /posts and verify presence of static page content, dynamic content from existingPost
        this.mvc.perform(get("/posts"))
                .andExpect(status().isOk())
                // Test static page content
                .andExpect(content().string(containsString("View All Posts")))
                // Test dynamic content from existingPost
                .andExpect(content().string(containsString(existingPost.getTitle())));
    }

    // Test CRUD functionality: Update an existing post, and confirm the updated text is present in that post's view
    @Test
    public void testEditPost() throws Exception {
        Post existingPost = postDao.findAll().get(0);

        // Make a post request to /posts/{id}/edit and expect redirection to that post's show page
        this.mvc.perform(
                post("/posts/" + existingPost.getId() + "/edit").with(csrf())
                    .session((MockHttpSession) httpSession)
                    .param("title", "Edited Test Post Title")
                    .param("body", "Edited test post body"))
                .andExpect(status().is3xxRedirection());

        // Make a get request to /posts/{id} and confirm edited post content is viewable
        this.mvc.perform(get("/posts/" + existingPost.getId()))
                .andExpect(status().isOk())
                // Test dynamic content from existingPost has been edited
                .andExpect(content().string(containsString("Edited Test Post Title")))
                .andExpect(content().string(containsString("Edited test post body")));
    }

    // Test CRUD functionality: Delete the post created during the Create Test
    @Test
    public void testDeleteAd() throws Exception {
        // Finds the post created during the Create Test
        Post existingPost = postDao.findByTitle("Integration Test Title");

        // Make a post request to /posts/{id}/delete and expect redirection to Post index
        this.mvc.perform(
                post("/posts/" + existingPost.getId() + "/delete").with(csrf())
                    .session((MockHttpSession) httpSession)
                    .param("id", String.valueOf(existingPost.getId())))
                .andExpect(status().is3xxRedirection());
    }

}
