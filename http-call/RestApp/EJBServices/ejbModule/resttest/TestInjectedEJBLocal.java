package resttest;

import resttest.TestInterface.FirstFault;
import resttest.TestInterface.SecondFault;

public interface TestInjectedEJBLocal {
	public Test testGet() throws FirstFault,SecondFault;

}
