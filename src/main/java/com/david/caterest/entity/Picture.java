package com.david.caterest.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@Entity
public class Picture {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private User user;
    private String description;
    @Lob
    private Byte[] image;
    private LocalDateTime dateOfPost;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "picture")
    private List<Comment> comments = new ArrayList<>();
}
