package com.housekeeping.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    String user;
    String password;
    String name;
    String role;
    String region;
    String avatar;
}
