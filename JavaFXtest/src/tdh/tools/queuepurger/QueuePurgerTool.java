package tdh.tools.queuepurger;

import com.db.treasury.tdh.QueuePurger;

import tdh.core.Main;
import tdh.tools.xml.Address;
import tdh.tools.xml.Feed;
import tdh.tools.xml.LDAP;
import tdh.tools.xml.Queue;
import tdh.tools.xml.jaxb.XMLReader;

import javax.jms.JMSException;
import javax.naming.NameNotFoundException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QueuePurgerTool  {
	
	private static String[] parse(LDAP ldap, Queue queue, Address address) {
            String queueSuffix = XMLReader.getTDHData().getUtilities().getQueueSuffix();
            String qcfSuffix = XMLReader.getTDHData().getUtilities().getQcfSuffix();
            
            String[] commandToReturn = new String[] {
              ldap.getName(),
              address.getUrl() + queue.getAddress() + queueSuffix,
              address.getUrl() + queue.getAddress() + qcfSuffix,
              address.getUser(),
              address.getPassword()
            };
            
            return commandToReturn;
	}
        
        public static String run(LDAP ldap, Feed feed, Queue queue, Address address) {
        	if(queue.equals(Main.ALL_QUEUES)) {
        		return purgeAllQueues(ldap, feed, address);
        	}
        	
        	String[] dataToSend = parse(ldap, queue, address);
        	
            ByteArrayOutputStream pipeOut = new ByteArrayOutputStream();
            String returnValue = "";

            // Store the current System.out
            PrintStream old_out = System.out;

            // Replace redirect output to our stream
            System.setOut(new PrintStream(pipeOut));
            
            try {
		QueuePurger.main(dataToSend);
            } catch (NameNotFoundException e) {
                System.out.println("ERROR: Your Util.xml contains non-existing queues");
            } catch (JMSException ex) {
                Logger.getLogger(QueuePurgerTool.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(QueuePurgerTool.class.getName()).log(Level.SEVERE, null, ex);
            } 
            // Revert back to the old System.out
            System.setOut(old_out);
            
            // Write the output to a handy string
            String output = new String(pipeOut.toByteArray());
            
            String[] outputFiltered = output.split(System.lineSeparator());
            
            for(int i = 1; i < outputFiltered.length; i++) {
            	returnValue += outputFiltered[i] + System.lineSeparator();
            }
            
            return returnValue;
       }
        
       private static String purgeAllQueues(LDAP ldap, Feed feed, Address address) {
           Map<Queue, String> returnValue = new HashMap<>();
           
           for(Queue queue : feed.getQueueList()) {
               returnValue.put(queue, QueuePurgerTool.run(ldap, feed, queue, address));
           }
           
           return QueuePurgerTool.stringParser(returnValue);
       }
       
       private static String stringParser(Map<Queue, String> stringToParse) {
           String stringToReturn = "";
           
           for(Entry<Queue, String> s1 : stringToParse.entrySet()) {
               int queuesPurged = 0;
           
               for(String s2 : s1.getValue().split(System.lineSeparator())) {
                   if(s2.contains("Removing"))  queuesPurged++;
               }
               
               if(queuesPurged > 0)
               stringToReturn += "Messages removed from [" + s1.getKey().toString() + "]: " + queuesPurged + System.lineSeparator();
           }
           
           if(stringToReturn.isEmpty()) stringToReturn = "No messages found, 0 queues purged";
           
           return stringToReturn;
       }
}
