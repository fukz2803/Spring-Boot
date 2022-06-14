package com.example.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {
    private int id;
    private String name;
    private String email;
    private String phone; 
    private String address;
    private String avatar;
    private String password;
}
