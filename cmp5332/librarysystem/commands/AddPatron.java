package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.model.Library;

import java.io.IOException;
import java.time.LocalDate;

import javax.swing.JOptionPane;

public class AddPatron implements Command {

    private final String name;
    private final String phone;
    private final String email;

    public AddPatron(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException, IOException {
        int maxId = 0;
    	if (library.getPatrons().size() > 0) {
    		int lastIndex = library.getPatrons().size() - 1;
            maxId = library.getPatrons().get(lastIndex).getId();
    	}
        Patron patron = new Patron(++maxId, this.name, this.phone, this.email);
        library.addPatron(patron);
        System.out.println("Patron #" + patron.getId() + " added.");
        try {
            LibraryData.store(library);
        } catch (IOException e) {
            patron.setHidden();
            JOptionPane.showMessageDialog(null, "There was an error saving the files! See console for details.\n\n Changes have been rolled back...\n\n(Please ignore the next popup)");
            System.err.println("There was an error saving the files. Changes have been rolled back: ERROR: " + e.getMessage());
        }
    }
}
 