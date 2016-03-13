package tdh.tools.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.NONE)
public class Utilities {

	@XmlAttribute(name="queue_suffix")
	private String queueSuffix;
	@XmlAttribute(name="qcf_suffix")
	private String qcfSuffix;
        @XmlAttribute(name="topic_suffix")
	private String topicSuffix;
	@XmlAttribute(name="tcf_suffix")
	private String tcfSuffix;
        @XmlAttribute(name="server_address")
        private String serverAddress;
        @XmlAttribute(name="slices_query")
        private String slicesQuery;
	@XmlElement(name="LDAP")
	private List<LDAP> ldapList;
	@XmlElement(name="environment")
        private List<Environment> environmentList;
        public static final String RESOURCES_PLINK ="plink.exe -i key.ppk ";
	 
        
	public String getQueueSuffix() {
		return queueSuffix;
	}
	public void setQueueSuffix(String queueSuffix) {
		this.queueSuffix = queueSuffix;
	}
	public String getQcfSuffix() {
		return qcfSuffix;
	}
	public void setQcfSuffix(String qcfSuffix) {
		this.qcfSuffix = qcfSuffix;
	}
	public List<LDAP> getLdapList() {
		return ldapList;
	}
	public void setLdapList(List<LDAP> ldapList) {
		this.ldapList = ldapList;
	}

    public List<Environment> getEnvironmentList() {
        return environmentList;
    }

    public void setEnvironmentList(List<Environment> environmentList) {
        this.environmentList = environmentList;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getSlicesQuery() {
        return slicesQuery;
    }

    public void setSlicesQuery(String slicesQuery) {
        this.slicesQuery = slicesQuery;
    }

    public String getTopicSuffix() {
        return topicSuffix;
    }

    public void setTopicSuffix(String topicSuffix) {
        this.topicSuffix = topicSuffix;
    }

    public String getTcfSuffix() {
        return tcfSuffix;
    }

    public void setTcfSuffix(String tcfSuffix) {
        this.tcfSuffix = tcfSuffix;
    }

    
}
