package freemarker;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.ListIterator;

import org.xml.sax.InputSource;

public class EnumaratedInputStream implements Enumeration<InputStream>
{
	private ArrayList<InputStream> sources;
	private ListIterator<InputStream> iterator = null;
	
	public void add(InputStream is)
	{
		sources.add(is);
	}
	public EnumaratedInputStream() {
		sources = new ArrayList<InputStream>();
		// TODO Auto-generated constructor stub
	}


	@Override
	public boolean hasMoreElements() {
		Boolean retVal = false;
		if(iterator == null)
			iterator = sources.listIterator();
		retVal = iterator.hasNext();
		if(retVal == false)
			iterator = null;
		return retVal;
	}

	@Override
	public InputStream nextElement() {
		hasMoreElements();
		if(iterator != null)
			return iterator.next();
		else
			return null;
	}

}
