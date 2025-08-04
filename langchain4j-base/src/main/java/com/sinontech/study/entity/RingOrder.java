package com.sinontech.study.entity;

import lombok.Data;

import java.util.Date;

@Data
public class RingOrder {
    private Long id;
    private String userId;
    private String ringName;
    private String ringType;
    private Date orderTime;
    private String status;
}
