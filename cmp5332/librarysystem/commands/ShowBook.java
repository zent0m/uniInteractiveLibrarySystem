package bcu.cmp5332.librarysystem.commands;

import java.time.LocalDate;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Book;

public class ShowBook implements Command {

    private final int bookId;

    public ShowBook(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {

        Book book = library.getBookByID(bookId);

        if (library.getBooks().contains(book) && !book.isHidden()) {
            System.out.println(book.getDetailsLong());
            if (book.isOnLoan()) {
                System.out.println("\nThis book is currently on loan to:\n" + book.getLoan().getPatron().getDetails() + "\n\nThe due date of the loan is: " + book.getDueDate());
            }
        }
        else {
            String message = "The ID of the specified Book wasn't found!";
            throw new LibraryException(message);
        }
    }
}
