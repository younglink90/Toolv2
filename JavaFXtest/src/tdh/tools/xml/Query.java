/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tdh.tools.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * @author araqjor
 */
@XmlAccessorType(XmlAccessType.NONE)
public class Query {
    @XmlAttribute(name="name")
    private String queryName;
    @XmlAttribute
    private String query;

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
        
    @Override
    public String toString() {
        return this.query;
    }
}
