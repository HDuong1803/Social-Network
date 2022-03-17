package com.example.demo.request;

import java.util.Date;

import lombok.Data;

@Data

public class AddExperience {

    private String title;
    private String companyName;
    private String location;
    private Date from;
    private Date to;
    private String description;

}
