import java.util.*;

public class Galaxy {

    private final List<Planet> planets;
    private List<SolarSystem> solarSystems;

    public Galaxy(List<Planet> planets) {
        this.planets = planets;
    }

    /**
     * Using the galaxy's list of Planet objects, explores all the solar systems in the galaxy.
     * Saves the result to the solarSystems instance variable and returns a shallow copy of it.
     *
     * @return List of SolarSystem objects.
     */
    public List<SolarSystem> exploreSolarSystems() {
        solarSystems = new ArrayList<>();
        Graph planetsGraph=new Graph(planets.size());
        // TODO: YOUR CODE HERE

        //////planetid ile index eşleştirmesi
        HashMap<String,Integer> indexOfVertex=new HashMap<>();
        for(int i=0;i<planets.size();i++){
            indexOfVertex.put(planets.get(i).getId(),i);
        }
        //find planet from id
        HashMap<Integer,Planet> indexOfPlanet=new HashMap<>();
        for(int i=0;i<planets.size();i++){
            indexOfPlanet.put(i,planets.get(i));
        }

        for(int i=0;i<planets.size();i++) {
            for (int j = 0; j < planets.get(i).getNeighbors().size(); j++) {
                planetsGraph.addEdge(indexOfVertex.get(planets.get(i).getId()),
                        indexOfVertex.get(planets.get(i).getNeighbors().get(j)));
            }
        }

        planetsGraph.connectedComponents();

        for(int i=0;i<planetsGraph.allcon.size();i++){
            SolarSystem sol=new SolarSystem();
            for(int j=0;j<planetsGraph.allcon.get(i).size();j++) {
                sol.addPlanet(indexOfPlanet.get(planetsGraph.allcon.get(i).get(j)));
            }
            solarSystems.add(sol);
        }
        return new ArrayList<>(solarSystems);
    }


    public List<SolarSystem> getSolarSystems() {
        return solarSystems;
    }

    // FOR TESTING
    public List<Planet> getPlanets() {
        return planets;
    }

    // FOR TESTING
    public int getSolarSystemIndexByPlanetID(String planetId) {
        for (int i = 0; i < solarSystems.size(); i++) {
            SolarSystem solarSystem = solarSystems.get(i);
            if (solarSystem.hasPlanet(planetId)) {
                return i;
            }
        }
        return -1;
    }

    public void printSolarSystems(List<SolarSystem> solarSystems) {
        System.out.printf("%d solar systems have been discovered.%n", solarSystems.size());
        for (int i = 0; i < solarSystems.size(); i++) {
            SolarSystem solarSystem = solarSystems.get(i);
            List<Planet> planets = new ArrayList<>(solarSystem.getPlanets());
            Collections.sort(planets);
            System.out.printf("Planets in Solar System %d: %s", i + 1, planets);
            System.out.println();
        }
    }
}
