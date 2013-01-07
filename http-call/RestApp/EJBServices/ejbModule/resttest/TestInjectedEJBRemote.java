package resttest;

import resttest.TestInterface.FirstFault;
import resttest.TestInterface.SecondFault;

public interface TestInjectedEJBRemote {
	public Test testGet() throws FirstFault,SecondFault;
}
