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
public class Picture {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private User user;
    private String description;
    @Lob
    private Byte[] image;
    private LocalDateTime dateOfPost;
}
