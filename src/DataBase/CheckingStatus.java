package DataBase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import classes.Book;

public class CheckingStatus extends TimerTask
{
	List<Book> books;
	String fileName;
	
	public CheckingStatus(List<Book> books, String fileName)
	{
		this.books = books;
		this.fileName = fileName;
	}
	
	public void run()
	{
		
			try
			{
				compare();
				
			}
			catch(Exception e)
			{
				System.out.println("exception");
			}
		
	}
	
	public void updateChecker(List<Book> books)
	{
		this.books = books;
	}
	
	public void compare()
	{
		List<Book> dataBase = getBooks();
		
		int id;
		String status;
		for(int i =0; i < books.size(); i++)
		{
			id = books.get(i).id;
			if( !existBook(id, dataBase))
			{
				JOptionPane.showMessageDialog(null, "id: " + id + " was deleted", "info", JOptionPane.INFORMATION_MESSAGE);
				//books.remove(i);
				continue;
			}
			else 
			{
				status = books.get(i).status;
				for(int j =0; j<dataBase.size(); j++)
				{
					if( id == dataBase.get(j).id)
					{
						String dataBaseStatus = dataBase.get(j).status;
						if( !status.equals(dataBaseStatus))
						{
							//books.set(i, dataBase.get(j));
							JOptionPane.showMessageDialog(null, "status " + id + " change from \"" + status + "\" to \"" + dataBaseStatus + "\"", "info", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			}
		}
		
		books = dataBase;
	}
	
	public boolean existBook(int id, List<Book> books)
	{
		for(Book book: books)
		{
			if(book.id == id)
			{
				return true;				
			}
		}
		return false;
	}
	
	public List<Book> getBooks()
	{
		List<Book> lista = new ArrayList<Book>();
		try
		{
			File xmlFiel = new File(fileName);
			
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(xmlFiel);
			
			NodeList list = document.getElementsByTagName("Book");
			
			for( int i =0; i<list.getLength(); i++)
			{
				Node node = list.item(i);
				
				if( node.getNodeType() == Node.ELEMENT_NODE)
				{
					Element element = (Element) node;
					lista.add(new Book(Integer.parseInt(element.getAttribute("id")),
								element.getElementsByTagName("title").item(0).getTextContent(),
								element.getElementsByTagName("author").item(0).getTextContent(),
								element.getElementsByTagName("status").item(0).getTextContent()));
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("exception");
		}
		return lista;
	}
}
