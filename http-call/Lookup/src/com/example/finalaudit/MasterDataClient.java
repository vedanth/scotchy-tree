package com.example.finalaudit;

import java.util.HashMap;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;

import net.treetechnologies.pc.services.interfaces.MasterDataManagement;

public class MasterDataClient 
{
	
	public static MasterDataManagement lookup(HashMap<String, String> contextData) 
	{
	
		MasterDataManagement masterDataManagement = null;
	    try
	    {
	
	    	Hashtable<String, Object> p = new Hashtable<String, Object>();
	    	//p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
	    	p.put(Context.PROVIDER_URL, "remote://localhost:4547/");
	    	p.put("jboss.naming.client.ejb.context", true);
	    	p.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
	    	p.put("jboss.naming.client.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT", "false");
	    	p.put(InitialContext.SECURITY_PRINCIPAL, contextData.get("username"));
	    	p.put(InitialContext.SECURITY_CREDENTIALS, contextData.get("password"));
	    	
	    	 
	    	final Context context = new InitialContext(p);
	    	masterDataManagement =  (MasterDataManagement) context.lookup("ejb:pcservices/EntityService/MasterDataManagementImpl!net.treetechnologies.pc.services.interfaces.MasterDataManagement");
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	    return masterDataManagement;
	}


}
