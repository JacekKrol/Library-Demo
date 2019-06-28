package Factory;

import DataBase.Mock;
import DataBase.XML;
import intefrace.BookInterface;

public class DatabaseFactory {
	
	public BookInterface getDatabase(String database)
	{
		if(database.equalsIgnoreCase("xml"))
			return new XML();
		else if(database.equalsIgnoreCase("mock"))
			return new Mock();
		else	
			return null;
	}
}
