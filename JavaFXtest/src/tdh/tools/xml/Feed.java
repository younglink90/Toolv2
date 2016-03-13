package tdh.tools.xml;

import tdh.tools.xml.jaxb.XMLReader;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@XmlAccessorType(XmlAccessType.NONE)
public class Feed implements Comparable<Feed> {

	@XmlAttribute
	private String name;
	@XmlElement(name = "queue")
	private List<Queue> queueList;
	@XmlElement(name = "command")
	private List<Command> commandList;
	@XmlElement(name = "query")
	private List<Query> queryList;
	@XmlElement
	private Topic topic;
	private boolean areQueriesResolved;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {

		System.out.println("Yes");

		this.name = name;
	}

	public List<Queue> getQueueList() {
		if(this.queueList != null) {
			return queueList;
		} else {
			return Arrays.asList(new Queue[] {new Queue("Queue not found")});
		}
		
	}

	public void setQueueList(List<Queue> queueList) {
		this.queueList = queueList;
	}

	public List<Command> getCommandList() {
		return commandList;
	}

	public void setCommandList(List<Command> commandList) {
		this.commandList = commandList;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public Feed() {
		this.areQueriesResolved = false;
	}

	public Feed(String name) {
		super();
		this.name = name;
	}

	public List<Query> getQueryList() {
		return queryList;
	}

	public void setQueryList(List<Query> queryList) {
		this.queryList = queryList;
	}

	public boolean areQueriesResolved() {
		return areQueriesResolved;
	}

	public void setAreQueriesResolved(boolean areQueriesResolved) {
		this.areQueriesResolved = areQueriesResolved;
	}

	public void resolveQueries(Environment environment) {
		int run = 0;
		Runtime r = Runtime.getRuntime();
		String command = Utilities.RESOURCES_PLINK + XMLReader.getTDHData().getUtilities().getServerAddress() + " cat "
				+ environment.getQueryFile();

		try {
			Process p = r.exec(command);

			InputStream std = p.getInputStream();
			boolean keepWaitingForResponse = true;
			String message = "";

			while (keepWaitingForResponse) {
				Thread.sleep(100);
				if (std.available() > 0) {
					while (std.available() > 0) {
						message += (char) std.read();
					}
					keepWaitingForResponse = false;
				}
			}

			for (Query query : getQueryList()) {
				for (String s : message.split("\n")) {
					if (s.contains(query.toString())) {
						query.setQuery(s.substring(query.getQuery().length() + 1, s.length()));
						break;
					}
				}
			}

			setAreQueriesResolved(true);
		} catch (IOException | InterruptedException e) {
			Logger.getLogger(FclDatabase.class.getName()).log(Level.SEVERE, null, e);
			setAreQueriesResolved(false);
		}
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public int compareTo(Feed o) {
		return getName().compareTo(o.getName());
	}
}
