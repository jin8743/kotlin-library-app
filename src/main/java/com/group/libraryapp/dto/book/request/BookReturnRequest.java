package com.group.libraryapp.dto.book.request;

import lombok.Getter;

@Getter
public class BookReturnRequest {

  public String userName;
  public String bookName;

  public BookReturnRequest(String userName, String bookName) {
    this.userName = userName;
    this.bookName = bookName;
  }
}
