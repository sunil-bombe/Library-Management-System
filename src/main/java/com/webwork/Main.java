package com.webwork;

import com.webwork.models.Book;
import com.webwork.models.Catelog;
import com.webwork.models.Member;
import com.webwork.service.LibService;
import com.webwork.service.MemberService;
import com.webwork.service.impl.LibServiceImpl;
import com.webwork.service.impl.MemberServiceImpl;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

  static {
    initializeData();
  }

  public static void main(String[] args) {
    MemberService memberService = new MemberServiceImpl();
    LibService libService = new LibServiceImpl();
    Scanner scanner = new Scanner(System.in);

    System.out.println("Hello and welcome to Library Management System!");

    System.out.print("Enter your username: ");
    String username = scanner.next();
    System.out.print("Enter Password: ");
    String password = scanner.next();

    if (!memberService.login(username, password)) {
      System.exit(0);
    }

    while (true) {
      int option = Catelog.ROLE.equals("ADMIN") ? getAdminOptions(scanner) : getMemberOptions(scanner);
      handleOption(option, libService, scanner);
    }
  }

  private static void initializeData() {
    Catelog.MEMBERLIST.add(new Member("1", "Sahil", new ArrayList<>(), "ADMIN", "sahil", "sahil123"));
    Catelog.MEMBERLIST.add(new Member("2", "Ganesh", new ArrayList<>(), "MEMBER", "ganesh", "ganesh123"));
    Catelog.MEMBERLIST.add(new Member("3", "Rahul", new ArrayList<>(), "MEMBER", "rahul", "rahul123"));
    Catelog.MEMBERLIST.add(new Member("4", "Ramesh", new ArrayList<>(), "MEMBER", "ramesh", "ramesh123"));

    Catelog.BOOKLIST.add(new Book("1", "400 Days", "Chetan Bhagat", 5));
    Catelog.BOOKLIST.add(new Book("2", "Wings of Fire", "A.P.J. Abdul Kalam", 3));
    Catelog.BOOKLIST.add(new Book("3", "Attitude is Everything", "Jeff Keller", 4));
  }

  private static int getMemberOptions(Scanner scanner) {
    System.out.println("Welcome " + Catelog.LOGINUSERNAME);
    System.out.println("1. Borrow Book");
    System.out.println("2. Return Book");
    System.out.println("3. Logout");
    System.out.print("Enter your option: ");
    return scanner.nextInt();
  }

  private static int getAdminOptions(Scanner scanner) {
    System.out.println("Welcome " + Catelog.LOGINUSERNAME + " (" + Catelog.ROLE + ")");
    System.out.println("=====================================================");
    System.out.println("Options:");
    System.out.println("1. Borrow Book");
    System.out.println("2. Return Book");
    System.out.println("3. Logout");
    System.out.println("4. Add Book Into the Library");
    System.out.println("5. Remove Book From the Library");
    System.out.print("Enter your option: ");
    return scanner.nextInt();
  }

  private static void handleOption(int option, LibService libService, Scanner scanner) {
    switch (option) {
      case 1:
        libService.displayAvailableBooks();
        libService.selectTheBook();
        libService.displayUserAccountWithBooks();
        break;

      case 2:
        libService.displayBorrowedBooks();
        libService.returnTheBook();
        libService.displayUserAccountWithBooks();
        break;

      case 3:
        System.exit(0);
        break;

      case 4:
        if (isAdmin()) {
          libService.displayAvailableBooks();
          libService.addBookIntoTheLib();
          libService.displayUserAccountWithBooks();
        } else {
          displayPermissionDeniedMessage();
        }
        break;

      case 5:
        if (isAdmin()) {
          libService.displayAvailableBooks();
          libService.removeBookIntoTheLib();
          libService.displayUserAccountWithBooks();
        } else {
          displayPermissionDeniedMessage();
        }
        break;

      default:
        System.out.println("Error: Enter a valid action!");
    }
  }

  private static boolean isAdmin() {
    return "ADMIN".equals(Catelog.ROLE);
  }

  private static void displayPermissionDeniedMessage() {
    System.out.println("Error: Enter a valid action!/Dont have permission");
  }
}
