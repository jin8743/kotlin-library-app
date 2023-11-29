package com.group.libraryapp.dto.book.request;

import lombok.Getter;

@Getter
public class BookRequest {

  public String name;

  public BookRequest(String name) {
    this.name = name;
  }
}
