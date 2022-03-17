package com.example.demo.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage {

  private String message;
  private Object data;

  public ResponseMessage(String message) {
    this.message = message;
  }

  public ResponseMessage(Object data) {
    this.data = data;
  }

  

  

}
