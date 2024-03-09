package bcu.cmp5332.librarysystem.commands;

import java.io.IOException;
import java.time.LocalDate;

import javax.swing.JOptionPane;

import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Loan;
import bcu.cmp5332.librarysystem.model.Patron;

public class ReturnBook implements Command {

    private final int patronId;
    private final int bookId;

    public ReturnBook(int patronId, int bookId) {
        this.patronId = patronId;
        this.bookId = bookId;
    }

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException, IOException {

         Book book = library.getBookByID(bookId);
         Patron patron = library.getPatronByID(patronId);

        if ((library.getPatrons().contains(patron) && !patron.isHidden()) && (library.getBooks().contains(book) && !book.isHidden())) {
            LocalDate oldStartDate = null;
            LocalDate oldDueDate = null;
            Loan oldLoan = null;

            if (book.getLoan() != null) {
                oldStartDate = book.getLoan().getStartDate();
                oldDueDate = book.getDueDate();
                oldLoan = book.getLoan();
            }

            patron.returnBook(book, currentDate);
            System.out.println("The book was returned to the library. The loan has been cleared.");
            try {
                LibraryData.store(library);
            } catch (IOException e) {
                Loan loan = new Loan(patron, book, oldStartDate, oldDueDate);
                library.removeLoan(oldLoan);
                book.setLoan(loan);
                patron.addBook(book);
                library.addLoan(loan);
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
