import java.util.List;
import java.util.Objects;

public class Task {
    private final int taskID;
    private final String description;
    private final int duration;
    private final List<Integer> dependencies;




    public int criticalCost;
    // the earliest start
    public int earlyStart;
    // the earliest finish
    public int earlyFinish;
    public int latestStart;
    public int latestFinish;

    //////////

    public Task(int taskID, String description, int duration, List<Integer> dependencies) {
        this.taskID = taskID;
        this.description = description;
        this.duration = duration;
        this.dependencies = dependencies;
        this.earlyFinish = -1;

    }
    public void setLatest(int maxCost) {
        latestStart = maxCost - criticalCost;
        latestFinish = latestStart + duration;
    }
    public int getTaskID() {
        return taskID;
    }

    public String getDescription() {
        return description;
    }

    public int getDuration() {
        return duration;
    }

    public List<Integer> getDependencies() {
        return dependencies;
    }

    public int getDependenciesSize(){
        return dependencies.size();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return taskID == task.taskID && duration == task.duration && description.equals(task.description) && dependencies.equals(task.dependencies);
    }

    public String[] toStringArray() {
        String criticalCond = earlyStart == latestStart ? "Yes" : "No";
        String[] toString = { String.valueOf(taskID), earlyStart + "", earlyFinish + "", latestStart + "", latestFinish + "",
                latestStart - earlyStart + "", criticalCond };
        return toString;
    }



}
