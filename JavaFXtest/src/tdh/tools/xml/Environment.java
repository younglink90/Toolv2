package tdh.tools.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.NONE)
public class Environment {

        @XmlAttribute
        private String name;
        @XmlAttribute
        private String location;
        @XmlAttribute(name = "env_properties")
        private String environmentProperties;
        @XmlAttribute(name = "query_file")
        private String queryFile;
        @XmlElement(name = "script")
        private List<Script> scriptList;
        @XmlElement(name = "fcl_database")
        private FclDatabase fclDatabase;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public List<Script> getScriptList() {
            return scriptList;
        }

        public void setScriptList(List<Script> scriptList) {
            this.scriptList = scriptList;
        }

        public FclDatabase getFclDatabase() {
            return fclDatabase;
        }

        public void setFclDatabase(FclDatabase fclDatabase) {
            this.fclDatabase = fclDatabase;
        }

        public String getEnvironmentProperties() {
            return environmentProperties;
        }

        public void setEnvironmentProperties(String environmentProperties) {
            this.environmentProperties = environmentProperties;
        }
        
        public void mostrar(){
            System.out.println(this.getFclDatabase().getUsername());
        }

        public String getQueryFile() {
            return queryFile;
        }

        public void setQueryFile(String queryFile) {
            this.queryFile = queryFile;
        }
        
                        
        @Override
        public String toString() {
            return getName();
        }
}
