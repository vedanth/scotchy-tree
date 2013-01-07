package resttest;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.interceptor.Interceptor;
import javax.interceptor.Interceptors;
import javax.jws.HandlerChain;
import javax.jws.WebService;

import org.apache.cxf.annotations.EndpointProperties;
import org.apache.cxf.annotations.EndpointProperty;
import org.apache.cxf.interceptor.InInterceptors;

import resttest.TestInterface.FirstFault;
import resttest.TestInterface.SecondFault;


@Stateless
@Remote(TestInterface.class)
@Local(TestLocalInterface.class)
@WebService(endpointInterface="resttest.TestInterface",wsdlLocation="META-INF/TestService.wsdl")
@DeclareRoles({"test","other"})
@InInterceptors
	(
	interceptors = 	{
	      			"org.jboss.wsf.stack.cxf.security.authentication.SubjectCreatingPolicyInterceptor",
	      			"org.apache.cxf.interceptor.security.SecureAnnotationsInterceptor"
					}
	)
@EndpointProperties
	(value =
		@EndpointProperty(key="ws-security.validate.token",value="false")
	)
public class TestService //extends Application
{
	@EJB
	private TestInjectedEJBRemote injected;
	
	@RolesAllowed({"test","code"})
	public Test testGet() throws FirstFault,SecondFault
	{
		//System.out.println("IN TEST");
		Test test = new Test();
		//test.data = new String("Dinu");
		throw new TestInterface.SecondFault("First Fault");
		//return test;
	}
	
	@RolesAllowed({"code"})
	public Test testGetInjected() throws FirstFault,SecondFault
	{
		return injected.testGet();
	}
	
	@RolesAllowed({"test"})
	public Test testPut(Test test) throws FirstFault,SecondFault
	{
		System.out.println("IN TESTPUT");
		return test;
	}
	
	public void test()
	{}
}

