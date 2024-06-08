package com.housekeeping.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployRecord {
    String id;
    String user;
    String truthName;
    String company;
    String experience;
    String certificate;
    String certificateInfo;
    String experienceInfo;
    String status;
    String date;
    String note;
}
