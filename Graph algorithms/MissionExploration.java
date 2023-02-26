import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MissionExploration {

    /**
     * Given a Galaxy object, prints the solar systems within that galaxy.
     * Uses exploreSolarSystems() and printSolarSystems() methods of the Galaxy object.
     *
     * @param galaxy a Galaxy object
     */
    public void printSolarSystems(Galaxy galaxy) {
        // TODO: YOUR CODE HERE
            galaxy.printSolarSystems(galaxy.exploreSolarSystems());

    }

    /**
     * TODO: Parse the input XML file and return a list of Planet objects.
     *
     * @param filename the input XML file
     * @return a list of Planet objects
     */
    public Galaxy readXML(String filename) {
        List<Planet> planetList = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try{
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc =builder.parse(filename);
            NodeList rowList =doc.getElementsByTagName("Planet");

            for(int i=0;i<rowList.getLength();i++){
                Node r= rowList.item(i);
                ArrayList<String> neighbors=new ArrayList<>();
                if(r.getNodeType()==Node.ELEMENT_NODE){
                    Element row= (Element) r;
                    NodeList titleList=row.getChildNodes();
                    int count=0;
                    String[] arr=new String[3];
                    for(int j=0;j<titleList.getLength();j++){
                        Node t= titleList.item(j);
                        if(t.getNodeType()== Node.ELEMENT_NODE){
                            Element title=(Element) t;
                            if(title.getTagName()=="ID"||title.getTagName()=="TechnologyLevel") {
                                arr[count] = title.getTextContent();
                                count++;
                            }
                            if(title.getTagName()=="Neighbors"){
                                NodeList neigh=title.getChildNodes();
                                for(int q=0;q<neigh.getLength();q++ ){
                                    Node dp=neigh.item(q);
                                    if(dp.getNodeType()==Node.ELEMENT_NODE){
                                        Element d=(Element) dp;
                                        if(d.getTagName()=="PlanetID"){
                                            neighbors.add(d.getTextContent());
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Planet planet=new Planet(arr[0],Integer.parseInt(arr[1]),neighbors);
                    planetList.add(planet);
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        Galaxy galaxy=new Galaxy(planetList);
        galaxy.exploreSolarSystems();


        return new Galaxy(planetList);
    }
}
