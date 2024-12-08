package com.webwork.service.impl;

import com.webwork.models.Book;
import com.webwork.models.Catelog;
import com.webwork.models.Member;
import com.webwork.service.LibService;
import com.webwork.service.MemberService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LibServiceImpl implements LibService {

  private final Scanner scanner = new Scanner(System.in);
  private final MemberService memberService = new MemberServiceImpl();

  @Override
  public void displayAvailableBooks() {
    System.out.println("Available Books:");
    Catelog.BOOKLIST.forEach(book ->
        System.out.printf("ID: %s, Title: %s, Author: %s, Available Copies: %d/%d%n",
            book.getId(), book.getTitle(), book.getAuthor(),
            book.getAvailableCopies(), book.getTotalCopies())
    );
  }

  @Override
  public void selectTheBook() {
    System.out.println("Enter the Book ID to borrow:");
    String selectedBookId = scanner.next();

    // Find the book by ID
    Optional<Book> bookOptional = Catelog.BOOKLIST.stream()
        .filter(book -> book.getId().equals(selectedBookId))
        .findFirst();

    if (bookOptional.isPresent()) {
      Book book = bookOptional.get();
      // Check if there are available copies
      if (book.getAvailableCopies() > 0) {
        // Add the book to the member's account
        if (memberService.addBooKById(selectedBookId)) {
          // Decrement the available copies
          book.borrowCopy();
          // Update the book in the static list
          updateBookInCatalog(book);
          System.out.println("Book borrowed successfully.");
        } else {
          System.out.println("Unable to add the book to the account.");
        }
      } else {
        System.out.println("No copies of the book are currently available.");
      }
    } else {
      System.out.println("Book ID not found. Please try again.");
    }
  }


  private void updateBookInCatalog(Book updatedBook) {
    for (int i = 0; i < Catelog.BOOKLIST.size(); i++) {
      if (Catelog.BOOKLIST.get(i).getId().equals(updatedBook.getId())) {
        Catelog.BOOKLIST.set(i, updatedBook);
        break;
      }
    }
  }


  @Override
  public void displayUserAccountWithBooks() {
    System.out.println("===================================================");
    System.out.println("Account Name: " + Catelog.LOGINUSERNAME);

    Optional<Member> member = Catelog.MEMBERLIST.stream()
        .filter(m -> m.getUsername().equals(Catelog.LOGINUSERNAME))
        .findFirst();

    member.ifPresentOrElse(m -> {
      List<Book> books = m.getAcquiredBookList();
      if (books.isEmpty()) {
        System.out.println("No books borrowed to your account.");
      } else {
        System.out.println("Number of books borrowed to your account:");
        books.forEach(book -> System.out.printf("%s: %s%n", book.getId(), book.getTitle()));
      }
      System.out.println("===================================================");
    }, () -> System.out.println("Account not found."));
  }

  @Override
  public void returnTheBook() {
    System.out.println("Enter the Book ID to return:");
    String selectedBookId = scanner.next();
    if (memberService.returnBooKById(selectedBookId)) {
      System.out.println("Book returned successfully.");
    } else {
      System.out.println("Unable to return the book. Please check the details.");
    }
  }

  @Override
  public void displayBorrowedBooks() {
    System.out.println("Borrowed Books:");
    Catelog.BOOKLIST.stream()
        .filter(book -> book.getAvailableCopies() < book.getTotalCopies())
        .forEach(book -> System.out.printf("ID: %s, Title: %s%n", book.getId(), book.getTitle()));
  }

  @Override
  public void addBookIntoTheLib() {
    System.out.println("Enter the Book ID:");
    String bookId = scanner.next();
    System.out.println("Enter the Book Title:");
    String bookTitle = scanner.next();
    System.out.println("Enter the Book AuthorName:");
    String authorName = scanner.next();
    System.out.println("Enter the total number of copies:");
    int totalCopies = scanner.nextInt();

    boolean bookExists = Catelog.BOOKLIST.stream()
        .anyMatch(book -> book.getId().equals(bookId));

    if (bookExists) {
      System.out.println("Book ID already exists. Cannot add duplicate books.");
    } else {
      Catelog.BOOKLIST.add(new Book(bookId, bookTitle, authorName, totalCopies));
      System.out.println("Book added successfully.");
    }
  }

  @Override
  public synchronized void removeBookIntoTheLib() {
    System.out.println("Enter the Book ID to remove:");
    String bookId = scanner.next();
    // Find the book with the given ID
    Optional<Book> bookOptional = Catelog.BOOKLIST.stream()
        .filter(book -> book.getId().equals(bookId))
        .findFirst();
    // If the book exists in the library
    if (bookOptional.isPresent()) {
      Book book = bookOptional.get();
      // Check if the book is currently borrowed by any member
      List<Member> borrowers = Catelog.MEMBERLIST.stream()
          .filter(member -> member.getAcquiredBookList().contains(book))
          .collect(Collectors.toList());

      if (borrowers.isEmpty()) {
        // If no members have borrowed the book, remove it from the library
        Catelog.BOOKLIST.remove(book);
        System.out.println("Book removed successfully from the library.");
      } else {
        // If the book is borrowed, display the list of members who have it
        System.out.println("The following members have borrowed this book and it cannot be removed yet:");
        borrowers.forEach(member -> System.out.println(member.getUsername()));
      }
    } else {
      System.out.println("Book ID not found in the library.");
    }
  }

}
