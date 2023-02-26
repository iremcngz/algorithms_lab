import java.math.BigInteger;
import java.util.*;
import java.math.*;
public class SubspaceCommunicationNetwork implements Constants{

    private List<SolarSystem> solarSystems;

    /**
     * Perform initializations regarding your implementation if necessary
     * @param solarSystems a list of SolarSystem objects
     */
    public SubspaceCommunicationNetwork(List<SolarSystem> solarSystems) {
        // TODO: YOUR CODE HERE
        this.solarSystems = solarSystems;

    }

    /**
     * Using the solar systems of the network, generates a list of HyperChannel objects that constitute the minimum cost communication network.
     * @return A list HyperChannel objects that constitute the minimum cost communication network.
     */

    public List<HyperChannel> getMinimumCostCommunicationNetwork() {
        List<HyperChannel> minimumCostCommunicationNetwork = new ArrayList<>();
        // TODO: YOUR CODE HERE
        List<Planet> maxOfSystem=new ArrayList<>();

        for(int i=0;i<solarSystems.size();i++){
            Planet willUse=solarSystems.get(i).getPlanets().get(0);
            int max=solarSystems.get(i).getPlanets().get(0).getTechnologyLevel();
            for(int j=1;j<solarSystems.get(i).getPlanets().size();j++){
                if(solarSystems.get(i).getPlanets().get(j).getTechnologyLevel()>max){
                    max=solarSystems.get(i).getPlanets().get(j).getTechnologyLevel();
                    willUse=solarSystems.get(i).getPlanets().get(j);
                }
            }
            maxOfSystem.add(willUse);
        }

        HashMap<Integer, Planet> fındPlanetFromIndex=new HashMap<>();
        for(int i=0;i<maxOfSystem.size();i++){
            fındPlanetFromIndex.put(i,maxOfSystem.get(i));
        }
        HashMap<String, Planet> fındPlanetFromID=new HashMap<>();
        for(int i=0;i<maxOfSystem.size();i++){
            fındPlanetFromID.put(maxOfSystem.get(i).getId(),maxOfSystem.get(i));
        }


        EdgeWeightedGraph.EdgeWeightedG g=new EdgeWeightedGraph.EdgeWeightedG(maxOfSystem.size());



        ///////////////////////////////////////////////////////////////////////////////////////////77
        for(int i=0;i<maxOfSystem.size();i++){
            for(int j=0;j<maxOfSystem.size();j++) {
                if(i!=j) {
                    double avrg = (maxOfSystem.get(i).getTechnologyLevel() + maxOfSystem.get(j).getTechnologyLevel()) / (double) 2;
                    double cost = SUBSPACE_COMMUNICATION_CONSTANT / avrg;
                    EdgeWeightedGraph.Edge e=new EdgeWeightedGraph.Edge(i,j,cost);
                    g.addEdge(e);
                }
            }
        }





        EdgeWeightedGraph.KruskalMST findMst=new EdgeWeightedGraph.KruskalMST(g);
        Iterable<EdgeWeightedGraph.Edge> mst= findMst.edges();
        for(EdgeWeightedGraph.Edge e: mst){

            Planet from=fındPlanetFromIndex.get(e.either());
            Planet to=fındPlanetFromIndex.get(e.other(e.either()));
            double cost=e.getWeight();
            HyperChannel hc=new HyperChannel(to,from,cost);
            minimumCostCommunicationNetwork.add(hc);
        }
        return minimumCostCommunicationNetwork;
    }


    public void printMinimumCostCommunicationNetwork(List<HyperChannel> network) {
        double sum = 0;
        for (HyperChannel channel : network) {

            Planet[] planets = {channel.getFrom(), channel.getTo()};
            Arrays.sort(planets);

            System.out.printf("Hyperchannel between %s - %s with cost %f", planets[0], planets[1], channel.getWeight());
            System.out.println();
            sum += channel.getWeight();
        }
        System.out.printf("The total cost of the subspace communication network is %f.", sum);
        System.out.println();
    }
}
