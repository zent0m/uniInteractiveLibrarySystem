package bcu.cmp5332.librarysystem.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import bcu.cmp5332.librarysystem.commands.AddBook;
import bcu.cmp5332.librarysystem.commands.AddPatron;
import bcu.cmp5332.librarysystem.model.Book;

import java.io.IOException;
import java.time.LocalDate;

import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Patron;

public class Tests {

	public Tests() throws LibraryException, IOException {;}

	Library library = LibraryData.load();

	Book bookControl = new Book(library.getBooks().get(library.getBooks().size()-1).getId(),"Title","Author","2000","Publisher");	
	Patron patronControl = new Patron(library.getPatrons().size(),"Name","07123123123","test@bcu.ac.uk");

// Test Book constructor
	@Test
	public void testBookConstructor()  {
		assertTrue(bookControl instanceof Book);
		assertEquals(library.getBooks().get(library.getBooks().size()-1).getId(),bookControl.getId());
		assertEquals("Title",bookControl.getTitle());
		assertEquals("Author",bookControl.getAuthor());
		assertEquals("2000",bookControl.getPublicationYear());
		assertEquals("Publisher",bookControl.getPublisher());

	}

// Test Publisher Setter and Getter	
	@Test
	public void testBookSetPublisher() {
		bookControl.setPublisher("PublisherSet");
		assertEquals("PublisherSet",bookControl.getPublisher());
	}

// Test AddBook Command	
	@Test
	public void testAddBook() throws LibraryException, IOException {
		AddBook process = new AddBook("Title", "Author", "2000", "Publisher");
        process.execute(library, LocalDate.now());

		Book bookTest = library.getBookByID(library.getBooks().get(library.getBooks().size()-1).getId());

		bookControl.setId(bookControl.getId() + 1);

		assertEquals(bookControl.getDetailsLong(), bookTest.getDetailsLong());
	}
		
// Test Patron constructor
	@Test
	public void testPatronConstructor()  {
		assertTrue(patronControl instanceof Patron);
		assertEquals(library.getPatrons().get(library.getPatrons().size()-1).getId(),patronControl.getId());
		assertEquals("Name",patronControl.getName());
		assertEquals("07123123123",patronControl.getPhone());
		assertEquals("test@bcu.ac.uk",patronControl.getEmail());
	}

// Test Email Setter and Getter	
	@Test
	public void testPatronSetPublisher() {
		patronControl.setEmail​("testSet@bcu.ac.uk");
		assertEquals("testSet@bcu.ac.uk",patronControl.getEmail());
	}

// Test AddPatron Command	
	@Test
	public void testPatronBook() throws LibraryException, IOException {
		AddPatron process = new AddPatron("Name", "07123123123", "test@bcu.ac.uk");
        process.execute(library, LocalDate.now());

		Patron patronTest = library.getPatronByID(library.getPatrons().size());

		patronControl.setId​(patronControl.getId() + 1);

		assertEquals(patronControl.getDetails(), patronTest.getDetails());
	}
	
}
