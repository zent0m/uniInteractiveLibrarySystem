package bcu.cmp5332.librarysystem.commands;

import java.io.IOException;
import java.time.LocalDate;

import javax.swing.JOptionPane;

import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

public class BorrowBook implements Command {

    private final int patronId;
    private final int bookId;

    public BorrowBook(int patronId, int bookId) {
        this.patronId = patronId;
        this.bookId = bookId;
    }

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException, IOException {

        Book book = library.getBookByID(bookId);
        Patron patron = library.getPatronByID(patronId);

        if ((library.getPatrons().contains(patron) && !patron.isHidden()) && (library.getBooks().contains(book) && !book.isHidden())) {
            if (patron.getBooks().size() < library.getMaxBooks()) {
                patron.borrowBook(book, currentDate.plusDays(library.getLoanPeriod()));
                library.addLoan(book.getLoan());
                System.out.println("The book was loaned out to the patron successfully.");
                try {
                    LibraryData.store(library);
                } catch (IOException e) {
                    patron.removeBook(book);
                    library.removeLoan(book.getLoan());
                    book.returnToLibrary();
                    JOptionPane.showMessageDialog(null, "There was an error saving the files! See console for details.\n\n Changes have been rolled back...\n\n(Please ignore the next popup)");
                    System.err.println("There was an error saving the files. Changes have been rolled back: ERROR: " + e.getMessage());
                }
            } else {System.out.println("Could not loan the book. This patron has reached the maximum number of books that can be loaned.");}    
        }
        else {
            String message = "The ID of the specified Patreon or Book wasn't found!";
            throw new LibraryException(message);
        }
    }
}
