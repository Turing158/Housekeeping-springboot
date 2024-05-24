package com.housekeeping.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    int id;
    String companyName;
    String user;
    String name;
    String date;
}
