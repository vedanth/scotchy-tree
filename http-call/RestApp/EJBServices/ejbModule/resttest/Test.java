package resttest;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="testClass",namespace="http://www.treetechnologies.net/pc/entities")
@XmlType(name="testType",namespace="http://www.treetechnologies.net/pc/entities")
public class Test implements Serializable
{
	@XmlElement(name="data")
	public String data;
	
}