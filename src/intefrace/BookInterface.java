package intefrace;

import java.util.List;

import classes.Book;

public interface BookInterface {
	
	public List<Book> getBooks();
	public Book getBookById(int id);
	public void addNewBook(Book book);
	public void deleteBook(int id);
	public String checkBookStatus(int id);
	public boolean existBookId(int id);
	public void updateBook(int id, Book book);
	
}
