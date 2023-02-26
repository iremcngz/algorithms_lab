import java.util.*;

class EdgeWeightedGraph {

    public static class Edge implements Comparable<Edge> {
        private final int v, w;
        private  double weight;

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
        public boolean isEdgesEqual(Edge e){
            if(weight==e.weight){
                if((v==e.either() && w==e.other(e.either()) )|| w==e.either() && v==e.other(e.either()))
                    return true;
                else
                    return false;
            }
            return false;
        }
        public double getWeight() {
            return weight;
        }
        public void setWeight(double weight){this.weight=weight;}

        public int compareTo(Edge that) {
            if (this.weight < that.weight) return -1;
            else if (this.weight > that.weight) return +1;
            else return 0;
        }
    }

    public static class EdgeWeightedG {
        private  int V;
        public final List<List<Edge>> adj;
        List<Edge> allEdges = new ArrayList<>();
        public double adjMatrix[][];


        public void addingToV(int howMany){
            this.V=V+howMany;
        }
        public int getV(){return this.V;}
        public EdgeWeightedG(int V) {
            this.V = V;
            adj = new ArrayList<>();
            //adjMatrix = new double[V][V];
            for (int v = 0; v < V; v++)
                adj.add(new ArrayList<Edge>());
        }

        public double[][] UpdateAdjMatrix(int increase,int whichWilldelete){
            if(increase==1) {
                double nadjMatrix[][] = new double[V + 1][V + 1];
                for (int i = 0; i < V; i++) {
                    for (int j = 0; j < V; j++) {
                        nadjMatrix[i][j] = adjMatrix[i][j];
                    }
                }
                return nadjMatrix;
            }else if(increase==0){
                return new double[V][V];
            }else{///deleting
                double nadjMatrix[][] = new double[V-1][V-1];
                for (int i = 0; i < whichWilldelete; i++) {
                    for (int j = 0; j < whichWilldelete; j++) {
                        nadjMatrix[i][j] = adjMatrix[i][j];
                    }
                }
                for(int i=whichWilldelete;i<V-1;i++){
                    for(int j=whichWilldelete;j<V-1;j++) {
                        //System.out.println(i+" "+j);
                        nadjMatrix[i][j] = adjMatrix[i + 1][j + 1];
                    }
                }
                return nadjMatrix;
            }
        }

        public void addEdge(Edge e) {
        //    System.out.println(e.getWeight());
            int v = e.either(), w = e.other(v);
            adj.get(v).add(e);
            adj.get(w).add(e);
            adjMatrix[v][w] = e.weight;
            adjMatrix[w][v] = e.weight;
            allEdges.add(e);
        }
        public void removeEdge(Edge e){
            int v = e.either(), w = e.other(v);
            adj.get(v).remove(e);
            adj.get(w).remove(e);
          //
            //
            //System.out.println(v+"   *   "+w+" "+adjMatrix.length);
            adjMatrix[v][w]=0;
            adjMatrix[w][v]=0;
            allEdges.remove(e);

        }



        public void changeWeight(Edge e,double newCost){
            e.setWeight(newCost);
            int v = e.either(), w = e.other(v);
            //System.out.println(adjMatrix[v][w]+" "+adjMatrix[w][v]+" eski");
            adjMatrix[v][w] = newCost;
            adjMatrix[w][v] = newCost;
            //System.out.println(adjMatrix[v][w]+" "+adjMatrix[w][v]+" yeni");

        }

        public Edge findEdgeFromVetrexNum(int ip1Num,int ip2Num ){
            for(int i=0;i<allEdges.size();i++){

                if((allEdges.get(i).either()==ip1Num)&&(allEdges.get(i).other(allEdges.get(i).either())==ip2Num))
                    return allEdges.get(i);
                if((allEdges.get(i).either()==ip2Num)&&(allEdges.get(i).other(allEdges.get(i).either())==ip1Num)){
                    return allEdges.get(i);
                }
            }
            return null;
        }

        public Iterable<Edge> adj(int v) {
            return adj.get(v);
        }


    }



}


