package com.myblog7.repository;

import com.myblog7.entity.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    //building custom method to build a query to search aperticular data based on perticular column of table
    List<Comment> findByPostId(long postId);//findbYCOLUMN NAMES equivalent to select* from comment sql query
}
