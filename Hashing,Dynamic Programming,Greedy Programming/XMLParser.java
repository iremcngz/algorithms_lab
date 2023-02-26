import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class XMLParser {
    /**
     * TODO: Parse the input XML file and return a dictionary as described in the assignment insturctions
     *
     * @param filename the input XML file
     * @return a dictionary as described in the assignment insturctions
     */
    public static Map<String, Malware> parse(String filename) {
        // TODO: YOUR CODE HERE
        Map<String, Malware> map
                = new HashMap<String, Malware>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try{
           DocumentBuilder builder = factory.newDocumentBuilder();
           Document doc =builder.parse(filename);
           NodeList rowList =doc.getElementsByTagName("row");
           for(int i=0;i<rowList.getLength();i++){
               Node r= rowList.item(i);
               if(r.getNodeType()==Node.ELEMENT_NODE){
                   Element row= (Element) r;
                    NodeList titleList=row.getChildNodes();
                    int count=0;
                    String[] arr=new String[3];
                    for(int j=0;j<titleList.getLength();j++){
                        Node t= titleList.item(j);
                        if(t.getNodeType()== Node.ELEMENT_NODE){
                            Element title=(Element) t;
                            if(title.getTagName()=="title"||title.getTagName()=="hash"||title.getTagName()=="level") {
                                arr[count] = title.getTextContent();
                                count++;
                            }
                        }
                    }
                    Malware mal=new Malware(arr[0],Integer.parseInt(arr[2],16),arr[1]);
                    map.put(arr[1],mal);
               }
           }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return map;
    }
}
