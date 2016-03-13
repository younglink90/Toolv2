package tdh.tools.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.NONE)
public class Queue {

	@XmlAttribute
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Queue() {
	}

	public Queue(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return getAddress();
	}
	
	@Override
	public boolean equals(Object o) {		
		if(o instanceof Queue) {
			Queue receivedQueue = (Queue) o;
			return this.getAddress().equals(receivedQueue.getAddress());
		}
		
		return false;
	}
}
