package com.webwork.models;

import java.util.List;

public class Member {

  private String id;

  private String name;

  private List<Book> acquiredBookList;

  private String role;

  private String username;

  private String password;

  public Member() {
  }

  public Member(String id, String name, List<Book> acquiredBookList, String role, String username,
      String password) {
    this.id = id;
    this.name = name;
    this.acquiredBookList = acquiredBookList;
    this.role = role;
    this.username = username;
    this.password = password;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Book> getAcquiredBookList() {
    return acquiredBookList;
  }

  public void setAcquiredBookList(List<Book> acquiredBookList) {
    this.acquiredBookList = acquiredBookList;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "Member{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", acquiredBookList=" + acquiredBookList +
        ", role='" + role + '\'' +
        ", username='" + username + '\'' +
        ", password='" + password + '\'' +
        '}';
  }
}
