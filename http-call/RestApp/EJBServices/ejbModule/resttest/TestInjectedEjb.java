package resttest;

import java.io.Serializable;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import resttest.TestInterface.FirstFault;
import resttest.TestInterface.SecondFault;

/**
 * Session Bean implementation class TestInjectedEjb
 */
@Stateless
@Remote(TestInjectedEJBRemote.class)
@Local(TestInjectedEJBLocal.class)
public class TestInjectedEjb implements Serializable{

    /**
     * Default constructor. 
     */
    public TestInjectedEjb() {
        // TODO Auto-generated constructor stub
    }
    
	public Test testGet() throws FirstFault,SecondFault
	{
		System.out.println("IN TESTGETINJECTED");
		Test test = new Test();
		test.data = new String("Injected");
		//throw new TestInterface.FirstFault("First Fault");
		return test;
	}

}
