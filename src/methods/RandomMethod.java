package methods;

import beans.Brigade;
import beans.Well;
import calc.Calculations;
import common.Constants;
import utils.PlanUtils;

import java.util.*;

public class RandomMethod {
    private static int SMALL = 1000;
    private static int MEDIUM = 6000;
    private static int LARGE = 30000;


    private List<Well> wells;

    public RandomMethod(List<Well> wells) {
        this.wells = wells;
    }

    public List<Brigade> find(){
        int count = SMALL;
        if (Constants.WELL_COUNT == 32){
            count = LARGE;
        }
        if (Constants.WELL_COUNT == 16){
            count = MEDIUM;
        }

        double[] best = null;
        for (int i = 0; i < count; i++) {
            double[] X = getOne();
            double profit = Calculations.calcProfit(X, wells);
            if(best == null || profit> Calculations.calcProfit(best,wells)){
                best = X;
            }
        }
        return PlanUtils.createPlan(best,wells);
    }

    private double[] getOne(){
        double val =1;
        List<Integer> available = new ArrayList<>();
        for (int i = 0; i < wells.size(); i++) {
            available.add(i);
        }
        double[] X = new double[wells.size()];
        while (val != 0){
            double v = Math.random()*val;
            if(available.size() == 1){
                v = val;
            }
            int ind = (int) (Math.random()*available.size());
            X[available.get(ind)] = v;
            val-=v;
            available.remove(ind);
        }
        return X;
    }

}
