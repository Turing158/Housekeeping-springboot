package com.housekeeping.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceToUser {
    int id;
    String title;
    String date;
    String name;
    String user;
    String region;
    String label;
}
