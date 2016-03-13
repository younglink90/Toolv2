package tdh.tools.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.NONE)
public class Command {
    
    @XmlAttribute
	private int order;
    @XmlAttribute
	private String name;
	@XmlAttribute
	private String parameter;
    @XmlAttribute(name="script_needed")
    private String scriptNeeded;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getScriptNeeded() {
        return scriptNeeded;
    }

    public void setScriptNeeded(String scriptNeeded) {
        this.scriptNeeded = scriptNeeded;
    }
        
    @Override
    public String toString() {
        return this.name;
    }
}
