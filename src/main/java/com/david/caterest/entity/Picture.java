package com.david.caterest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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
    @Column(length = 6_220_800) // 6.2208MB Max size for image
    private Byte[] image;
    private LocalDateTime dateOfPost;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "picture")
    private List<Comment> comments = new ArrayList<>();
}
