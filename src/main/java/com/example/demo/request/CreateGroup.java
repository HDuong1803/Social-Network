package com.example.demo.request;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateGroup {

    private String name;
    private Integer code;
    private String description;
    
}
