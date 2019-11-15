package com.codeup.blog.blog.repositories;

import com.codeup.blog.blog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    Post findByTitle(String title);

    @Query(value = "SELECT * FROM posts WHERE user_id = :userId",
        nativeQuery = true)
    List<Post> findByUserId(@Param("userId") long userId);
}
