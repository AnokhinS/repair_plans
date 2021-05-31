package plans;

import beans.Brigade;
import beans.Well;

import java.util.ArrayList;
import java.util.List;

public class RandomPlan {
    public static int count = 0;
    protected int brigadeCount = 1;
    protected int wellCount = 5;
    protected int repairPeriod = 5;
    protected int fullPeriod = 25;
    protected List<Well> wells = new ArrayList<>();
    protected List<Brigade> brigades = new ArrayList<>();

    public RandomPlan(int brigadeCount, int wellCount, int repairPeriod, int fullPeriod) {
        this.brigadeCount = brigadeCount;
        this.wellCount = wellCount;
        this.repairPeriod = repairPeriod;
        this.fullPeriod = fullPeriod;
        init();
    }

    public void init(){
        initWells();
        initBrigades();
    }

    protected void initWells(){
        for (int i = 0; i < wellCount; i++) {
            wells.add(new Well());
        }
    }

    protected void initBrigades(){
        for (int i = 0; i < brigadeCount; i++) {
            brigades.add(new Brigade(repairPeriod));
        }
    }

    public List<Well> getWells() {
        return wells;
    }

    public void setWells(List<Well> wells) {
        this.wells = wells;
    }

    public List<Brigade> getBrigades() {
        return brigades;
    }

    public void setBrigades(List<Brigade> brigades) {
        this.brigades = brigades;
    }

    public int getRepairPeriod() {
        return repairPeriod;
    }

    public void setRepairPeriod(int repairPeriod) {
        this.repairPeriod = repairPeriod;
    }

    public int getFullPeriod() {
        return fullPeriod;
    }

    public void setFullPeriod(int fullPeriod) {
        this.fullPeriod = fullPeriod;
    }
}
