package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.commands.DeletePatron;
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

public class DeletePatronWindow extends JFrame implements ActionListener {

    private MainWindow mw;
    private JTextField idText = new JTextField();

    private JButton deleteBtn = new JButton("Delete");
    private JButton cancelBtn = new JButton("Cancel");

    public DeletePatronWindow(MainWindow mw) {
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

        setTitle("Delete a Member");

        setSize(300, 200);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(5, 2));
        topPanel.add(new JLabel("Please enter the ID of the Member to be DELETED:"));
        topPanel.add(idText);
        topPanel.add(new JLabel("Are you sure you want to delete the member?:"));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3));
        bottomPanel.add(new JLabel("     "));
        bottomPanel.add(deleteBtn);
        bottomPanel.add(cancelBtn);

        deleteBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        this.getContentPane().add(topPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == deleteBtn) {
            deletePatron();
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
        }

    }

    private void deletePatron() {
        try {
            int id = Integer.parseInt(idText.getText());
            // create and execute the DeletePatron Command
            Command deletePatron = new DeletePatron(id);
            deletePatron.execute(mw.getLibrary(), LocalDate.now());
            // refresh the view with the list of patrons
            mw.displayMembers();
            // hide (close) the DeletePatronWindow
            this.setVisible(false);
            JOptionPane.showMessageDialog(null, "The patron was successfully deleted!");
        } catch (LibraryException ex) {
            JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
