package com.david.caterest.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@RequiredArgsConstructor
@Entity
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    Picture picture; // The picture, the comment belongs to.
    @OneToOne
    User user; // User that posted the comment
    LocalDateTime dateOfPost;
    String commentText;
}