package com.group.libraryapp.dto.book.request;

import lombok.Getter;

@Getter
public class BookRequest {

  private String name;

  public BookRequest(String name) {
    this.name = name;
  }
}
