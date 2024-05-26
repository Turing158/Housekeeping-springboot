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
    String createName;
    String createDate;
    int service;
    String serviceName;
    String reservedUser;
    String reservedName;
    String reservedPhone;
    String reservedDate;
    String reservedPlace;
    String note;
    double value;
    String status;
    String userEvaluateStar;
    String userEvaluate;
}
