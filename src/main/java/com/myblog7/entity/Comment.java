package com.myblog7.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import javax.persistence.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {//@joinColumn should be written in child tablechild table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    private String body;
    @ManyToOne(fetch = FetchType.LAZY)//
    @JoinColumn(name = "post_id", nullable = false)//@join column joins entity class with comment class using post_id
    private Post post;
}
