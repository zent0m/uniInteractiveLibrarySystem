package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.commands.BorrowBook;
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

public class BorrowBookWindow extends JFrame implements ActionListener {

    private MainWindow mw;
    private JTextField bookIdText = new JTextField();
    private JTextField patronIdText = new JTextField();

    private JButton issueBtn = new JButton("Issue");
    private JButton cancelBtn = new JButton("Cancel");

    public BorrowBookWindow(MainWindow mw) {
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

        setTitle("Issue a Book");

        setSize(350, 200);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(5, 2));
        topPanel.add(new JLabel("This is the book issuing window, used to issue a book to a patron (Limit 3 per patron)\n\n"));
        topPanel.add(new JLabel("Please enter the ID of the Book to be ISSUED:"));
        topPanel.add(bookIdText);
        topPanel.add(new JLabel("\nPlease enter the ID of the Patron the book will be ISSUED TO:"));
        topPanel.add(patronIdText);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(2, 3));
        bottomPanel.add(new JLabel("     "));
        bottomPanel.add(new JLabel("     "));
        bottomPanel.add(issueBtn);
        bottomPanel.add(cancelBtn);

        issueBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        this.getContentPane().add(topPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == issueBtn) {
            issueBook();
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
        }

    }

    private void issueBook() {
        try {
            int bookId = Integer.parseInt(bookIdText.getText());
            int patronId = Integer.parseInt(patronIdText.getText());
            // create and execute the BorrowBook Command
            Command issueBook = new BorrowBook(patronId, bookId);
            issueBook.execute(mw.getLibrary(), LocalDate.now());
            // refresh the view with the list of books
            mw.displayBooks();
            // hide (close) the BorrowBooksWindow
            this.setVisible(false);
            JOptionPane.showMessageDialog(null, "The process has finished!");
        } catch (LibraryException ex) {
            JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
