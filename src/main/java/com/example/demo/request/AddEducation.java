package com.example.demo.request;

import java.util.Date;

import lombok.Data;

@Data
public class AddEducation {
    private String schooleName;
    private String fieldofstudy;
    private Date from;
    private Date to;
    private String description; 
}
