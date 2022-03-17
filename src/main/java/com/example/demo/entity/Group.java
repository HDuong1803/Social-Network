package com.example.demo.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "groups")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Group {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer code;
    private String description;

    @OneToMany()
    private List<Manager> managers = new ArrayList<>();

    @OneToMany()
    private List<Member> members = new ArrayList<>();

    @OneToMany()
    private List<MemberRequest> memberRequests = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "profile_id")
    private Profile creator;

    private Date date;

    private Date updateDate;



    
}
