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
    String content;
    String date;
    String name;
    String region;
    String label;
}
