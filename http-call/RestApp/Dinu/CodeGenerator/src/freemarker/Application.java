package freemarker;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.SequenceInputStream;
import java.io.StringReader;
import java.io.Writer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.stream.FileImageInputStream;
import javax.sound.midi.Sequence;
import javax.xml.parsers.DocumentBuilderFactory;

import org.xml.sax.InputSource;

import freemarker.ext.dom.NodeModel;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
			Application app = new Application();
			/*
			String templateFolder = "C:\\Users\\user\\workspace\\CodeGenerator\\src\\META-INF\\templates";
			String mappingFolder = "C:\\Users\\user\\workspace\\CodeGenerator\\src\\META-INF";
			String sourceFolder = "C:\\Users\\user\\workspace\\CodeGenerator\\src";
			String template = "C:\\Users\\user\\workspace\\CodeGenerator\\src\\META-INF\\templates\\orm.ftl";
			*/
			String template = args[0];
			String templateFolder = args[1];
			String mappingFolder = args[2];
			String sourceFolder = args[3];
			//app.getInputStreamForMappings(mappingFolder);
			app.process(template,templateFolder,mappingFolder,sourceFolder);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private SequenceInputStream getInputStreamForMappings(String directory)
	{
		try
		{
			//String beginTag = new String("<mapping-files>");
			String beginTag = new String("<mapping-files version=\"2.0\" " + 
									    "xmlns=\"http://treetechnologies.net/xml/ns/persistence/orm\" " + 
									    "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " + 
									    "xsi:schemaLocation=\"http://treetechnologies.net/xml/ns/persistence/orm http://treetechnologies.net/xml/ns/persistence/orm \">"
									    );
			String endTag = new String("</mapping-files>");
			//System.out.println(beginTag);
			
			InputStream begin = new ByteArrayInputStream(beginTag.getBytes());
			InputStream end = new ByteArrayInputStream(endTag.getBytes());
			EnumaratedInputStream isArray = new EnumaratedInputStream();
			isArray.add(begin);
			File sourceDirectory = new File(directory);
			for(File f:sourceDirectory.listFiles
					(
						new FilenameFilter() 
						{
							@Override
							public boolean accept(File dir, String name) 
							{
								return (name.matches(".*.xml"));
							}
						}
					)
				)
			{
				//System.out.println("Adding File "+f.getAbsolutePath());
				isArray.add(new FileInputStream(f));
			}
			isArray.add(end);
			SequenceInputStream is = new SequenceInputStream(isArray); 
//			int c = 0;
//			while((c = is.read()) > -1)
//				System.out.print((char)c);
			return is;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	private void process(String template,String templateFolder, String mappingFolder, String outputFolder) throws Exception
	{
        /* ------------------------------------------------------------------- */    
        /* You should do this ONLY ONCE in the whole application life-cycle:   */    
    
        /* Create and adjust the configuration */
        Configuration cfg = new Configuration();
        cfg.setWhitespaceStripping(true);
        cfg.setDirectoryForTemplateLoading(new File(templateFolder));
        //cfg.setDirectoryForTemplateLoading(new File("C:\\Users\\user\\workspace\\CodeGenerator\\src\\META-INF\\templates"));
        cfg.setObjectWrapper(new DefaultObjectWrapper());

        /* ------------------------------------------------------------------- */    
        /* You usually do these for many times in the application life-cycle:  */ 

        Template temp = cfg.getTemplate(template);

        /* Create a data-model */
        Map root = new HashMap();
        DocumentBuilderFactory fact = freemarker.ext.dom.NodeModel.getDocumentBuilderFactory();
        
        //fact.setIgnoringElementContentWhitespace(true);
        //fact.setValidating(true);
        
        //freemarker.ext.dom.NodeModel.setDocumentBuilderFactory(fact);
        freemarker.ext.dom.NodeModel.useJaxenXPathSupport();
        NodeModel model = NodeModel.parse(new InputSource(getInputStreamForMappings(mappingFolder)));
        //NodeModel model = freemarker.ext.dom.NodeModel.parse(new File("C:\\Users\\user\\workspace\\CodeGenerator\\src\\META-INF\\orm.xml"));
        root.put("doc", model);
        /*
        root.put("user", "Big Joe");
        Map latest = new HashMap();
        root.put("latestProduct", latest);
        latest.put("url", "products/greenmouse.html");
        latest.put("name", "green mouse");
		*/
        /* Merge data-model with template */
        File tempfile = File.createTempFile("code-gen", "");
        //System.out.println(tempfile.getAbsolutePath());
        FileWriter out = new FileWriter(tempfile);
        //Writer out = new OutputStreamWriter(System.out);
        temp.process(root, out);
        out.flush();
        out.close();
        BufferedReader reader = new BufferedReader(new FileReader(tempfile));
        //FileWriter dest = null;
        BufferedWriter dest = null;
        String line = null;
        while ((line = reader.readLine()) != null)
        {
        	if(line.startsWith("FILE"))
        	{
        		if(dest != null)
        			dest.close();
        		File f = new File(outputFolder + "/" + line.substring(5));
        		f.getParentFile().mkdirs();
        		dest = new BufferedWriter(new FileWriter(f));
        	}
        	else
	        	if(dest != null)
	        		dest.write(line + "\n");
        }
        dest.close();
        reader.close();
        tempfile.delete();
	}

}
