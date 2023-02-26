import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

public class MissionGroundwork {

    /**
     * Given a list of Project objects, prints the schedule of each of them.
     * Uses getEarliestSchedule() and printSchedule() methods of the current project to print its schedule.
     * @param projectList a list of Project objects
     */
    public void printSchedule(List<Project> projectList) {
        // TODO: YOUR CODE HERE
        for(Project p:projectList){
            p.printSchedule(p.getEarliestSchedule());
        }

    }

    /**
     * TODO: Parse the input XML file and return a list of Project objects
     *
     * @param filename the input XML file
     * @return a list of Project objects
     */
    public List<Project> readXML(String filename) {
        List<Project> projectList = new ArrayList<>();
        // TODO: YOUR CODE HERE
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        int counter=0;
        try{
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc =builder.parse(filename);
            doc.getDocumentElement().normalize();
            String name = null;
            Task tas = null;

            NodeList rowList =doc.getElementsByTagName("Project");
            for(int i=0;i<rowList.getLength();i++){
                Node r= rowList.item(i);
                if(r.getNodeType()==Node.ELEMENT_NODE){
                    Element row= (Element) r;
                    NodeList titleList=row.getChildNodes(); //child of project
                    for(int j=0;j<titleList.getLength();j++){
                        Node t= titleList.item(j);
                        if(t.getNodeType()== Node.ELEMENT_NODE){
                            Element title=(Element) t;

                            if(title.getTagName()=="Tasks") {
                                List<Task> tasks=new ArrayList<>();
                                NodeList s=title.getChildNodes();
                                for(int k=0;k<s.getLength();k++){
                                    Node l=s.item(k);
                                    if(l.getNodeType()==Node.ELEMENT_NODE){
                                        Element tsk=(Element) l;
                                        if(tsk.getTagName()=="Task"){
                                            NodeList o=tsk.getChildNodes();
                                            String[] arr=new String[4];
                                            List<Integer> dep=new ArrayList<>();
                                            int count=0;
                                            for(int u=0;u<o.getLength();u++){
                                                Node p=o.item(u);
                                                if(p.getNodeType()==Node.ELEMENT_NODE){
                                                    Element w=(Element) p;
                                                    if(w.getTagName()=="TaskID"||w.getTagName()=="Description"||
                                                            w.getTagName()=="Duration"){
                                                         arr[count]=w.getTextContent();
                                                         count++;
                                                    }
                                                    if(w.getTagName()=="Dependencies"){
                                                       NodeList depend=w.getChildNodes();
                                                       for(int q=0;q<depend.getLength();q++ ){
                                                           Node dp=depend.item(q);
                                                           if(dp.getNodeType()==Node.ELEMENT_NODE){
                                                               Element d=(Element) dp;
                                                               if(d.getTagName()=="DependsOnTaskID"){
                                                                   dep.add(Integer.parseInt(d.getTextContent()));
                                                               }
                                                           }
                                                       }
                                                    }
                                                }
                                            }
                                            tas=new Task(Integer.parseInt(arr[0]),arr[1],Integer.parseInt(arr[2]),dep);
                                            tasks.add(tas);
                                        }
                                    }
                                }
                                Project p=new Project(name,tasks);
                                projectList.add(p);
                                p.getEarliestSchedule();
                            }
                            if(title.getTagName()=="Name") {
                                name=title.getTextContent();
                            }
                        }
                    }



                }
            }
            //control

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }



        return projectList;
    }


}
