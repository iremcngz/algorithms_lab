import javax.swing.*;
import java.util.*;

public class Project {
    private final String name;
    private final List<Task> tasks;

    public static int maxCost;


    public Project(String name, List<Task> tasks) {
        this.name = name;
        this.tasks = tasks;
    }

    /**
     * Schedule all tasks within this project such that they will be completed as early as possible.
     *
     * @return An integer array consisting of the earliest start days for each task.
     */
    public int[] getEarliestSchedule() {
        // TODO: YOUR CODE HERE
        Graph taskGrap=new Graph(tasks.size());

        ArrayList<Integer> topSortedTask=new ArrayList();
        for(int i=0;i< tasks.size();i++){
            for(int j=0;j<tasks.get(i).getDependencies().size();j++){
                taskGrap.addDirectedEdge(tasks.get(i).getDependencies().get(j),tasks.get(i).getTaskID());
            }
        }
        taskGrap.topologicalSort(topSortedTask);
        int[] durations=new int[topSortedTask.size()];
        int[] startTime=new int[topSortedTask.size()];
        ArrayList<Integer> completed=new ArrayList<>();
        findTaskByID(topSortedTask.get(0)).earlyStart=0;
        findTaskByID(topSortedTask.get(0)).earlyFinish=findTaskByID(topSortedTask.get(0)).getDuration();
        durations[topSortedTask.get(0)]=findTaskByID(topSortedTask.get(0)).getDuration();
        startTime[topSortedTask.get(0)]=0;
        completed.add(topSortedTask.get(0));
        for(int i=1;i<topSortedTask.size();i++){
           // System.out.println("duration aranan: "+topSortedTask.get(i));
            findDuration(findTaskByID(topSortedTask.get(i)),durations);
            startTime[findTaskByID(topSortedTask.get(i)).getTaskID()]=findTaskByID(topSortedTask.get(i)).earlyStart;

        }

        return startTime;
    }





    public int findMax(int[] arr){
        int max=arr[0];
        for(int i =1;i<arr.length;i++)
            if(max<arr[i])
                max=arr[i];
        return max;
    }

    public void  findDuration(Task t,int[] durations){
        if(t.getDependenciesSize()==0) {
            durations[t.getTaskID()]=0;
            t.earlyStart=0;
            t.earlyFinish=t.earlyStart+t.getDuration();
        }
        else {
            int[] finishes=new int[t.getDependenciesSize()];
            for (int i = 0; i < t.getDependenciesSize(); i++) {
                 finishes[i]=findTaskByID(t.getDependencies().get(i)).earlyFinish;
            }
            t.earlyFinish=findMax(finishes)+t.getDuration();
            t.earlyStart=findMax(finishes);

        }


    }


public static String format = "%1$-10s %2$-5s %3$-5s %4$-5s %5$-5s %6$-5s %7$-10s\n";
    public Task findTaskByID(int ID){
        for(int i=0;i<tasks.size();i++){
            if(tasks.get(i).getTaskID()==ID)
                return tasks.get(i);
        }
        return null;
    }


    /**
     * @return the total duration of the project in days
     */
    public int getProjectDuration() {
        int projectDuration = 0;
        // TODO: YOUR CODE HERE
        int[] schedule=getEarliestSchedule();
        projectDuration=tasks.get(schedule.length - 1).getDuration() + schedule[schedule.length - 1];
        return projectDuration;
    }

    public void getTasks(){

        for(int i=0;i< tasks.size();i++) {
            System.out.println(tasks.get(i).getTaskID() + " " + tasks.get(i).getDuration() + " " +
                    tasks.get(i).getDescription());
            for(int j=0;j<tasks.get(i).getDependencies().size();j++)
                System.out.print(tasks.get(i).getDependencies().get(j)+" ");
            System.out.println();
        }
    }

    public static void maxCost(List<Task> tasks) {
        int max = -1;
        for (Task t : tasks) {
            if (t.criticalCost > max)
                max = t.criticalCost;
        }
        maxCost = max;
        System.out.println("Critical path length (cost): " + maxCost);
        for (Task t : tasks) {
            t.setLatest(maxCost);
        }
    }

    public static void print(Task[] tasks) {
        System.out.format(format, "Task", "ES", "EF", "LS", "LF", "Slack", "Critical?");
        for (Task t : tasks)
            System.out.format(format, (Object[]) t.toStringArray());
    }

///////
    public static void printlnDash(int limit, char symbol) {
        for (int i = 0; i < limit; i++) System.out.print(symbol);
        System.out.println();
    }


    public void printSchedule(int[] schedule) {
        int limit = 65;
        char symbol = '-';
        printlnDash(limit, symbol);
        System.out.println(String.format("Project name: %s", name));
        printlnDash(limit, symbol);

        // Print header
        System.out.println(String.format("%-10s%-45s%-7s%-5s", "Task ID", "Description", "Start", "End"));
        printlnDash(limit, symbol);
        for (int i = 0; i < schedule.length; i++) {
            Task t = tasks.get(i);
            System.out.println(String.format("%-10d%-45s%-7d%-5d", i, t.getDescription(), schedule[i], schedule[i] + t.getDuration()));
        }
        printlnDash(limit, symbol);
        System.out.println(String.format("Project will be completed in %d days.", tasks.get(schedule.length - 1).getDuration() + schedule[schedule.length - 1]));
        printlnDash(limit, symbol);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;

        int equal = 0;

        for (Task otherTask : ((Project) o).tasks) {
            if (tasks.stream().anyMatch(t -> t.equals(otherTask))) {
                equal++;
            }
        }
        return name.equals(project.name) && equal == tasks.size();
    }

}
