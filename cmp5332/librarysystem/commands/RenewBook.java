package bcu.cmp5332.librarysystem.commands;

import java.io.IOException;
import java.time.LocalDate;

import javax.swing.JOptionPane;

import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

public class RenewBook implements Command {

    private final int patronId;
    private final int bookId;

    public RenewBook(int patronId, int bookId) {
        this.patronId = patronId;
        this.bookId = bookId;
    }

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException, IOException {

        Book book = library.getBookByID(bookId);
        Patron patron = library.getPatronByID(patronId);

        if ((library.getPatrons().contains(patron) && !patron.isHidden()) && (library.getBooks().contains(book) && !book.isHidden())) {
            patron.renewBook(book, currentDate.plusDays(library.getLoanPeriod()));
            System.out.println("The loan was renewed for " + library.getLoanPeriod() + " more days from now.");
            try {
                LibraryData.store(library);
            } catch (IOException e) {
                patron.renewBook(book, currentDate.minusDays(library.getLoanPeriod()));
                JOptionPane.showMessageDialog(null, "There was an error saving the files! See console for details.\n\n Changes have been rolled back...\n\n(Please ignore the next popup)");
                System.err.println("There was an error saving the files. Changes have been rolled back: ERROR: " + e.getMessage());
            }
        }
        else {  
            String message = "The ID of the specified Patreon or Book wasn't found!";
            throw new LibraryException(message);
        }
    }
}