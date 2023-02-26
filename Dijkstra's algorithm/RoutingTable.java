import java.io.Serializable;
import java.util.*;

public class RoutingTable implements Serializable {

    static final long serialVersionUID = 99L;
    private final Router router;
    private final Network network;
    private final List<RoutingTableEntry> entryList;



   public Network getterNetwork(){return this.network;};
   public Router getterRouter(){
       return this.router;
   }
   public List<RoutingTableEntry> getterEntryList(){
       return this.entryList;
   }
    public RoutingTable(Router router) {
        this.router = router;
        this.network = router.getNetwork();
        this.entryList = new ArrayList<>();
    }

    /**
     * updateTable() should calculate routing information and then instantiate RoutingTableEntry objects, and finally add
     * them to RoutingTable object’s entryList.
     */
    public void updateTable() {
        // TODO: YOUR CODE HERE
       ////////////////////////////////////////////clear ekledim
        entryList.clear();
        DijkstrasAlgorithm.routers= network.getRouters();
        for(int i=0;i< DijkstrasAlgorithm.routers.size();i++){

            if(!router.getIpAddress().equals(DijkstrasAlgorithm.routers.get(i).getIpAddress())) {

             //   System.out.println("/////////////////////////////////////////////////////////////");
                String dest = DijkstrasAlgorithm.routers.get(i).getIpAddress();
  //              System.out.println(Network.graph.adjMatrix.length+"updateTable içindeki adjMatrixsize");
                System.out.println("pathTo çalışacak: " + router + " destolacak: " + DijkstrasAlgorithm.routers.get(i).getIpAddress());
                ////////////////////////////////
                Stack<Link> s = pathTo(DijkstrasAlgorithm.routers.get(i));
                System.out.println("pathto düzgün çalıştı, fullPathsize: "+s.size());
                RoutingTableEntry r = new RoutingTableEntry(router.getIpAddress(), dest,
                        s);
                entryList.add(r);

            }
        }

    }

    /**
     * pathTo(Router destination) should return a Stack<Link> object which contains a stack of Link objects,
     * which represents a valid path from the owner Router to the destination Router.
     *
     * @param destination Destination router
     * @return Stack of links on the path to the destination router
     */
    public Stack<Link> pathTo(Router destination) {
        // TODO: YOUR CODE

        Stack<Router> stackOfRouters=DijkstrasAlgorithm.dijkstra(Network.graph.adjMatrix,router.getIpAddress(),destination).get(network.findRoutersNumFromID(destination.getIpAddress()));
       // System.out.println("ro: "+router.getIpAddress()+" dest: "+ destination.getIpAddress()+ "için link: ");
        //System.out.println(stackOfRouters.size());
        Stack<Link> stack=new Stack<Link>();

        if(router.getIpAddress().equals(destination.getIpAddress())) {
            return stack;
        }
        DijkstrasAlgorithm.reverseStack(stackOfRouters);

        if(stackOfRouters.isEmpty()){
            return stack;
        }

        Router first=stackOfRouters.pop();
        if(stackOfRouters.isEmpty()){
            stack.add(router.getNetwork().getLinkBetweenRouters(first.getIpAddress(),router.getIpAddress()));
        }
        while(!stackOfRouters.isEmpty()){
            Router sec =stackOfRouters.pop();
          //  System.out.println("link varsa stack'e ekle ");
            try {
                router.getNetwork().getLinkBetweenRouters(first.getIpAddress(), sec.getIpAddress()).getIpAddress2();
              //  System.out.println(first.getIpAddress()+"       buldu      "+sec.getIpAddress());
                stack.add(router.getNetwork().getLinkBetweenRouters(first.getIpAddress(), sec.getIpAddress()));
            }catch(NullPointerException n){
               // System.out.println(first.getIpAddress()+"     link yok    "+sec.getIpAddress());
                break;
            }
            first=sec;
        }

        return stack;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoutingTable that = (RoutingTable) o;
        return router.equals(that.getterRouter()) && entryList.equals(that.getterEntryList());
    }
    @Override
    public String toString(){
        System.out.println("\n\n\nRouter: "+router+"\nNetwork: "+network);
        for(int i=0;i<entryList.size();i++){
            System.out.println(entryList.get(i).toString());
        }
        return null;
    }



    public List<RoutingTableEntry> getEntryList() {
        return entryList;
    }
}
