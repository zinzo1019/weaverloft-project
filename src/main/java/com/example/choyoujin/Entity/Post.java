package com.example.choyoujin.Entity;

import javax.persistence.*;

@Entity
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String title;
    private String content;
}