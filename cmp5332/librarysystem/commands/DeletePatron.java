package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;

import java.io.IOException;
import java.time.LocalDate;

import javax.swing.JOptionPane;


/**
 * "Deletes" a patron from the system.
 * 
 * It is an implementation of the {@link Command} interface and its {@link #execute(Library, LocalDate) execute(Library, LocalDate)} method adds a new Patron to the library.
 * 
 * It is created and executed by the {@link CommandParser} when the "deletepatron" command is given by the user, or through the GUI menu.
 * 
 * The class has a constructor to initialise the fields needed to execute the command.
 */
public class DeletePatron implements Command {

    private final int patronId;

    public DeletePatron(int patronId) {
        this.patronId = patronId;
    }

    
    /** "Deletes" a patron from the system.
     * This method is implemented from the command interface, and actually just hides the patron by switching a boolean flag in the patron object.
     * The method also tries saving the updated information to the files. If for some reason unsuccessful, the changes are rolled back and an IOException is thrown.
     * @param library the library object which is used to access the patron and book lists and other functions.
     * @param currentDate the current date at the time of execution. Part of the command interface.
     * @throws LibraryException custom exception. Already handled in functions accessed inside. Part of command interface.
     * @throws IOException thrown when there's an error with the file saving methodology.
     */
    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException, IOException {
    	library.getPatronByID(patronId).setHidden();
        System.out.println("Patron #" + patronId + " deleted.");
        try {
                LibraryData.store(library);
            } catch (IOException e) {
                library.getPatronByID(patronId).setHidden();
                JOptionPane.showMessageDialog(null, "There was an error saving the files! See console for details.\n\n Changes have been rolled back...\n\n(Please ignore the next popup)");
                System.err.println("There was an error saving the files. Changes have been rolled back: ERROR: " + e.getMessage());
            }
    }
}
 