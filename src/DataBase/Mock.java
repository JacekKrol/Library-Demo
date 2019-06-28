package DataBase;

import java.util.ArrayList;
import java.util.List;

import classes.Book;
import intefrace.BookInterface;

public class Mock implements BookInterface{
	
	List<Book> list = new ArrayList<Book>();
	
	public Mock()
	{
		addNewBook(new Book(56, "title", "Author12", "loan"));
		addNewBook(new Book(123, "new_title", "Luis", "in stock"));
		addNewBook(new Book(44, "title_44", "Mike", "loan"));
	}
	
	public List<Book> getBooks()
	{
		return list;
	}
	
	public Book getBookById(int id)
	{
		for(Book book: list)
		{
			if(book.id == id)
			{
				return book;				
			}
		}
		return null;
	}
	
	public void addNewBook(Book book)
	{
		this.list.add(book);
	}
	
	public void deleteBook(int id)
	{
		for(int i =0; i< list.size(); i++)
		{
			if(list.get(i).id == id)
			{
				list.remove(i);
				break;
			}
		}
	}
	
	public String checkBookStatus(int id)
	{
		Book book = getBookById(id);
		if(book == null)
		{
			return new String("brak id");		
		}
		else
			return book.status;
			
	}
	
	public boolean existBookId(int id)
	{
		if(getBookById(id) == null)
		{
			return false;				
		}
		else 
			return true;
	}
	
	public void updateBook(int id, Book book)
	{
		if( existBookId(id))
		{
			for(int i =0; i< list.size(); i++)
			{
				if(list.get(i).id == id)
				{
					list.set(i, book);
					break;
				}
			}
		}
	}

}
