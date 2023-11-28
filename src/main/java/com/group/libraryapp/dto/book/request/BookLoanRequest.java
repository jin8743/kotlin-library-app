package com.group.libraryapp.dto.book.request;

import lombok.Getter;

@Getter
public class BookLoanRequest {

  private String userName;
  private String bookName;


  public BookLoanRequest(String userName, String bookName) {
    this.userName = userName;
    this.bookName = bookName;
  }
}
