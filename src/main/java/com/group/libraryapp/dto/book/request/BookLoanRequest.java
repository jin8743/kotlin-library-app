package com.group.libraryapp.dto.book.request;

import lombok.Getter;

@Getter
public class BookLoanRequest {

  public String userName;
  public String bookName;


  public BookLoanRequest(String userName, String bookName) {
    this.userName = userName;
    this.bookName = bookName;
  }
}
