package resttest;

import java.io.Serializable;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.ws.WebFault;



@WebService(targetNamespace="http://www.treetechnologies.net/pc/services")
@SOAPBinding(style=Style.DOCUMENT,use=Use.LITERAL,parameterStyle=ParameterStyle.WRAPPED)
public interface TestInterface{
	@Provider
	public class FaultMapper implements ExceptionMapper<Exception>
	{

		@Override
		public Response toResponse(Exception arg0) {
			
			Response resp = Response.status(javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR).build();
			return resp;
		}
		
	}
	
	@XmlRootElement(name="FaultInfo")
	public static class FaultInfo implements Serializable
	{
		protected String code;
		
		protected String message;
		
		public String getCode()
		{
			return code;
		}
		
		public void setCode(String code)
		{
			this.code = code;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}
	
	@WebFault(name="Fault",messageName="FirstFault")
	public class FirstFault extends Exception
	{
	    private FaultInfo faultInfo;
	    
	    public FirstFault(String message, FaultInfo faultInfo) 
	    {
	    	super("Application Error. Please see FaultInfo");
	    	this.faultInfo = faultInfo;
	    	this.faultInfo.setCode(this.getClass().getCanonicalName());
	    	this.faultInfo.setMessage(message);
	    }
	    public FirstFault(String message, FaultInfo faultInfo, Throwable cause) 
	    {
	    	super("Application Error. Please see FaultInfo",cause);
	    	this.faultInfo = faultInfo;
	    	this.faultInfo.setCode(this.getClass().getCanonicalName());
	    	this.faultInfo.setMessage(message);
	    }
	    
	    FirstFault()
	    {
	    	super();
	    	this.faultInfo = this.constructFaultinfo("Unspecified Application Error. Please contact system admin");
	    }
	    FirstFault(String message)
	    {
	    	super("Application Error. Please see FaultInfo");
	    	this.faultInfo = this.constructFaultinfo(message);
	    }
	    
	    FirstFault(String message,Throwable cause)
	    {
	    	super("Application Error. Please see FaultInfo",cause);
	    	this.faultInfo = this.constructFaultinfo(message);
	    }
	    
	    FirstFault(Throwable cause)
	    {
	    	super("Application Error. Please see FaultInfo");
	    	this.faultInfo = this.constructFaultinfo(cause.getMessage());
	    }
	    
	    public FaultInfo getFaultInfo() 
	    {
	    	return faultInfo;
	    }
	    
	    private FaultInfo constructFaultinfo(String message)
	    {
	    	FaultInfo f = new FaultInfo();
	    	f.setCode(this.getClass().getCanonicalName());
	    	f.setMessage(message);
	    	return f;
	    }
	}
	
	@WebFault(name="Fault",messageName="FirstFault")
	public class SecondFault extends Exception
	{
	    private FaultInfo faultInfo;
	    
	    public SecondFault(String message, FaultInfo faultInfo) 
	    {
	    	super("Application Error. Please see FaultInfo");
	    	this.faultInfo = faultInfo;
	    	this.faultInfo.setCode(this.getClass().getCanonicalName());
	    	this.faultInfo.setMessage(message);
	    }
	    public SecondFault(String message, FaultInfo faultInfo, Throwable cause) 
	    {
	    	super("Application Error. Please see FaultInfo",cause);
	    	this.faultInfo = faultInfo;
	    	this.faultInfo.setCode(this.getClass().getCanonicalName());
	    	this.faultInfo.setMessage(message);
	    }
	    
	    SecondFault()
	    {
	    	super();
	    	this.faultInfo = this.constructFaultinfo("Unspecified Application Error. Please contact system admin");
	    }
	    SecondFault(String message)
	    {
	    	super("Application Error. Please see FaultInfo");
	    	this.faultInfo = this.constructFaultinfo(message);
	    }
	    
	    SecondFault(String message,Throwable cause)
	    {
	    	super("Application Error. Please see FaultInfo",cause);
	    	this.faultInfo = this.constructFaultinfo(message);
	    }
	    
	    SecondFault(Throwable cause)
	    {
	    	super("Application Error. Please see FaultInfo");
	    	this.faultInfo = this.constructFaultinfo(cause.getMessage());
	    }
	    
	    public FaultInfo getFaultInfo() 
	    {
	    	return faultInfo;
	    }
	    
	    private FaultInfo constructFaultinfo(String message)
	    {
	    	FaultInfo f = new FaultInfo();
	    	f.setCode(this.getClass().getCanonicalName());
	    	f.setMessage(message);
	    	return f;
	    }
	}

	
	public @WebResult(name="testClass",targetNamespace="http://www.treetechnologies.net/pc/entities") Test testGet() throws FirstFault,SecondFault;

	public @WebResult(name="testClass",targetNamespace="http://www.treetechnologies.net/pc/entities") Test testGetInjected() throws FirstFault,SecondFault;
	
	public @WebResult(name="testClass",targetNamespace="http://www.treetechnologies.net/pc/entities") Test testPut(@WebParam(name="testClass",targetNamespace="http://www.treetechnologies.net/pc/entities") Test test) throws FirstFault,SecondFault;
	
}
