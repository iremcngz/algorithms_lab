
import java.util.ArrayList;

/**
 * This class accomplishes Mission Nuke'm
 */
public class DefenseAgainstEnemyTroops {
    private ArrayList<Integer> numberOfEnemiesArrivingPerHour;

    public DefenseAgainstEnemyTroops(ArrayList<Integer> numberOfEnemiesArrivingPerHour){
        this.numberOfEnemiesArrivingPerHour = numberOfEnemiesArrivingPerHour;
    }

    public ArrayList<Integer> getNumberOfEnemiesArrivingPerHour() {
        return numberOfEnemiesArrivingPerHour;
    }

    private int getRechargedWeaponPower(int hoursCharging){
        return hoursCharging*hoursCharging;
    }

    //helper
    public int E(int numberOfSoldierAtSpecificHaur){
        return numberOfEnemiesArrivingPerHour.get(numberOfSoldierAtSpecificHaur-1);
    }
    public int P(int hour){
        return (hour*hour);
    }
    public int min(int soldier,int shot){
        if(shot<=soldier)
            return shot;
        else
            return soldier;
    }

    /**
     *     Function to implement the given dynamic programming algorithm
     *     SOL(0) <- 0
     *     HOURS(0) <- [ ]
     *     For{j <- 1...N}
     *         SOL(j) <- max_{0<=i<j} [ (SOL(i) + min[ E(j), P(j − i) ] ]
     *         HOURS(j) <- [HOURS(i), j]
     *     EndFor
     *
     * @return OptimalEnemyDefenseSolution
     */
    public OptimalEnemyDefenseSolution getOptimalDefenseSolutionDP(){
        // TODO: YOUR CODE HERE
        int maxNumberOfKilledEnemies=0;
        //sol(i) i. saatte vurulabilecek max asker
        //maxNumberOfKilledEnemies ise sol içindeki en büyük sayı

        ArrayList<Integer> sol=new ArrayList<>();
        ArrayList<ArrayList<Integer>> allHo=new ArrayList<>();
        ArrayList<Integer> zero=new ArrayList<>();
        zero.add(0);
        allHo.add(zero);
        sol.add(0);
        for(int j=1;j<numberOfEnemiesArrivingPerHour.size()+1;j++){
            int maxV=0;
            int maxH=0;
            ArrayList<Integer> hours=new ArrayList<>();
            for(int i=0;i<j;i++){
               if((sol.get(i)+min(E(j),P(j-i)))>maxV){
                   maxV=sol.get(i)+min(E(j),P(j-i));
                   maxH=i;
               }
            }
            sol.add(maxV);
            hours.add(j);
            for(int i=0;i<allHo.get(maxH).size();i++){
                hours.add(allHo.get(maxH).get(i));
            }
            allHo.add(hours);
        }

        for(int i=0;i<sol.size();i++) {
            if (sol.get(i)>maxNumberOfKilledEnemies){
                maxNumberOfKilledEnemies=sol.get(i) ;
            }
        }
        ArrayList<Integer> fHours=new ArrayList<>();
        for(int m=allHo.get(numberOfEnemiesArrivingPerHour.size()).size()-1;m>=0;m--){
            if(allHo.get(numberOfEnemiesArrivingPerHour.size()).get(m)!=0)
                fHours.add(allHo.get(numberOfEnemiesArrivingPerHour.size()).get(m));
        }
        OptimalEnemyDefenseSolution op=new OptimalEnemyDefenseSolution(maxNumberOfKilledEnemies, fHours);
        return op;
    }
}
