package test;

import beans.Brigade;
import beans.Well;
import calc.Calculations;
import common.Constants;
import common.WellPriority;
import methods.FullSearchSolution;
import methods.NaiveSortSolution;
import methods.RandomMethod;
import methods.SwarmSolution;
import plans.CustomPlan;
import plans.RandomPlan;
import utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {




    }

    private static double getSwarmProfit(List<Well> wells) {
        SwarmSolution ss = new SwarmSolution(wells);
        List<Brigade> best = ss.findBest();
        double swarmProfit = Calculations.calcProfit(best, wells);
        return swarmProfit;
    }

    private static double getNaiveProfit(List<Well> wells) {
        NaiveSortSolution nss = new NaiveSortSolution(wells, wells1 -> WellPriority.subDesc(wells1));
        List<Brigade> best = nss.findBest();
        double sortProfit = Calculations.calcProfit(best, wells);
        return sortProfit;
    }
}
