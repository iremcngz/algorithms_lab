import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

class Graph {
    int V;
    ArrayList<ArrayList<Integer>> adjListArray;

    // constructor
    Graph(int V) {
        this.V = V;
        adjListArray = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adjListArray.add(i, new ArrayList<>());
        }
    }

    // Adds an edge to an undirected graph
    void addEdge(int src, int dest) {

        adjListArray.get(src).add(dest);
        adjListArray.get(dest).add(src);
    }

    void addDirectedEdge(int v, int w) { adjListArray.get(v).add(w); }

    public List<List<Integer> > allcon=new ArrayList<>();
    List<Integer> connected=new ArrayList<>();
    void DFSUtil(int v, boolean[] visited) {
        visited[v] = true;
        connected.add(v);
        for (int x : adjListArray.get(v)) {
            if (!visited[x])
                DFSUtil(x, visited);
        }
    }

    void connectedComponents() {
        // Mark all the vertices as not visited
        boolean[] visited = new boolean[V];
        for (int v = 0; v < V; ++v) {
            if (!visited[v]) {
                DFSUtil(v, visited);
                ArrayList<Integer> add=new ArrayList<>(connected);
                allcon.add(add);
                connected.removeAll(connected);
            }
        }
    }









    ////////////////part 1
    void topologicalSortUtil(int v, boolean visited[],
                             Stack<Integer> stack)
    {
        visited[v] = true;
        Integer i;

        Iterator<Integer> it = adjListArray.get(v).iterator();
        while (it.hasNext()) {
            i = it.next();
            if (!visited[i])
                topologicalSortUtil(i, visited, stack);
        }

        stack.push(new Integer(v));
    }

    void topologicalSort(ArrayList<Integer> topSortedTask)
    {
        Stack<Integer> stack = new Stack<Integer>();

        boolean visited[] = new boolean[V];
        for (int i = 0; i < V; i++)
            visited[i] = false;
        for (int i = 0; i < V; i++)
            if (visited[i] == false)
                topologicalSortUtil(i, visited, stack);
        while (stack.empty() == false){
            topSortedTask.add(stack.pop());
        }

    }











}