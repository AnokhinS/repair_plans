package beans;

import common.Constants;

public class Well {
    private static int count =1;
    private int id = count++;

    private int repairDuration = (int)((Math.random()* Constants.REPAIR_PERIOD)+1);
    private double debetBefore = (int)((Math.random()*50)+1);
    private double debetAfter =  (int)((Math.random()*2+1) * debetBefore);

    public Well(int repairDuration, double debetBefore, double debetAfter) {
        this.repairDuration = repairDuration;
        this.debetBefore = debetBefore;
        this.debetAfter = debetAfter;
    }

    public Well() {
    }

    public int getRepairDuration() {
        return repairDuration;
    }

    public void setRepairDuration(int repairDuration) {
        this.repairDuration = repairDuration;
    }

    public double getDebetBefore() {
        return debetBefore;
    }

    public void setDebetBefore(double debetBefore) {
        this.debetBefore = debetBefore;
    }

    public double getDebetAfter() {
        return debetAfter;
    }

    public void setDebetAfter(double debetAfter) {
        this.debetAfter = debetAfter;
    }

    public int getId() {
        return id;
    }


    @Override
    public String toString() {
        return "Well{" +
                "id=" + id +
                ", repairDuration=" + repairDuration +
                ", debetBefore=" + debetBefore +
                ", debetAfter=" + debetAfter +
                '}';
    }

    public static void setCount(int count) {
        Well.count = count;
    }
}
