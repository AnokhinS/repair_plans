package test;

import beans.Brigade;
import beans.Well;
import calc.Calculations;
import common.Constants;
import common.Logger;
import common.WellPriority;
import info.Info;
import methods.NaiveSortSolution;
import methods.RandomMethod;
import methods.SwarmSolution;
import params.Lambda;
import params.Phi;
import plans.RandomPlan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Function;

public class SwarmTest {
    static Info naiveInfo = new Info();
    static Info randomChoiceInfo = new Info();
    static Info swarmInfo = new Info();

    public static int tests = 1000;

    private static List<RandomPlan> problems = new ArrayList<>();


    public static void main(String[] args) {
        for (int i = 0; i < tests; i++) {
            RandomPlan rp = new RandomPlan();
            problems.add(rp);

            long start = System.currentTimeMillis();
            double naiveProfit = getNaiveProfit(rp.getWells());
            long end = System.currentTimeMillis();
            naiveInfo.addTime(end-start);
            naiveInfo.addValue(naiveProfit);
        }
        parallel(getRandomChoiceFunction(),randomChoiceInfo);




//        for (int i = 0; i < 1; i++) {
//            getRun(problems).run();
//            logStat();
//        }


//        findBestParams();
//        agentCount();
//        problemSize();
//        iterationCount();
//        Constants.CLUSTER_SIZE = Constants.BRIGADE_COUNT * Constants.WELL_COUNT;
        parallel(getSwarmFunction(),swarmInfo);
        logStat();

    }

    private static void logStat() {
        System.out.println("Naive Info  "+naiveInfo);
        System.out.println("Random Info  "+randomChoiceInfo);
        System.out.println("Swarm Info  "+swarmInfo);

        Logger.writeMsg("Naive Info  "+naiveInfo);
        Logger.writeMsg("Random Info  "+randomChoiceInfo);
        Logger.writeMsg("Swarm Info  "+swarmInfo);

    }

    public static void agentCount() {
        List<Integer> sizes = Arrays.asList(1,2,5,8,10,15,20);


        for (int i : sizes) {
            Constants.CLUSTER_SIZE = i * Constants.BRIGADE_COUNT * Constants.WELL_COUNT;
            System.out.println("i=" + i);
            Logger.writeMsg("i=" + i);
            swarmInfo = new Info();
            parallel(getSwarmFunction(),swarmInfo);
            logStat();
        }
    }

    public static void iterationCount() {
        List<Integer> sizes = Arrays.asList(10,20,50,100);

        for (int i : sizes) {
            Constants.USELESS_ITERATIONS = i;
            System.out.println("i=" + i);
            Logger.writeMsg("i=" + i);
            swarmInfo = new Info();
            parallel(getSwarmFunction(),swarmInfo);
            logStat();
        }
    }

    public static void problemSize() {
        for (int i = 7; i <= 10; i++) {
            Constants.BRIGADE_COUNT = i * 2;
            Constants.WELL_COUNT = i * 5;
            Constants.CLUSTER_SIZE = Constants.WELL_COUNT * Constants.BRIGADE_COUNT;
            System.out.println("wells = " + Constants.WELL_COUNT + "   brigs = " + Constants.BRIGADE_COUNT);
            Logger.writeMsg("wells = " + Constants.WELL_COUNT + "   brigs = " + Constants.BRIGADE_COUNT);

            swarmInfo = new Info();
            parallel(getSwarmFunction(),swarmInfo);
            logStat();
        }
    }

    public static void findBestParams() {
        double step = 0.2;
        for (double i = 0.2; i <= 1; i += step) {
            for (double j = 0.2; j <= 1; j += step) {

//                Phi.PHI_MIN = i;
//                Phi.PHI_MAX = i+step;
//                Lambda.LAMBDA_MIN=j;
//                Lambda.LAMBDA_MAX=j+step;
//                System.out.println("PHI = ["+ Phi.PHI_MIN+", "+ Phi.PHI_MAX+"]");
//                System.out.println("Lambda = ["+ Lambda.LAMBDA_MIN+", "+ Lambda.LAMBDA_MAX+"]");
//                Logger.writeMsg("PHI = ["+ Phi.PHI_MIN+", "+ Phi.PHI_MAX+"]");
//                Logger.writeMsg("Lambda = ["+ Lambda.LAMBDA_MIN+", "+ Lambda.LAMBDA_MAX+"]");

                Constants.MUTATION_CHANCE = i;
                Constants.MUTATION_VALUE = j;
                System.out.println("chance = " + Constants.MUTATION_CHANCE + "   val= " + Constants.MUTATION_VALUE);
                Logger.writeMsg("chance = " + Constants.MUTATION_CHANCE + "   val= " + Constants.MUTATION_VALUE);
                swarmInfo = new Info();
                parallel(getSwarmFunction(),swarmInfo);
                logStat();
            }
        }


//        for (double i = 0; i <= 1; i += step) {
//            for (double j = 0; j <= 1; j += step) {
//
//                Phi.PHI_MIN = 0;
//                Phi.PHI_MAX = i;
//                Lambda.LAMBDA_MIN=j;
//                Lambda.LAMBDA_MAX=1;
//                System.out.println("PHI = ["+ Phi.PHI_MIN+", "+ Phi.PHI_MAX+"]");
//                System.out.println("Lambda = ["+ Lambda.LAMBDA_MIN+", "+ Lambda.LAMBDA_MAX+"]");
//                Logger.writeMsg("PHI = ["+ Phi.PHI_MIN+", "+ Phi.PHI_MAX+"]");
//                Logger.writeMsg("Lambda = ["+ Lambda.LAMBDA_MIN+", "+ Lambda.LAMBDA_MAX+"]");
//
////                Constants.MUTATION_CHANCE = i;
////                Constants.MUTATION_VALUE = j;
////                System.out.println("chance = " + Constants.MUTATION_CHANCE + "   val= " + Constants.MUTATION_VALUE);
////                Logger.writeMsg("chance = " + Constants.MUTATION_CHANCE + "   val= " + Constants.MUTATION_VALUE);
//
//                swarmInfo =new Info();
//                parallel(getSwarmFunction(),swarmInfo);
//                logStat();
//            }
//        }
    }

