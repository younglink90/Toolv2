package tdh.tools.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement(name="tdh", namespace="http://www.example.org/TDH")
@XmlAccessorType(XmlAccessType.NONE)
public class TDH {

	@XmlElement(name="feed")
	private List<Feed> feedList;
	@XmlElement(name="utilities")
	private Utilities utilities;
	
	public List<Feed> getFeedList() {
		return feedList;
	}
	public void setFeedList(List<Feed> feedList) {
		this.feedList = feedList;
	}
	public Utilities getUtilities() {
		return utilities;
	}
	public void setUtilities(Utilities utilities) {
		this.utilities = utilities;
	}
}
