package com.example.demo.request;


import java.util.List;
import java.util.Set;

import com.example.demo.entity.Education;
import com.example.demo.entity.Experience;
import com.example.demo.entity.Social;
import com.example.demo.entity.Skill;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SetManager {

    private Long groupId;
    private Long profileId;
    private String role;
    
}