    public static void awaitTerminationAfterShutdown(ExecutorService threadPool) {
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(100, TimeUnit.HOURS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException ex) {
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public static Function<List<RandomPlan>, Runnable> getSwarmFunction() {
        return (subProblems)->new Runnable() {
            @Override
            public void run() {
                for (RandomPlan rp : subProblems) {
                    System.out.println(problems.indexOf(rp));
                    double swarmProfit = getSwarmProfit(rp.getWells());
                    swarmInfo.addValue(swarmProfit);
                }
            }
        };
    }

    public static Function<List<RandomPlan>, Runnable> getRandomChoiceFunction() {
        return (subProblems)->new Runnable() {
            @Override
            public void run() {
                for (RandomPlan rp : subProblems) {
                    double randomChoiseProfit = getRandomChoiseProfit(rp.getWells());
                    randomChoiceInfo.addValue(randomChoiseProfit);
                }
            }
        };
    }


    private static double getNaiveProfit(List<Well> wells) {
//        List<Well> wells = new ArrayList<>();
//        wells.add(new Well(5,40,90));
//        wells.add(new Well(5,15,65));
//        wells.add(new Well(5,25,75));
//        wells.add(new Well(5,10,60));
//        wells.add(new Well(5,30,80));
//        wells.add(new Well(5,20,70));
//        wells.add(new Well(6,100,160));
//        wells.add(new Well(6,100,160));
//        wells.add(new Well(6,100,160));
//        wells.add(new Well(5,30,70));
//
//        List<BrigadePlan> brigadePlans = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            brigadePlans.add(new BrigadePlan(10));
//        }
        NaiveSortSolution nss = new NaiveSortSolution(wells, wells1 -> WellPriority.subDesc(wells1));
        List<Brigade> best = nss.findBest();
        double sortProfit = Calculations.calcProfit(best, wells);
        return sortProfit;
    }

    private static double getRandomChoiseProfit(List<Well> wells) {
//        List<Well> wells = new ArrayList<>();
//        wells.add(new Well(5,40,90));
//        wells.add(new Well(5,15,65));
//        wells.add(new Well(5,25,75));
//        wells.add(new Well(5,10,60));
//        wells.add(new Well(5,30,80));
//        wells.add(new Well(5,20,70));
//        wells.add(new Well(6,100,160));
//        wells.add(new Well(6,100,160));
//        wells.add(new Well(6,100,160));
//        wells.add(new Well(5,30,70));
//
//        List<BrigadePlan> brigadePlans = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            brigadePlans.add(new BrigadePlan(10));
//        }

        RandomMethod rm = new RandomMethod(wells);
        List<Brigade> brigades = rm.find();
//        System.out.println(map);
        return Calculations.calcProfit(brigades, wells);
    }

    private static double getSwarmProfit(List<Well> wells) {
//        List<Well> wells = new ArrayList<>();
//        wells.add(new Well(5,40,90));
//        wells.add(new Well(5,15,65));
//        wells.add(new Well(5,25,75));
//        wells.add(new Well(5,10,60));
//        wells.add(new Well(5,30,80));
//        wells.add(new Well(5,20,70));
//        wells.add(new Well(6,100,160));
//        wells.add(new Well(6,100,160));
//        wells.add(new Well(6,100,160));
//        wells.add(new Well(5,30,70));
//
//        List<BrigadePlan> brigadePlans = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            brigadePlans.add(new BrigadePlan(10));
//        }
        SwarmSolution ss = new SwarmSolution(wells);
        List<Brigade> best = ss.findBest();
        double swarmProfit = Calculations.calcProfit(best, wells);
        return swarmProfit;
    }

    public static void parallel(Function<List<RandomPlan>, Runnable> function, Info info) {
        int threads = 10;
        int count = tests / threads;

        ExecutorService pool = Executors.newFixedThreadPool(threads);
        long start = System.currentTimeMillis();
        for (int k = 0; k < threads; k++) {
            pool.execute(function.apply(problems.subList(k*count,k*count+count)));
        }
        awaitTerminationAfterShutdown(pool);
        long end = System.currentTimeMillis();
        info.addTime(end-start);
    }
}

