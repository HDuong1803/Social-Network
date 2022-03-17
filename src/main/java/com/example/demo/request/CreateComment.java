package com.example.demo.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateComment {

    private String text;
    private Long postId;

}
