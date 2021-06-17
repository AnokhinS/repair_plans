package methods;

import beans.Brigade;
import beans.Well;
import calc.Calculations;
import utils.Combination;
import utils.PlanUtils;
import utils.Utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FullSearchSolution {
    private List<Well> wells;
    private Function<List<Well>, List<Well>> sortFunc;

    public FullSearchSolution(List<Well> wells) {
        this.wells = wells;
    }

    public List<Brigade> findBest(){
        double res = 0;
        int[] bestPlan = null;
        List<int[]> combinations = Combination.getCombinations(wells.size());
        for (int[] c : combinations){
            double profit = Calculations.calcProfit(PlanUtils.createPlan(wellSequence(c)),wells);
            if(profit > res){
                bestPlan = c;
                res = profit;
            }
        }
        return PlanUtils.createPlan(wellSequence(bestPlan));
    }

    public List<Well> wellSequence(int[] x){
        List<Well> res = new ArrayList<>();
        for (int i = 0; i < x.length; i++) {
            res.add(wells.get(x[i]));
        }
        return res;
    }



}
