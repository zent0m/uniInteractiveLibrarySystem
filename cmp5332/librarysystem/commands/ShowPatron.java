package bcu.cmp5332.librarysystem.commands;

import java.time.LocalDate;
import java.util.List;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.model.Book;

public class ShowPatron implements Command {

    private final int patronId;

    public ShowPatron(int patronId) {
        this.patronId = patronId;
    }

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {

        Patron patron = library.getPatronByID(patronId);
        List<Book> books = patron.getBooks();

        if (library.getPatrons().contains(patron) && !patron.isHidden()) {
            System.out.println(patron.getDetails());
            if (!books.isEmpty()) {
                System.out.println("\nThe patron currently has "+books.size()+" book(s) loaned out. Details for the book(s):\n");
                for (Book book : books) {
                    System.out.println(book.getDetailsShort() + ". Due Date for return: " + book.getDueDate());
                }
            }
        }
        else {
            String message = "The ID of the specified Patreon wasn't found!";
            throw new LibraryException(message);
        }
    }
}
