package bcu.cmp5332.librarysystem.model;

import java.time.LocalDate;

public class Loan {
    
    private Patron patron;
    private Book book;
    private LocalDate startDate;
    private LocalDate dueDate;

    private Boolean terminated = false;
    private LocalDate returnDate;

    public Loan(Patron patron, Book book, LocalDate startDate, LocalDate dueDate) {
        this.patron = patron;
        this.book = book;
        this.startDate = startDate;
        this.dueDate = dueDate;
    }
    
    public Patron getPatron() {return this.patron;}

    public void setPatron​(Patron patron) {this.patron = patron;}

    public Book getBook() {return this.book;}

    public void setBook​(Book book) {this.book = book;}

    public java.time.LocalDate getStartDate() {return this.startDate;}

    public void setStartDate​(java.time.LocalDate startDate) {this.startDate = startDate;}

    public java.time.LocalDate getDueDate() {return this.dueDate;}

    public void setDueDate​(java.time.LocalDate dueDate) {this.dueDate = dueDate;}

    public java.time.LocalDate getReturnDate() {return this.returnDate;}

    public void setReturnDate​(java.time.LocalDate returnDate) {this.returnDate = returnDate;}

    public Boolean isTerminated() {return terminated;}

    public void setTermination() {terminated = !terminated;}

    public String getDetails() {
        String message =  "\nPatron ID#" + patron.getId() + "\nPatron Name: " + patron.getName() + "\n" + book.getDetailsShort() + "\nBeginning: " + startDate + "\nDue: " + dueDate;
        if (terminated) {
            message += "\nReturned to library on: " + returnDate;
        }
        return message;
    }
}
 