package test;

import beans.Brigade;
import calc.Calculations;
import common.Constants;
import methods.SwarmSolution;
import plans.CustomPlan;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        Constants.CLUSTER_SIZE = 2;
        CustomPlan cp = new CustomPlan();
        int worse =0;
        int better = 0;
        for (int i = 0; i < 1000; i++) {
            SwarmSolution ss = new SwarmSolution(cp.getWells());
            List<Brigade> best = ss.findBest();
            double profit = Calculations.calcProfit(best, cp.getWells());
            System.out.println("sort   4350.0");
            System.out.println("swarm "+profit);
            if(profit<4350){
                System.out.println("WORSE");
                worse++;
            }
            else if(profit>4350){
                System.out.println("Better");
                better++;
            }
        }
        System.out.println(worse);
        System.out.println(better);

    }
}
