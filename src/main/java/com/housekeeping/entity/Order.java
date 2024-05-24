package com.housekeeping.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    String id;
    String createUser;
    String createDate;
    int service;
    String reservedUser;
    String reservedDate;
    String reservedPlace;
    String note;
    double value;
    String status;
    String userEvaluateStar;
    String userEvaluate;
}
