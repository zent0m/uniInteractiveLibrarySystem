package bcu.cmp5332.librarysystem.model;

import bcu.cmp5332.librarysystem.main.LibraryException;
import java.util.*;

public class Library {
    
    private final int loanPeriod = 7;
    private final int maxBooks = 3;
    private final Map<Integer, Patron> patrons = new TreeMap<>();
    private final Map<Integer, Book> books = new TreeMap<>();
    private final List<Loan> loans = new ArrayList<>();

    public int getLoanPeriod() {return loanPeriod;}

    public int getMaxBooks() {return maxBooks;}

    public List<Book> getBooks() {
        List<Book> out = new ArrayList<>(books.values());
        return Collections.unmodifiableList(out);
    }

    public List<Patron> getPatrons() {
        List<Patron> out = new ArrayList<>(patrons.values());
        return Collections.unmodifiableList(out);
    }

    public List<Loan> getLoans() {
        return Collections.unmodifiableList(loans);
    }

    public Book getBookByID(int id) throws LibraryException {
        if (!books.containsKey(id)) {
            throw new LibraryException("There is no such book with that ID.");
        }
        return books.get(id);
    }

    public Patron getPatronByID(int id) throws LibraryException {
        if (!patrons.containsKey(id)) {
            throw new LibraryException("There is no patron with that ID.");
        }
        return patrons.get(id);
    }

    public void addBook(Book book) {
        if (books.containsKey(book.getId())) {
            throw new IllegalArgumentException("Duplicate book ID.");
        }
        books.put(book.getId(), book);
    }

    public void addPatron(Patron patron) {
        if (patrons.containsKey(patron.getId())) {
            throw new IllegalArgumentException("Duplicate patron ID.");
        }
        patrons.put(patron.getId(), patron);
    }

    public void addLoan(Loan loan) {
        loans.add(loan);
    }

    public void removeLoan(Loan loan) {
        loans.remove(loan);
    }

    public int getPatronsSize(List<Patron> patrons) {
        int count = 0;
        for (Patron patron : patrons) {
            if (patron.isHidden()) {count += 1;}
        }
        return patrons.size() - count;
    }

    public int getBooksSize(List<Book> books) {
        int count = 0;
        for (Book book : books) {
            if (book.isHidden()) {count += 1;}
        }
        return books.size() - count;
    }
}
 