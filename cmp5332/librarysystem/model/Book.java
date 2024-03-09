package bcu.cmp5332.librarysystem.model;

import bcu.cmp5332.librarysystem.main.LibraryException;
import java.time.LocalDate;

public class Book {
    
    private int id;
    private String title;
    private String author;
    private String publicationYear;
    private String publisher;

    private Boolean hidden = false;

    private Loan loan;

    public Book(int id, String title, String author, String publicationYear, String publisher) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.publisher = publisher;
    }

    public int getId() {return id;} 

    public void setId(int id) {this.id = id;}

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}
    
    public String getAuthor() {return author;}
    
    public void setAuthor(String author) {this.author = author;}

    public String getPublicationYear() {return publicationYear;}

    public void setPublicationYear(String publicationYear) {this.publicationYear = publicationYear;}

    public String getPublisher() {return publisher;}

    public void setPublisher(String publisher) {this.publisher = publisher;}

    public Boolean isHidden() {return hidden;}

    public void setHidden() {hidden = !hidden;}
	
    public String getDetailsShort() {return "Book #" + id + " - " + title;}

    public String getDetailsLong() {return "Book "+id+"\n"+"Title: "+ title+ "\n" + "Author: "+ author+"\n"+"Publication year: "+publicationYear+"\nPublisher: "+ publisher;}
    
    public boolean isOnLoan() {return (loan != null);}
    
    public String getStatus() {
        if (loan == null) {return "Available";}
        else {return "On Loan";}
    }

    public LocalDate getDueDate() {return loan.getDueDate();}
    
    public void setDueDate(LocalDate dueDate) throws LibraryException {
        if (loan == null) {
            throw new LibraryException("The book isn't on loan.");
        } else {
            loan.setDueDate​(dueDate);
        }  
    }

    public Loan getLoan() {return loan;}

    public void setLoan(Loan loan) {this.loan = loan;}

    public void returnToLibrary() {loan = null;}
}
