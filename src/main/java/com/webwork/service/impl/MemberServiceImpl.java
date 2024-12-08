package com.webwork.service.impl;

import com.webwork.models.Book;
import com.webwork.models.Catelog;
import com.webwork.models.Member;
import com.webwork.service.MemberService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MemberServiceImpl implements MemberService {

  @Override
  public boolean login(String username, String password) {
    Optional<Member> memberOptional = Catelog.MEMBERLIST.stream()
        .filter(member -> member.getUsername().equals(username) && member.getPassword().equals(password))
        .findFirst();
    if (memberOptional.isPresent()) {
      Member member = memberOptional.get();
      Catelog.LOGINUSERID = member.getId();
      Catelog.LOGINUSERNAME = member.getUsername();
      Catelog.ROLE = member.getRole();
      return true;
    } else {
      System.out.println("Entered Wrong Username or Password");
      return false;
    }
  }

  @Override
  public synchronized boolean addBooKById(String selectedBookId) {
    Optional<Book> bookOptional = Catelog.BOOKLIST.stream()
        .filter(book -> book.getId().equals(selectedBookId))
        .findFirst();
    if (bookOptional.isPresent()) {
      Book book = bookOptional.get();
      Optional<Member> memberOptional = Catelog.MEMBERLIST.stream()
          .filter(member -> member.getUsername().equals(Catelog.LOGINUSERNAME))
          .findFirst();
      if (memberOptional.isPresent()) {
        Member member = memberOptional.get();
        member.getAcquiredBookList().add(book);
        updateMemberInCatalog(member);
        System.out.println("Book is successfully added to the user account!");
        return true;
      } else {
        System.out.println("Logged-in user not found!");
        return false;
      }
    } else {
      System.out.println("Book with the given ID not found!");
      return false;
    }
  }



  @Override
  public synchronized boolean returnBooKById(String selectedBookId) {
    // Step 1: Find the book by ID in the catalog
    Optional<Book> bookOptional = Catelog.BOOKLIST.stream()
        .filter(book -> book.getId().equals(selectedBookId))
        .findFirst();

    if (bookOptional.isPresent()) {
      Book book = bookOptional.get();

      // Step 2: Find the logged-in member
      Optional<Member> memberOptional = Catelog.MEMBERLIST.stream()
          .filter(member -> member.getUsername().equals(Catelog.LOGINUSERNAME))
          .findFirst();

      if (memberOptional.isPresent()) {
        Member member = memberOptional.get();

        // Step 3: Try to remove the book from the member's list
        boolean bookRemoved = member.getAcquiredBookList().remove(book);

        if (bookRemoved) {
          // Step 4: Perform actions after removing the book from the member's list
          book.returnCopy(); // Assuming this method restores the book copy status

          // Step 5: Remove the book from the member's account (as part of member record update)
          removeBooKFromTheMemberAccount(book);

          // Step 7: Update the book details in the catalog
          updateBookInCatalog(book);

          System.out.println("Book is successfully returned to the library!");
          return true;
        } else {
          System.out.println("The book was not found in the user's borrowed list.");
          return false;
        }
      } else {
        System.out.println("Logged-in user not found!");
        return false;
      }
    } else {
      System.out.println("Book with the given ID not found!");
      return false;
    }
  }


  public synchronized boolean removeBooKFromTheMemberAccount(Book book) {
    Optional<Member> memberOptional = Catelog.MEMBERLIST.stream()
        .filter(member -> member.getUsername().equals(Catelog.LOGINUSERNAME))
        .findFirst();

    if (memberOptional.isPresent()) {
      Member member = memberOptional.get();
      boolean bookRemoved = member.getAcquiredBookList().remove(book);

      if (bookRemoved) {
        updateMemberInCatalog(member);
        System.out.println("Book is successfully removed from the user's account!");
        return true;
      } else {
        System.out.println("The book was not found in the user's borrowed list.");
        return false;
      }
    } else {
      System.out.println("Logged-in user not found!");
      return false;
    }
  }

  private void updateMemberInCatalog(Member updatedMember) {
    for (int i = 0; i < Catelog.MEMBERLIST.size(); i++) {
      if (Catelog.MEMBERLIST.get(i).getUsername().equals(updatedMember.getUsername())) {
        Catelog.MEMBERLIST.set(i, updatedMember);
        break;
      }
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

}
