package methods;


import agents.Particle;
import beans.Brigade;
import beans.Well;
import calc.Calculations;
import common.Constants;
import common.WellPriority;
import utils.PlanUtils;

import java.util.ArrayList;
import java.util.List;

public class SwarmSolution {
    private List<Well> wells;

    public SwarmSolution(List<Well> wells) {
        this.wells = wells;
    }

    private Particle bestSolution = null;

    public Particle getBestSolution() {
        return bestSolution;
    }

    private Particle getAgent(List<Well> sorted) {
        Particle agent = new Particle(wells);
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

    public void addNaiveAgents(List<Particle> cluster) {
        List<Well> sorted = WellPriority.subDesc(wells);
        Particle sortAgent = getAgent(sorted);
        cluster.add(sortAgent);

        Particle sortAgent2 = getAgent(WellPriority.subDivDesc(wells));
        cluster.add(sortAgent2);

        double c1 = Calculations.calcProfit(PlanUtils.createPlan(sortAgent.getB(),wells), wells);
        double c2 = Calculations.calcProfit(PlanUtils.createPlan(sortAgent2.getB(),wells), wells);
        bestSolution = c1>c2 ? sortAgent : sortAgent2;
    }

    public List<Brigade> findBest() {
        int clusterSize = Constants.CLUSTER_SIZE;
//                brigades.size() * wells.size();
        List<Particle> cluster = new ArrayList<>();
        if (Constants.WITH_TEACHER){
            addNaiveAgents(cluster);
        }


        for (int i = 0; i < clusterSize; i++) {
            Particle p = new Particle(wells);
//            p.print();
            cluster.add(p);
            double v = bestSolution == null ? 0: bestSolution.bestProfit();
            double v1 = p.bestProfit();
            if (bestSolution == null || v1> v) {
                bestSolution = p;
            }
        }
//        System.out.println("Лучшее решение до поиска:");
//        bestSolution.print();

        for (Particle sol : cluster) {
            sol.initV(bestSolution.getB());
        }

//        Phi.PHI_MIN = 0;
//        Phi.PHI_MAX =0;
//        Lambda.LAMBDA_MIN =1;
//        Lambda.LAMBDA_MAX =1;

        int uselessSteps = 0;
        while (uselessSteps != Constants.USELESS_ITERATIONS) {
            for (Particle sol : cluster) {

//                System.out.println("Before search");
//                System.out.println(Utils.toString(sol.getX()));

                sol.search(bestSolution.getB());
//                System.out.println("After search");
//                System.out.println(Utils.toString(sol.getX()));

                boolean needMutation = Math.random() < Constants.MUTATION_CHANCE;
                int tries = 0;
                double[] oldX = sol.getX();
                if (needMutation) {
//                    System.out.println("Before mutation");
//                    System.out.println(Utils.toString(sol.getX()));
                    for (int i = 0; i < wells.size()/2; i++) {
                        sol.mutation();
                        if (sol.currentProfit() > sol.bestProfit()){
                            sol.setB(sol.getX());
                        }
                        else {
                            sol.setX(oldX);
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
