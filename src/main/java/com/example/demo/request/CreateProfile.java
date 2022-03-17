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
public class CreateProfile {
    private String website;
    private String location;
    private Set<Skill> skill;
    private List<Social> social;
    private List<Experience> experiences;
    private List<Education> educations;
    
}
