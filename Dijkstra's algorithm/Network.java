import java.io.*;
import java.io.FileNotFoundException;
import java.io.Serializable;

import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Network implements Serializable {
    static final long serialVersionUID = 55L;
    private List<Router> routers = new ArrayList<>();
    private List<Link> links = new ArrayList<>();

    public static EdgeWeightedGraph.EdgeWeightedG graph;

    public int findRoutersNumFromID(String ID){

        for(int i=0;i<routers.size();i++){
            if(routers.get(i).getIpAddress().equals(ID))
                return i;
        }
        return -1;
    }


    /////////////
    /**
     * The constructor should read the given file and generate necessary Router and Link objects and initialize link
     * and router arrays.
     * Also, you should implement Link class’s calculateAndSetCost() method in order for the costs to be calculated
     * based on the formula given in the instructions.
     *
     * @param filename Input file to generate the network from
     * @throws FileNotFoundException
     */
    public Network(String filename) throws FileNotFoundException {
        // TODO: YOUR CODE HERE
        Pattern regexp = Pattern.compile("[RouterIP].*:(\\d*\\.\\d*\\.(\\d*)\\.\\d*)");
        Pattern regexp2 = Pattern.compile("[Link].*:(\\d*\\.\\d*\\.\\d*\\.\\d*)-(\\d*\\.\\d*\\.\\d*\\.\\d*)" +
                " Bandwidth:(\\d*) Mbps");
        Pattern regexpRo = Pattern.compile("RouterIP");
        Pattern regexpLi = Pattern.compile("Link:");
        Matcher matcher = regexp.matcher("");
        Matcher matcher2 = regexp2.matcher("");
        Matcher matcherRo = regexpRo.matcher("");
        Matcher matcherLi = regexpLi.matcher("");
        Path path = Paths.get(filename);
        try (BufferedReader reader = Files.newBufferedReader(path);
                LineNumberReader lineReader = new LineNumberReader(reader);
        ){
            String line = null;
            while ((line = lineReader.readLine()) != null){
                matcherRo.reset(line);
                matcherLi.reset(line);
                if(matcherRo.find(0)) {
                    //routers
                    matcher.reset(line); //reset the input
                    if (!matcher.find(0)) {
                        continue;
                    }
                    Router router=new Router(matcher.group(1),this);
                    routers.add(router);
                }else if(matcherLi.find(0)){
                    //links
                    matcher2.reset(line); //reset the input
                    if (!matcher2.find(0)) {
                        continue;
                    }
                    Link link=new Link(matcher2.group(1),matcher2.group(2),Integer.parseInt(matcher2.group(3)));
                    links.add(link);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        graph=new EdgeWeightedGraph.EdgeWeightedG(routers.size());
        graph.adjMatrix= graph.UpdateAdjMatrix(0,0);
        for(int i=0;i<links.size();i++){
            EdgeWeightedGraph.Edge edge=new EdgeWeightedGraph.Edge(
                    findRoutersNumFromID(links.get(i).getIpAddress1()),findRoutersNumFromID(links.get(i).getIpAddress2()),
                    links.get(i).getCost());
            graph.addEdge(edge);
        }
        updateAllRoutingTables();

    }



    /**
     * IP address of the router should be placed in group 1
     * Subnet of the router should be placed group 2
     *
     * @return regex for matching RouterIP lines
     */
    public static String routerRegularExpression() {
        // TODO: REGEX HERE

       //return ".*R.*o.*u.*t.*.*e.*r.*I.*P.*:\\D*(\\d{1,3}\\.\\d{1,3}\\.(\\d{1,3})\\.\\d{1,3})";
      //  return ".*R.*o.*u.*t.*.*e.*r.*:\\D*(\\d{1,3}\\.\\d{1,3}\\.(\\d{1,3})\\.\\d{1,3}).*";
       //return ".*R.*o.*u.*t.*.*e.*r.*I.*P.*:\\.*(\\d{1,3}\\.\\d{1,3}\\.(\\d{1,3})\\.\\d{1,3}).*";
        return "[RouterIP].*:*(\\d{3}\\.\\d{1,3}\\.(\\d{1,3})\\.\\d{1,3}).*";

    }

    /**
     * IP address of the router 1 should be placed in group 1
     * IP address of the router 2 should be placed in group 2
     * Bandwidth of the link should be placed in group 3
     *
     * @return regex for matching Link lines
     */
    public static String linkRegularExpression() {
        // TODO: REGEX HERE
       // return "[Link].*:\\D?(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})-(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}) .*:*(\\d{3,4})";
       //return ".*[Link].*:\\D?(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})-(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}) .*:\\D*(\\d*).*";
       //return ".*L.*i.*n.*k.*:\\D?(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})\\D*?-\\D*?(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}) .*?:\\D*?(\\d{3,4}).*";
      // return ".*L.*i.*n.*k.*:\\D*(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})\\D*-\\D*(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}).*:\\D*(\\d{3,4}).*";
       return"[Link].*:\\.*(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})\\D*-\\D*(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}).*:\\D*(\\d{3,4}).*";
    }

    public List<Router> getRouters() {
        return routers;
    }

    public List<Link> getLinks() {
        return links;
    }

    public List<RoutingTable> getRoutingTablesOfAllRouters() {

        if (routers != null) {
            List<RoutingTable> routingTableList = new ArrayList<>();
            for (Router router : routers)
                routingTableList.add(router.getRoutingTable());
            return routingTableList;
        }
        return null;
    }

    public Router getRouterWithIp(String ip) {
        if (routers != null) {
            for (Router router : routers) {
                if (router.getIpAddress().equals(ip))
                    return router;
            }
        }
        return null;
    }

    public Link getLinkBetweenRouters(String ipAddr1, String ipAddr2) {
        if (links != null) {
            for (Link link : links) {

                if (link.getIpAddress1().equals(ipAddr1) && link.getIpAddress2().equals(ipAddr2)
                        || link.getIpAddress1().equals(ipAddr2) && link.getIpAddress2().equals(ipAddr1)) {

                    return link;
                }
            }
        }
        return null;
    }

    public List<Link> getLinksOfRouter(Router router) {
        List<Link> routersLinks = new ArrayList<>();
        if (links != null) {
            for (Link link : links) {
                if (link.getIpAddress1().equals(router.getIpAddress()) ||
                        link.getIpAddress2().equals(router.getIpAddress())) {
                    routersLinks.add(link);
                }
            }
        }
        return routersLinks;
    }

    public void updateAllRoutingTables() {
        System.out.println("\nup**********************************************************************\n");
       // System.out.println(graph.adjMatrix.length+"updateAllRouting içinde");
        for (Router router : getRouters()) {
            router.getRoutingTable().updateTable();
        }
    }







    /**
     * Changes the cost of the link with a new value, and update all routing tables.
     *
     * @param link    Link to update
     * @param newCost New link cost
     */
    public void changeLinkCost(Link link, double newCost) {
       // System.out.println(getRoutingTablesOfAllRouters());
         for(int i=0;i<links.size();i++){
             if(links.get(i).equals(link)){
                 link.setCost(newCost);
             }
         }
         EdgeWeightedGraph.Edge e=graph.findEdgeFromVetrexNum
                 (findRoutersNumFromID(link.getIpAddress1()),
                         findRoutersNumFromID(link.getIpAddress2()));
         Network.graph.changeWeight(
                 graph.findEdgeFromVetrexNum
                         (findRoutersNumFromID(link.getIpAddress1()),
                                 findRoutersNumFromID(link.getIpAddress2())),newCost);
      //   System.out.println(getRoutingTablesOfAllRouters());
        updateAllRoutingTables();
        // TODO: YOUR CODE HERE
    }

    /**
     * Add a new Link to the Network, and update all routing tables.
     *
     * @param link Link to be added
     */
    public void addLink(Link link) {
        links.add(link);
        //System.out.println(link.getCost()+" eklenen link costu//////////////////////////////////////////////////////////////");
        EdgeWeightedGraph.Edge e=new EdgeWeightedGraph.Edge(findRoutersNumFromID(link.getIpAddress1()),
                findRoutersNumFromID(link.getIpAddress2()),link.getCost());
       // System.out.println("adding newEdge");
        graph.addEdge(e);
        //System.out.println("finish adding newEdge");
        updateAllRoutingTables();
        // TODO: YOUR CODE HERE
    }

    /**
     * Remove a Link from the Network, and update all routing tables.
     *
     * @param link Link to be removed
     */
    public void removeLink(Link link) {
        links.remove(link);
        EdgeWeightedGraph.Edge e=new EdgeWeightedGraph.Edge(findRoutersNumFromID(link.getIpAddress1()),
                findRoutersNumFromID(link.getIpAddress2()),link.getCost());
        graph.removeEdge(e);
        updateAllRoutingTables();
        // TODO: YOUR CODE HERE
    }

    /**
     * Add a new Router to the Network, and update all routing tables.
     *
     * @param router Router to be added
     */
    public void addRouter(Router router) {
        // TODO: YOUR CODE HERE
      //  System.out.println("adding router");
        routers.add(router);
       // System.out.println(routers.size()+"addRouter sonrası routerssize ");
        graph.adjMatrix= graph.UpdateAdjMatrix(1,0);
       // System.out.println(graph.adjMatrix.length+"addRouter sonrası adjMatrix ");
        graph.addingToV(1);
       // System.out.println(graph.adjMatrix.length+"addRouter sonrası adjMatrix ");

        graph.adj.add(new ArrayList<EdgeWeightedGraph.Edge>());
       // System.out.println(graph.adjMatrix.length+"addRouter sonrası adjMatrix ");
        updateAllRoutingTables();
    }

    /**
     * Remove a Router from the Network, and update all routing tables. Beware that removing a router also causes the
     * removal of any links connected to it from the Network. Also beware that a router which was removed should not
     * appear in any routing table entry.
     *
     * @param router Router to be removed
     */
    public void removeRouter(Router router) {
        // TODO: YOUR CODE HERE
/*
//        System.out.println(graph.adjMatrix.length+" "+graph.adj.size()+" "+graph.getV()+graph.allEdges.size());
        int routerNum=findRoutersNumFromID(router.getIpAddress());
    //    graph.removeEdgeOfVertex(routerNum);

        for(int i=0;i<graph.allEdges.size();i++){
            if(graph.allEdges.get(i).either()==routerNum || graph.allEdges.get(i).other(graph.allEdges.get(i).either())==routerNum){
                //  System.out.println(allEdges.get(i).w+" "+allEdges.get(i).v);
                Link link=getLinkBetweenRouters(DijkstrasAlgorithm.findRoutersfromNum
                                (graph.allEdges.get(i).other(graph.allEdges.get(i).either()),routers).getIpAddress(),
                        DijkstrasAlgorithm.findRoutersfromNum(graph.allEdges.get(i).either(),routers).getIpAddress());
                links.remove(link);
                EdgeWeightedGraph.Edge e=new EdgeWeightedGraph.Edge(findRoutersNumFromID(link.getIpAddress1()),
                        findRoutersNumFromID(link.getIpAddress2()),link.getCost());
                graph.removeEdge(e);
            }
        }
        routers.remove(router);
        graph.adjMatrix=graph.UpdateAdjMatrix(-1,routerNum);
        graph.addingToV(-1);
        graph.adj.remove(routerNum);
        deleteNum++;
        //System.out.println(graph.adjMatrix.length+" "+graph.adj.size()+" "+graph.getV()+ graph.allEdges.size());
        updateAllRoutingTables();
*/
    }

    /**
     * Change the state of the router (down or live), and update all routing tables. Beware that a router which is down
     * should not be reachable and should not appear in any routing table entry’s path. However, this router might appear
     * in other router’s routing-tables as a separate entry with a totalRouteCost=Infinity value because it was not
     * completely removed from the network.
     *
     * @param router Router to update
     * @param isDown New status of the router
     */
    public void changeStateOfRouter(Router router, boolean isDown) {
        // TODO: YOUR CODE HERE
    }

}
