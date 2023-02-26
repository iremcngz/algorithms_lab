import java.io.Serializable;
import java.util.Stack;

public class RoutingTableEntry implements Serializable {
    static final long serialVersionUID = 88L;
    private String destinationIpAddr;
    private String nextRouterIpAddr;
    private String sourceIpAddr;
    private Stack<Link> fullPath;
    private double totalRouteCost;

    public RoutingTableEntry(String sourceIpAddr, String destinationIpAddr, Stack<Link> fullPath) {
        if (fullPath != null) {

            this.destinationIpAddr = destinationIpAddr;
            this.sourceIpAddr = sourceIpAddr;
            this.fullPath = fullPath;
            this.totalRouteCost = calculateTotalRouteCost(fullPath);
            this.nextRouterIpAddr = !fullPath.isEmpty() ? fullPath.peek().getOtherIpAddress(sourceIpAddr) : null;
        }
    }
    String getDestinationIpAddr(){
        return destinationIpAddr;
    }
    String getNextRouterIpAddr(){
        return nextRouterIpAddr;
    }
    String getSourceIpAddr(){
        return sourceIpAddr;
    }
    Stack<Link> getFullPath(){
        return fullPath;
    }
    double getTotalRouteCost(){
        return totalRouteCost;
    }


    double calculateTotalRouteCost(Stack<Link> fullPath) {
        if (!fullPath.isEmpty()) {

            double cost = 0f;

            for (Link link : fullPath) {
              //   System.out.println(link.getIpAddress1()+" "+link.getIpAddress2());
                cost += link.getCost();
            }

           // System.out.println(cost);
            return cost;
        }else {
            return Double.POSITIVE_INFINITY;
        }
    }

    @Override
    public String toString() {
        return "RoutingTableEntry{" +
                "destinationIpAddr='" + destinationIpAddr + '\'' +
                ", nextRouterIpAddr='" + nextRouterIpAddr + '\'' +
                ", totalRouteCost=" + totalRouteCost +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoutingTableEntry entry = (RoutingTableEntry) o;
        boolean sameCosts = ((getTotalRouteCost() == Double.POSITIVE_INFINITY && entry.getTotalRouteCost() == Double.POSITIVE_INFINITY)
                || (Math.abs(entry.getTotalRouteCost() - getTotalRouteCost()) <= 0.0001));
        return sameCosts && getDestinationIpAddr().equals(entry.getDestinationIpAddr()) && getSourceIpAddr().equals(entry.getSourceIpAddr());
    }

}
