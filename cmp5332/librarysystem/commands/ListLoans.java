package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Loan;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.time.LocalDate;
import java.util.List;

public class ListLoans implements Command {

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        List<Loan> loans = library.getLoans();
        for (Loan loan : loans) {
            System.out.println(loan.getDetails()); 
        }
        System.out.println(loans.size() + " loan(s) to date.");
    }
}
 