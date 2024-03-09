package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.time.LocalDate;
import java.util.List;

public class ListPatrons implements Command {

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        List<Patron> patrons = library.getPatrons();
        for (Patron patron : patrons) {
            if (!patron.isHidden()) {System.out.println(patron.getDetails());}
        }
        System.out.println(library.getPatronsSize(patrons) + " patron(s)");
    }
}
