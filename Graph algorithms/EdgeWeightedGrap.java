import java.util.*;

class EdgeWeightedGraph {

    public static class Edge implements Comparable<Edge> {
        private final int v, w;
        private final double weight;

        public Edge(int v, int w, double weight) {
            this.v = v;
            this.w = w;
            this.weight = weight;
        }

        public int either() {
            return v;
        }

        public int other(int vertex) {
            if (vertex == v) return w;
            else return v;
        }
        public double getWeight(){
            return weight;
        }

        public int compareTo(Edge that) {
            if (this.weight < that.weight) return -1;
            else if (this.weight > that.weight) return +1;
            else return 0;
        }
    }

    public static class EdgeWeightedG {
        private final int V;
        private final List<List<Edge>> adj;
        List<Edge> allEdges = new ArrayList<>();

        public EdgeWeightedG(int V) {
            this.V = V;
            adj = new ArrayList<>();
            for (int v = 0; v < V; v++)
                adj.add(new ArrayList<Edge>());
        }

        public void addEdge(Edge e) {
            int v = e.either(), w = e.other(v);
            adj.get(v).add(e);
            adj.get(w).add(e);
            allEdges.add(e);
        }

        public Iterable<Edge> adj(int v) {
            return adj.get(v);
        }


    }



    public static class KruskalMST {
        private PriorityQueue<Edge> mst = new PriorityQueue<>();
        class subset {
            int parent, rank;
        }

        int find(subset[] subsets, int i) {
            if (subsets[i].parent != i)
                subsets[i].parent
                        = find(subsets, subsets[i].parent);

            return subsets[i].parent;
        }

        void Union(subset subsets[], int x, int y) {
            int xroot = find(subsets, x);
            int yroot = find(subsets, y);

            if (subsets[xroot].rank
                    < subsets[yroot].rank)
                subsets[xroot].parent = yroot;
            else if (subsets[xroot].rank
                    > subsets[yroot].rank)
                subsets[yroot].parent = xroot;

            else {
                subsets[yroot].parent = xroot;
                subsets[xroot].rank++;
            }
        }
        public KruskalMST(EdgeWeightedG G) {
            PriorityQueue<Edge> pq = new PriorityQueue<Edge>();
            for (Edge e : G.allEdges)
                pq.add(e);

            subset subsets[] = new subset[G.V];
            for (int i = 0; i < G.V; ++i)
                subsets[i] = new subset();
            for (int v = 0; v < G.V; ++v) {
                subsets[v].parent = v;
                subsets[v].rank = 0;
            }
            while (mst.size() < G.V - 1) {
                Edge next_edge = pq.remove();


                int x = find(subsets, next_edge.v);
                int y = find(subsets, next_edge.w);

                if (x != y) {
                    mst.add(next_edge);
                    Union(subsets, x, y);
                }
                // Else discard the next_edge
            }
        }
        public Iterable<Edge> edges() {
            return mst;
        }
    }
}

