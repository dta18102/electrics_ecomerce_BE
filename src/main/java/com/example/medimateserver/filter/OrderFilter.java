package com.example.medimateserver.filter;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
public class OrderFilter {
    private Integer date;
    private Date minDate;
    private Date maxDate;
}
