package com.david.caterest.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;
    @Lob
    private Byte[] image;
    private LocalDate dateOfPost;
}
