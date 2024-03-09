package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.commands.ReturnBook;
import bcu.cmp5332.librarysystem.commands.Command;
import bcu.cmp5332.librarysystem.main.LibraryException;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class ReturnBookWindow extends JFrame implements ActionListener {

    private MainWindow mw;
    private JTextField bookIdText = new JTextField();
    private JTextField patronIdText = new JTextField();

    private JButton returnBtn = new JButton("Return");
    private JButton cancelBtn = new JButton("Cancel");

    public ReturnBookWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {

        } 

        setTitle("Return a Book");

        setSize(350, 200);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(5, 2));
        topPanel.add(new JLabel("This is the book return window, used to return a book to the library.\n\n"));
        topPanel.add(new JLabel("Please enter the ID of the Book to be RETURNED:"));
        topPanel.add(bookIdText);
        topPanel.add(new JLabel("\nPlease enter the ID of the Patron who will be RETURNING the book:"));
        topPanel.add(patronIdText);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(2, 3));
        bottomPanel.add(new JLabel("     "));
        bottomPanel.add(new JLabel("     "));
        bottomPanel.add(returnBtn);
        bottomPanel.add(cancelBtn);

        returnBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        this.getContentPane().add(topPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == returnBtn) {
            returnBook();
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
        }

    }

    private void returnBook() {
        try {
            int bookId = Integer.parseInt(bookIdText.getText());
            int patronId = Integer.parseInt(patronIdText.getText());
            // create and execute the ReturnBook Command
            Command returnBook = new ReturnBook(patronId, bookId);
            returnBook.execute(mw.getLibrary(), LocalDate.now());
            // refresh the view with the list of books
            mw.displayBooks();
            // hide (close) the ReturnBooksWindow
            this.setVisible(false);
            JOptionPane.showMessageDialog(null, "The book was successfully returned!");
        } catch (LibraryException ex) {
            JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
