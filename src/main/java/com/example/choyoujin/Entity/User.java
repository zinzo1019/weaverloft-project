package com.example.choyoujin.Entity;

import lombok.Data;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "tb_user")
public class User {
    @Id
    private String email;
    private String pw;
    private String name;
    private String role;
    private int enabled;
}
