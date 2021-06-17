package plans;

import beans.Brigade;
import beans.Well;
import common.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPlan {
    public static int count = 0;
    protected List<Well> wells = new ArrayList<>();

    public RandomPlan(){
        init();
    }

    public void init(){
        initWells();
    }

    protected void initWells(){
        for (int i = 0; i < Constants.WELL_COUNT; i++) {
            wells.add(new Well());
        }
    }

    public List<Well> getWells() {
        return wells;
    }

    public void setWells(List<Well> wells) {
        this.wells = wells;
    }
}
