package com.myblog7.service;

import com.myblog7.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(long postId);

   CommentDto getCommentsById(Long postId, long commentId);

    List<CommentDto> getAllCommentsById();

    void deleteCommentById(Long postId, long commentId);
}