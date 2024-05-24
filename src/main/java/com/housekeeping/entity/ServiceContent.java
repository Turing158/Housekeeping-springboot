package com.housekeeping.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceContent {
    int id;
    String title;
    String date;
    String content;
    String user;
    String name;
    String region;
    String label;
    String avatar;
    double value;

}
