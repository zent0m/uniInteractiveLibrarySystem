package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.io.*;

import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;

// import org.w3c.dom.events.MouseEvent;

public class MainWindow extends JFrame implements ActionListener {

    private JMenuBar menuBar;
    private JMenu adminMenu;
    private JMenu booksMenu;
    private JMenu membersMenu;

    private JMenuItem adminExit;

    private JMenuItem booksView;
    private JMenuItem booksAdd;
    private JMenuItem booksDel;	
    private JMenuItem booksIssue;
    private JMenuItem booksRenew;
    private JMenuItem booksReturn;

    private JMenuItem memView;
    private JMenuItem memAdd;
    private JMenuItem memDel;

    private Library library;

    public MainWindow(Library library) {

        initialize();
        this.library = library;
    } 
    
    public Library getLibrary() {
        return library;
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {

        }

        setTitle("Library Management System");

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        //adding adminMenu menu and menu items
        adminMenu = new JMenu("Admin");
        menuBar.add(adminMenu);

        adminExit = new JMenuItem("Exit");
        adminMenu.add(adminExit);
        adminExit.addActionListener(this);

        // adding booksMenu menu and menu items
        booksMenu = new JMenu("Books");
        menuBar.add(booksMenu);

        booksView = new JMenuItem("View");
        booksAdd = new JMenuItem("Add");
        booksDel = new JMenuItem("Delete");
        booksIssue = new JMenuItem("Issue");
        booksRenew = new JMenuItem("Renew");
        booksReturn = new JMenuItem("Return");
        booksMenu.add(booksView);
        booksMenu.add(booksAdd);
        booksMenu.add(booksDel);
        booksMenu.add(booksIssue);
        booksMenu.add(booksRenew);
        booksMenu.add(booksReturn);
        for (int i = 0; i < booksMenu.getItemCount(); i++) {
            booksMenu.getItem(i).addActionListener(this);
        }

        // adding membersMenu menu and menu items
        membersMenu = new JMenu("Members");
        menuBar.add(membersMenu);

        memView = new JMenuItem("View");
        memAdd = new JMenuItem("Add");
        memDel = new JMenuItem("Delete");

        membersMenu.add(memView);
        membersMenu.add(memAdd);
        membersMenu.add(memDel);

        memView.addActionListener(this);
        memAdd.addActionListener(this);
        memDel.addActionListener(this);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    LibraryData.store(library);
                    System.exit(0);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }   
            }
        });

        setSize(800, 500);

        setVisible(true);
        setAutoRequestFocus(true);
        toFront();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        /* Uncomment the following line to not terminate the console app when the window is closed */
        // setDefaultCloseOperation(DISPOSE_ON_CLOSE);        

    }	

/* Uncomment the following code to run the GUI version directly from the IDE */
//    public static void main(String[] args) throws IOException, LibraryException {
//        Library library = LibraryData.load();
//        new MainWindow(library);			
//    }



    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == adminExit) {
            System.exit(0);
        } else if (ae.getSource() == booksView) {
            displayBooks();  
        } else if (ae.getSource() == booksAdd) {
            new AddBookWindow(this);
        } else if (ae.getSource() == booksDel) {
            new DeleteBookWindow(this);
        } else if (ae.getSource() == booksIssue) {
            new BorrowBookWindow(this);
        } else if (ae.getSource() == booksRenew) {
            new RenewBookWindow(this);
        }else if (ae.getSource() == booksReturn) {
            new ReturnBookWindow(this);
        } else if (ae.getSource() == memView) {
            displayMembers();     
        } else if (ae.getSource() == memAdd) {
            new AddPatronWindow(this);     
        } else if (ae.getSource() == memDel) {
            new DeletePatronWindow(this);  
        }
    }

    public void displayBooks(){
        List<Book> booksList = library.getBooks();
        // headers for the table
        String[] columns = new String[]{"ID", "Title", "Author", "Pub Date", "Publisher", "Status"};

        Object[][] data = new Object[library.getBooksSize(booksList)][6];
        int i = 0, j = 0;

        while (j < library.getBooksSize(booksList)) {
            Book book = booksList.get(i);
            if (!book.isHidden()) {
                data[j][0] = book.getId();
                data[j][1] = book.getTitle();
                data[j][2] = book.getAuthor();
                data[j][3] = book.getPublicationYear();
                data[j][4] = book.getPublisher();
                data[j][5] = book.getStatus();

                i += 1; j += 1;
            }  else {i += 1;} 
        }

        JTable table = new JTable(data, columns);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                if (event.getClickCount() == 1) {
                    JTable target = (JTable) event.getSource();
                    int row = target.getSelectedRow();
                    try {
                        Book book = library.getBookByID((Integer)data[row][0]);
                        if (!(book.getLoan() == null)) {
                            String message = "\nThis book is currently on loan to:\n" + book.getLoan().getPatron().getDetails() + "\n\nThe due date of the loan is: " + book.getDueDate();
                            JOptionPane.showMessageDialog(null, message);
                        }
                    } catch (LibraryException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();
    }	

    public void displayMembers() {
        List<Patron> patronList = library.getPatrons();
        // headers for the table
        String[] columns = new String[]{"ID", "Name", "Phone", "Email", "No. Of Books Loaned"};

        Object[][] data = new Object[library.getPatronsSize(patronList)][6];
        int i = 0, j = 0;

        while (j < library.getPatronsSize(patronList)) {
            Patron patron = patronList.get(i);
            if (!patron.isHidden()) {
                data[j][0] = patron.getId();
                data[j][1] = patron.getName();
                data[j][2] = patron.getPhone();
                data[j][3] = patron.getEmail();
                data[j][4] = patron.getBooks().size();
                

                i += 1; j += 1;
            } else {i += 1;}  
        }

        JTable table = new JTable(data, columns);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                if (event.getClickCount() == 1) {
                    JTable target = (JTable) event.getSource();
                    int row = target.getSelectedRow();
                    try {
                        List<Book> books = library.getPatronByID((Integer)data[row][0]).getBooks();
                        if (books.size() > 0) {
                            String message = "\nThe patron currently has "+ books.size()+" book(s) loaned out. \nDetails for the book(s):\n\n";
                            for (Book book : books) {
                            message += book.getDetailsShort() + ".\nDue Date for return: " + book.getDueDate() + "\n\n";
                            }
                            JOptionPane.showMessageDialog(null, message);
                        }
                    } catch (LibraryException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
            
        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();
    }	
}
