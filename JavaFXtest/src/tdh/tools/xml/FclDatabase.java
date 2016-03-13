package tdh.tools.xml;

import tdh.tools.xml.jaxb.XMLReader;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author araqjor
 */
@XmlAccessorType(XmlAccessType.NONE)
public class FclDatabase {
    
    @XmlAttribute
    private String address;
    @XmlAttribute
    private String username;
    @XmlAttribute
    private String password;
    
    private Map<String, String> slices;
    private Boolean isResolved = false;
    private static final String SLICE_ID_COLUMN = "SLICE_ID";
    private static final String DESIRED_COB_COLUMN = "DESIRED_COB_DATE";
    private static final String DESIRED_SLICE = ":#v_slice_id";
    private static final String DESIRED_COB = ":#v_desired_cob_date";

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, String> getSlices() {
        return slices;
    }

    public void setSlices(Map<String, String> slices) {
        this.slices = slices;
    }

    public Boolean isResolved() {
        return isResolved;
    }

    public void setIsResolved(Boolean isResolved) {
        this.isResolved = isResolved;
    }
        
    public void resolve(Environment environment) {
        int run = 0;
        Runtime r = Runtime.getRuntime();
        String command = Utilities.RESOURCES_PLINK + XMLReader.getTDHData().getUtilities().getServerAddress() + " cat " + environment.getEnvironmentProperties();
        
        try { 
            Process p = r.exec(command);
            
            InputStream std = p.getInputStream();
            boolean keepWaitingForResponse = true;
            String message = "";
            
            while(keepWaitingForResponse) {
                Thread.sleep(100);
                if (std.available() > 0) {        	
                    while (std.available() > 0) {
                        message += (char) std.read();
                    }
                    keepWaitingForResponse = false;
                }
            }
            
            Connection con = environment.getEnvironmentProperties().contains(".conf") ? 
                    getDatabaseConnectionFromNewProperties(message) : getDatabaseConnectionFromOldProperties(message);
                        
            Statement statement = con.createStatement();

            ResultSet rs = statement.executeQuery(XMLReader.getTDHData().getUtilities().getSlicesQuery());
            
            Map<String, String> slicesMap = new LinkedHashMap<>();
            
            while(rs.next()) {
                String cob = rs.getString(DESIRED_COB_COLUMN).split(" ")[0];
                slicesMap.put(rs.getString(SLICE_ID_COLUMN), cob);
            }

            setSlices(slicesMap);
            
            setIsResolved(true);
        } catch(IOException | InterruptedException | SQLException e) {
            Logger.getLogger(FclDatabase.class.getName()).log(Level.SEVERE, null, e);
        }                 
    }
    
    public Connection getDatabaseConnectionFromNewProperties(String properties) {
        String propertyBlock = getPropertyBlock(properties);
        
        String addressTemp = address.substring(address.lastIndexOf(".") + 1, address.length());
        String usernameTemp = username.substring(username.lastIndexOf(".") + 1, username.length());
        
        for(String s : propertyBlock.split("\n")) {
            s = s.replace("\t", "");
            if(s.contains(addressTemp + "=") && s.indexOf(addressTemp + "=") == 0) address = s.substring(addressTemp.length() + 1 ).replace("\"", "");
            if(s.contains(usernameTemp + "=") && s.indexOf(usernameTemp + "=") == 0) username = s.substring(usernameTemp.length() + 1 ).replace("\"", "");
        }
        
        return null;//OracleJDBC.getGenericConnection(address, username, password);
    }
    
    public String getPropertyBlock(String properties) {
        int indexOfLastDot = address.lastIndexOf(".");
        String propertyBlockName = address.substring(0, indexOfLastDot);
        int ocurrenceOfPropertyBlock = properties.indexOf(propertyBlockName);
        int ocurrenceOfEndPropertyBlock = properties.substring(ocurrenceOfPropertyBlock, properties.length()).indexOf("}");
        return properties.substring(ocurrenceOfPropertyBlock, ocurrenceOfPropertyBlock + ocurrenceOfEndPropertyBlock);
    }
    
    public Connection getDatabaseConnectionFromOldProperties(String properties) {
        properties = properties.trim();
        address = "tch." + address;
        username = "tch." + username;
        
        for(String s : properties.split("\n")) {
                s = s.replace("\t", "");
                if(s.contains(address) && s.indexOf(address + "=") == 0) setAddress(s.substring(address.length() + 1, s.length()));
                else if(s.contains(username) && s.indexOf(username + "=") == 0) setUsername(s.substring(username.length() + 1, s.length()));
                else if(s.contains(password)) setPassword(s.substring(password.length() + 1, s.length()));
            }
        
        return null;//OracleJDBC.getGenericConnection(address, username, password);
    }
    
    public String writeTopicMessage(String slice, String cob) {
        File f = new File("TopicMessage.txt");
        String fileCreated = "";
        
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String topicMessage = "";
            String lineRead;
            
            while((lineRead = br.readLine()) != null) {
                topicMessage += lineRead + System.lineSeparator();
            }
            
            topicMessage = FclDatabase.replaceSLICE(topicMessage, slice);
            topicMessage = FclDatabase.replaceCOB(topicMessage, cob);
            
            fileCreated = "message_ready_to_send" + new Date().getTime() + ".txt";
            
            try (FileWriter fw = new FileWriter(fileCreated)) {
                fw.write(topicMessage);
            }
        } catch (IOException ex) {
            Logger.getLogger(FclDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return fileCreated;
    }
    
    public static String replaceCOB(String stringToReplace, String COB) {
        return stringToReplace.replace(DESIRED_COB, COB);
    }
    
    public static String replaceSLICE(String stringToReplace, String slice) {
        return stringToReplace.replace(DESIRED_SLICE, slice);
    }
}
