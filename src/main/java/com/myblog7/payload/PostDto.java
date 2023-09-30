package com.myblog7.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data

public class PostDto {//many time you dont want to return entity content,not to expose entity class directly so we crete dto object

    private Long id;
    // title should not be null or empty
    // title should have at least 2 characters
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;
    // title should not be null or empty
    // title should have at least 2 characters
    @NotEmpty
    @Size(min = 4, message = "Post description should have at least 4 characters")
    private String description;
    // title should not be null or empty
    // title should have at least 2 characters
    @NotEmpty
    @Size(min = 5, message = "Post content should have at least 5 characters")
    private String content;
}
