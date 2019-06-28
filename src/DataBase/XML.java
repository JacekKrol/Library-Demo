package DataBase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import classes.Book;
import intefrace.BookInterface;

import org.w3c.dom.Attr;

public class XML implements BookInterface{
	
	String fileName = "data.xml";
	CheckingStatus checker;
	public XML()
	{
		Timer time = new Timer();
		checker = new CheckingStatus(getBooks(), fileName);
		time.schedule(checker, 0, 4000);
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
	
	public Book getBookById(int id) 
	{
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
					
					if(Integer.parseInt(element.getAttribute("id")) == id)
					{
						return new Book(id,
								element.getElementsByTagName("title").item(0).getTextContent(),
								element.getElementsByTagName("author").item(0).getTextContent(),
								element.getElementsByTagName("status").item(0).getTextContent());
					}
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("exception");
		}
		return null;
	}
	
	public void addNewBook(Book book) 
	{
		try
		{
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			
			Document document = documentBuilder.parse(fileName);
			Element root= document.getDocumentElement();
			Element element = document.createElement("Book");
			
			Attr attr = document.createAttribute("id");
			attr.setValue(String.valueOf(book.id));
			element.setAttributeNode(attr);
			
			Element title = document.createElement("title");
			title.appendChild(document.createTextNode(book.title));
			element.appendChild(title);
			
			Element author = document.createElement("author");
			author.appendChild(document.createTextNode(book.author));
			element.appendChild(author);
			
			Element status = document.createElement("status");
			status.appendChild(document.createTextNode(book.status));
			element.appendChild(status);
			
			root.appendChild(element);
			
			TransformerFactory transformFactory = TransformerFactory.newInstance();
			Transformer transformer = transformFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			
			StreamResult streamResult = new StreamResult(new File(fileName));
			
			transformer.transform(source, streamResult);
		}
		catch(Exception e)
		{
			System.out.println("excpetion");
		}
		
		checker.updateChecker(getBooks());
	}
	
	public void deleteBook(int id)
	{
		List<Book> lista = getBooks();
		
		for(int i = 0; i < lista.size(); i++)
		{
			if(lista.get(i).id == id)
			{
				lista.remove(i);
				break;
			}
		}
		
		try
		{
			create();
		}
		catch(Exception e)
		{
			System.out.println("exception");
		}
		
		for(Book book: lista)
		{
			addNewBook(book);
		}
		checker.updateChecker(getBooks());
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
	
	public void create() 
	{
		try
		{
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			
			Document document = documentBuilder.newDocument();
			Element element = document.createElement("Books");
			
			document.appendChild(element);
			
			TransformerFactory transformFactory = TransformerFactory.newInstance();
			Transformer transformer = transformFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			
			StreamResult streamResult = new StreamResult(new File(fileName));
			
			transformer.transform(source, streamResult);
		}
		catch(Exception e)
		{
			System.out.println("Transform exception");
		}
		
		checker.updateChecker(getBooks());
	}
	
	
	
	public boolean existBookId(int id)
	{
		if( getBookById(id) == null)
			return false;
		else 
			return true;
	}
	
	public void updateBook(int id, Book book)
	{
		List<Book> lista = getBooks();
		
		for(int i = 0; i < lista.size(); i++)
		{
			if(lista.get(i).id == id)
			{
				lista.set(i, book);
				break;
			}
		}
		
		try
		{
			create();
		}
		catch(Exception e)
		{
			System.out.println("exception");
		}
		
		for(Book b: lista)
		{
			addNewBook(b);
		}
		checker.updateChecker(getBooks());
	}
}
