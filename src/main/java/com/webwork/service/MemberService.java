package com.webwork.service;

public interface MemberService {

  boolean login(String username, String password);

  boolean addBooKById(String selectedBookId);

  boolean returnBooKById(String selectedBookId);
}
