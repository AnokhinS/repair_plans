package beans;

import java.util.ArrayList;
import java.util.List;

public class Brigade {
    public static int count=1;
    private int id=count++;

    List<Well> repairedWells = new ArrayList<>();
    private double repairPeriod;

    public Brigade(double repairPeriod) {
        this.repairPeriod = repairPeriod;
    }

    public int willBeReady(){
        return repairedWells.stream().map(Well::getRepairDuration).reduce(0,(x,y)->x+y);
    }

    public int getId() {
        return id;
    }

    public List<Well> getRepairedWells() {
        return repairedWells;
    }

    public void setRepairedWells(List<Well> repairedWells) {
        this.repairedWells = repairedWells;
    }

    public double getRepairPeriod() {
        return repairPeriod;
    }

    public void setRepairPeriod(double repairPeriod) {
        this.repairPeriod = repairPeriod;
    }

}
