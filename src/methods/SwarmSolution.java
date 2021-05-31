package methods;


import agents.SolutionAgent;
import beans.Brigade;
import beans.Well;
import calc.Calculations;
import common.Constants;
import common.WellPriority;
import utils.MatrixUtils;
import utils.PlanUtils;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SwarmSolution {
    private List<Well> wells;

    public SwarmSolution(List<Well> wells) {
        this.wells = wells;
    }

    private SolutionAgent bestSolution = null;

    public SolutionAgent getBestSolution() {
        return bestSolution;
    }

    private SolutionAgent getAgent(List<Well> sorted) {
        SolutionAgent agent = new SolutionAgent(wells);
        double val = 1;
        double[] X = new double[wells.size()];
        for (Well w : sorted){
            int index = wells.indexOf(w);
            X[index] = (val/2)*1.1;
            if(sorted.indexOf(w) == sorted.size()-1){
                X[index] = val;
            }
            val -= X[index];
        }
        agent.setB(X);
        agent.setX(X.clone());
        return agent;
    }

    private void addNaiveAgents(List<SolutionAgent> cluster) {
        List<Well> sorted = WellPriority.subDesc(wells);
        SolutionAgent sortAgent = getAgent(sorted);
        cluster.add(sortAgent);

        SolutionAgent sortAgent2 = getAgent(WellPriority.subDivDesc(wells));
        cluster.add(sortAgent2);

        double c1 = Calculations.calcProfit(PlanUtils.createPlan(sortAgent.getB(),wells), wells);
        double c2 = Calculations.calcProfit(PlanUtils.createPlan(sortAgent2.getB(),wells), wells);
        bestSolution = c1>c2 ? sortAgent : sortAgent2;
    }

    public List<Brigade> findBest() {
        int clusterSize = Constants.CLUSTER_SIZE;
//                brigades.size() * wells.size();
        List<SolutionAgent> cluster = new ArrayList<>();
//        addNaiveAgents(cluster);
        for (int i = 0; i < clusterSize; i++) {
            SolutionAgent ps = new SolutionAgent(wells);
//            ps.print();
            cluster.add(ps);
            double v = bestSolution == null ? 0: bestSolution.bestProfit();
            double v1 = ps.bestProfit();
            if (bestSolution == null || v1> v) {
                bestSolution = ps;
            }
        }
//        System.out.println("Лучшее решение до поиска:");
//        bestSolution.print();

        for (SolutionAgent sol : cluster) {
            sol.initV(bestSolution.getB());
        }

//        Phi.PHI_MIN = 0;
//        Phi.PHI_MAX =0;
//        Lambda.LAMBDA_MIN =1;
//        Lambda.LAMBDA_MAX =1;

        int uselessSteps = 0;
        while (uselessSteps != Constants.USELESS_ITERATIONS) {
            for (SolutionAgent sol : cluster) {

//                System.out.println("Before search");
//                System.out.println(Utils.toString(sol.getX()));

                sol.search(bestSolution.getB());
//                System.out.println("After search");
//                System.out.println(Utils.toString(sol.getX()));

                boolean needMutation = Math.random() < Constants.MUTATION_CHANCE;
                int tries = 0;
                if (needMutation) {
//                    System.out.println("Before mutation");
//                    System.out.println(Utils.toString(sol.getX()));
                    for (int i = 0; i < wells.size()/2; i++) {
                        sol.mutation();
                        if (sol.currentProfit() > sol.bestProfit()){
                            sol.setB(sol.getX());
                        }
                    }

//                    System.out.println("after mutation");
//                    System.out.println(Utils.toString(sol.getX()));
//            double newProfit = Calculations.calcProfit(X,wells);
//            while (oldProfit>= newProfit && tries < 100) {
//                X = oldX;
//                oldProfit = Calculations.calcProfit(X,wells);
//                mutation();
//                newProfit = Calculations.calcProfit(X,wells);
//                tries++;
//            }
//            if (tries == 100) {
//                X = oldX;
//            }
                }

                double v = bestSolution.bestProfit();
                double v1 = sol.bestProfit();
                if (v1>v) {
//                    System.out.println("new Best value = "+sol.bestProfit());
//                    sol.print();
                    bestSolution = sol;
                    uselessSteps = 0;

//                    Phi.PHI_MIN = 0;
//                    Phi.PHI_MAX =0;
//                    Lambda.LAMBDA_MIN =1;
//                    Lambda.LAMBDA_MAX =1;
//                    System.out.println("MOVE");
                }
            }
//            System.out.println("Has conf = "+hasConflicts);
//            System.out.println("dont have = "+dontHaveConflicts);
            uselessSteps++;
//            Phi.PHI_MIN = 0.1*uselessSteps;
//            Phi.PHI_MAX =0.1*uselessSteps;
//            Lambda.LAMBDA_MIN =1-0.1*uselessSteps;
//            Lambda.LAMBDA_MAX =1-0.1*uselessSteps;


        }
//        System.out.println("Лучшее решение после поиска:");
//        bestSolution.print();

        List<Brigade> plan = PlanUtils.createPlan(bestSolution.getB(), wells);
        return plan;
    }

}
