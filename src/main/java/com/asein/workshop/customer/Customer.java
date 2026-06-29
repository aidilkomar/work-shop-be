package com.asein.workshop.customer;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class Customer {
    private Long id;
    private String name;
    private String phone;
    private String address;
    private Timestamp createdAt;
}
