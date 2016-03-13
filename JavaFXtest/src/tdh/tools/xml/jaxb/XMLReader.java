package tdh.tools.xml.jaxb;

import tdh.tools.xml.TDH;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Collections;

public class XMLReader {
	private static TDH tdhData = null;

	public static TDH getTDHData() {
		if(tdhData == null) {
			JAXBContext context;
			
			try {
				context = JAXBContext.newInstance(TDH.class);
				File xmlFile = new File("Util.xml");
				Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
				tdhData = (TDH) jaxbUnmarshaller.unmarshal(xmlFile);
				Collections.sort(tdhData.getFeedList());

			} catch (JAXBException e) {
				e.printStackTrace();
			}
		}
		return tdhData;
	}
	
	private XMLReader() {}
}
