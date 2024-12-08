package com.webwork.models;

public class Book {
  private String id;
  private String title;
  private String author;
  private int totalCopies;    // Total copies of the book
  private int availableCopies; // Available copies of the book

  public Book(String id, String title, String author, int totalCopies) {
    this.id = id;
    this.title = title;
    this.author = author;
    this.totalCopies = totalCopies;
    this.availableCopies = totalCopies; // Initially, all copies are available
  }

  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }

  public int getTotalCopies() {
    return totalCopies;
  }

  public int getAvailableCopies() {
    return availableCopies;
  }

  public void borrowCopy() {
    if (availableCopies > 0) {
      availableCopies--;
    } else {
      throw new IllegalStateException("No available copies to borrow.");
    }
  }

  public void returnCopy() {
    if (availableCopies < totalCopies) {
      availableCopies++;
    } else {
      throw new IllegalStateException("All copies are already returned.");
    }
  }
}
