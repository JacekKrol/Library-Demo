package classes;

public class Book {
	public String title;
	public String author;
	public int id;
	public String status;
	
	public Book()
	{
		this.title = "empty";
		this.author = "empty";
		this.id = 0;
		this.status = "empty";
	}
	
	public Book( int id, String title, String author, String status)
	{
		this.id = id;
		this.title = title;
		this.author = author;
		this.status = status;
	}
}
