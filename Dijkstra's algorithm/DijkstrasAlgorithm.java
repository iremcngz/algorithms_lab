import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class DijkstrasAlgorithm {
    public static List<Router> routers = new ArrayList<>();
    private static final int NO_PARENT = -1;
    public static Stack<Link> stack = new Stack<>();
    public static List<Stack<Router>> routerStack = new ArrayList<>();

    public static Router findRoutersfromNum(int num, List<Router> routers) {
        for (int i = 0; i < routers.size(); i++) {
            if (i == num)
                return routers.get(i);
        }
        return null;
    }

    public static <T> void reverseStack(Stack<T> stack) {
        if (stack.isEmpty()) {
            return;
        }
        T bottom = popBottom(stack);
        reverseStack(stack);
        stack.push(bottom);
    }

    private static <T> T popBottom(Stack<T> stack) {
        T top = stack.pop();
        if (stack.isEmpty()) {
            return top;
        } else {
            T bottom = popBottom(stack);
            stack.push(top);
            return bottom;
        }
    }



    public static List<Stack<Router>> dijkstra(double[][] adjacencyMatrix,
                                               String sourceID, Router destination) {

        int count = -1;

        int nVertices = adjacencyMatrix.length;
        //System.out.println(adjacencyMatrix.length+"*********");
        int startVertex = 0;
        DijkstrasAlgorithm.routerStack.clear();
      //  System.out.println(routers.size()+"++++++++++++");
        for (int i = 0; i < routers.size(); i++) {
            DijkstrasAlgorithm.routerStack.add(new Stack<>());
            if (routers.get(i).getIpAddress().equals(sourceID)) {
                startVertex = i;
            }
        }

        double[] shortestDistances = new double[nVertices];
        boolean[] added = new boolean[nVertices];
        for (int vertexIndex = 0; vertexIndex < nVertices;
             vertexIndex++){
            shortestDistances[vertexIndex] = Integer.MAX_VALUE;
            added[vertexIndex] = false;
        }
        shortestDistances[startVertex] = 0;
        int[] parents = new int[nVertices];
        parents[startVertex] = -1;
        for (int i = 1; i < nVertices; i++) {
            int nearestVertex = -1;
            double shortestDistance = Integer.MAX_VALUE;
            for (int vertexIndex = 0;
                 vertexIndex < nVertices;
                 vertexIndex++) {

                if (!added[vertexIndex] &&
                        shortestDistances[vertexIndex] <
                                shortestDistance) {

                    nearestVertex = vertexIndex;
                    shortestDistance = shortestDistances[vertexIndex];
                }
            }

            if(nearestVertex==-1){
                return routerStack;
            }
            added[nearestVertex] = true;
            for (int vertexIndex = 0;
                 vertexIndex < nVertices;
                 vertexIndex++) {
                // vertexIndex=findRoutersNumfromID(sourceID);
                double edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex];

                if (edgeDistance > 0
                        && ((shortestDistance + edgeDistance) <
                        shortestDistances[vertexIndex])) {
                    parents[vertexIndex] = nearestVertex;
                    shortestDistances[vertexIndex] = shortestDistance +
                            edgeDistance;
                }
            }


        }
        printSolution(startVertex, shortestDistances, parents, routers, destination, count);
        return DijkstrasAlgorithm.routerStack;
    }

    // A utility function to print
    // the constructed distances
    // array and shortest paths
    private static void printSolution(int startVertex,
                                      double[] distances,
                                      int[] parents, List<Router> routers, Router destination, int count) {

        int nVertices = distances.length;
        // System.out.print("Vertex\t Distance\tPath");
        int vertexIndex;
        for (vertexIndex = 0;
             vertexIndex < nVertices;
             vertexIndex++) {
            count = vertexIndex;
            printPath(vertexIndex, parents, routers, destination, count);
        }
    }

    private static void printPath(int currentVertex,
                                  int[] parents, List<Router> routers, Router destination, int count) {
        if (currentVertex == -1) {
            return;
        }
        DijkstrasAlgorithm.routerStack.get(count).add(DijkstrasAlgorithm.findRoutersfromNum(currentVertex, routers));
        // System.out.println("\n---------------"+count+" "+DijkstrasAlgorithm.routerStack.get(count));

        printPath(parents[currentVertex], parents, routers, destination, count);
    }
}