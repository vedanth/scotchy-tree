package resttest;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import resttest.TestInterface.FirstFault;
import resttest.TestInterface.SecondFault;

@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
@ApplicationPath("/rest")
@Path("/tests")
public interface TestLocalInterface {
	@GET
	@Path("testGet")
	public @WebResult(name="testClass",targetNamespace="http://www.treetechnologies.net/pc/entities") Test testGet() throws FirstFault,SecondFault;
	
	@GET
	@Path("testGetInjected")
	public @WebResult(name="testClass",targetNamespace="http://www.treetechnologies.net/pc/entities") Test testGetInjected() throws FirstFault,SecondFault;

	@PUT
	@Path("testPut")
	public @WebResult(name="testClass",targetNamespace="http://www.treetechnologies.net/pc/entities") Test testPut(@WebParam(name="testClass",targetNamespace="http://www.treetechnologies.net/pc/entities") Test test) throws FirstFault,SecondFault;

}
