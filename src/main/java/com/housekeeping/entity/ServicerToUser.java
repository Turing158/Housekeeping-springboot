package com.housekeeping.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicerToUser {
    String user;
    String name;
    String certificate;
    String experience;
    String region;
    String company;
}
