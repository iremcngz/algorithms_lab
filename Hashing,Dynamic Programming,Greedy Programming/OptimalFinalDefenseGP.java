import java.util.ArrayList;
import java.util.Comparator;

/**
 * This class accomplishes Mission Exterminate
 */
public class OptimalFinalDefenseGP
{
    private ArrayList<Integer> bombWeights;

    public OptimalFinalDefenseGP(ArrayList<Integer> bombWeights) {
        this.bombWeights = bombWeights;
    }

    public ArrayList<Integer> getBombWeights() {
        return bombWeights;
    }

    /**
     *
     * @param maxNumberOfAvailableAUAVs the maximum number of available AUAVs to be loaded with bombs
     * @param maxAUAVCapacity the maximum capacity of an AUAV
     * @return the minimum number of AUAVs required using first fit approach over reversely sorted items.
     * Must return -1 if all bombs can't be loaded onto the available AUAVs
     */

    int AuvaNumber=0;
    public int findFirst(int bombKg,ArrayList<Integer> remainingWe,ArrayList visited){

        for(int i=0;i<remainingWe.size();i++) {
            if(remainingWe.get(i)>=bombKg) {
                if(!visited.contains(i)){
                    AuvaNumber++;
                    visited.add(i);
                }
                return i;
            }
        }
        return -1;
    }
    public int getMinNumberOfAUAVsToDeploy(int maxNumberOfAvailableAUAVs, int maxAUAVCapacity)
    {
        ArrayList<Integer> visited=new ArrayList<>();
        // First sort all weights in decreasing order
        // Initialize result (Count of AUAVs)
        // Create an array to store remaining space in AUAVs, there can be at most maxNumberOfAvailableAUAVs AUAVs
        // Place items one by one
        bombWeights.sort(Comparator.reverseOrder());
        int bombSize=bombWeights.size();
        ArrayList<Integer> remainingWe=new ArrayList<>();
        for(int i=0;i<maxNumberOfAvailableAUAVs;i++){
            remainingWe.add(maxAUAVCapacity);
        }
        for(int i=0;i<bombSize;i++){
            int indxOfAU=findFirst(bombWeights.get(0),remainingWe,visited);
             if(indxOfAU<0)
                 return -1;
            remainingWe.set(indxOfAU,remainingWe.get(indxOfAU) - bombWeights.get(0));
            bombWeights.remove(0);

        }
        if(bombWeights.size()>0)
            return -1;
        else return AuvaNumber;


    }
    public void printFinalDefenseOutcome(int maxNumberOfAvailableAUAVs, int AUAV_CAPACITY){
        int minNumberOfAUAVsToDeploy = this.getMinNumberOfAUAVsToDeploy(maxNumberOfAvailableAUAVs, AUAV_CAPACITY);
        if(minNumberOfAUAVsToDeploy!=-1) {
            System.out.println("The minimum number of AUAVs to deploy for complete extermination of the enemy army: " + minNumberOfAUAVsToDeploy);
        }
        else{
            System.out.println("We cannot load all the bombs. We are doomed.");
        }
    }
}
