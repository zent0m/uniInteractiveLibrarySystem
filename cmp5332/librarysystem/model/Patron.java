package bcu.cmp5332.librarysystem.model;

import bcu.cmp5332.librarysystem.main.LibraryException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Patron {
    
    private int id;
    private String name;
    private String phone;
    private String email;
    private final List<Book> books = new ArrayList<>();

    private Boolean hidden = false;
    
    public Patron(int id, java.lang.String name, java.lang.String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {return this.id;}

    public void setId​(int id) {this.id = id;}

    public java.lang.String getName() {return this.name;}

    public void setName​(java.lang.String name) {this.name = name;}

    public java.lang.String getPhone() {return this.phone;}

    public void setPhone​(java.lang.String phone) {this.phone = phone;}

    public java.lang.String getEmail() {return this.email;}

    public void setEmail​(java.lang.String email) {this.email = email;}

    public Boolean isHidden() {return hidden;}

    public void setHidden() {hidden = !hidden;}
 
    public java.util.List<Book> getBooks() {return Collections.unmodifiableList(books);}

    public void addBook(Book book) {books.add(book);}

    public String getDetails() {return "Patron #" + id + ", Name: " + name + ", Phone: " + phone + ", Email: " + email;}
 
    public void borrowBook(Book book, LocalDate dueDate) throws LibraryException {
        if (book.isOnLoan()) {
            String message = "This book is already loaned out.";
            throw new LibraryException(message);
        }
        else {
            Loan loan = new Loan(this, book, LocalDate.now(), dueDate);
            book.setLoan(loan);
            books.add(book);
        }
    }

    public void renewBook(Book book, LocalDate dueDate) throws LibraryException {
        if (this.books.contains(book)) {
            book.getLoan().setDueDate​(dueDate);
    	}
    	else {
    		String message = "The specified book isn't on loan to this patron!";
    		throw new LibraryException(message);
    	}
    }

    public void returnBook(Book book, LocalDate returnDate) throws LibraryException {
        if (this.books.contains(book)) {
            book.getLoan().setTermination();
            book.getLoan().setReturnDate​(returnDate);
            this.books.remove(book);
            book.returnToLibrary();
        }
        else {
            String message = "The specified book isn't on loan to this patron!";
    		throw new LibraryException(message);
        }
    }

    public void removeBook(Book book) {
        this.books.remove(book);
    }
}
 