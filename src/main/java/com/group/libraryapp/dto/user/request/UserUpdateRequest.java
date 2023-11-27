package com.group.libraryapp.dto.user.request;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class UserUpdateRequest {

  private long id;

  @NotNull
  private String name;

  public UserUpdateRequest(long id, @NotNull String name) {
    this.id = id;
    this.name = name;
  }
}
