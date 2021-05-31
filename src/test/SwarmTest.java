package test;

import beans.Brigade;
import beans.Well;
import calc.Calculations;
import common.Constants;
import common.Logger;
import common.WellPriority;
import methods.NaiveSortSolution;
import methods.RandomMethod;
import methods.SwarmSolution;
import plans.RandomPlan;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SwarmTest {
    static int countBetter = 0;
    static int countWorse = 0;
    static int brokenPlans = 0;

    static List<Double> worseList = new ArrayList<>();
    static List<Double> betterList = new ArrayList<>();

    public static int tests = 1000;

    public static void main(String[] args) {
//        for (int i = 0; i < 1; i++) {
//            getRun(1).run();
//        }


//        findBestParams();
//        agentCount();
//        problemSize();

        Constants.CLUSTER_SIZE = Constants.BRIGADE_COUNT * Constants.WELL_COUNT;
        parallel();
        logStat();
        printStat();

    }

    private static void printStat() {
        System.out.println("worse = " + countWorse);
        System.out.println("better = " + countBetter);
        System.out.println("WORSE LIST");
        System.out.println(worseList);
        if (!worseList.isEmpty()) {
            double sum = worseList.stream().reduce(0d, (x, y) -> x+y);
            System.out.println("min = " + worseList.stream().min(Double::compareTo));
            System.out.println("avg = " + (sum/worseList.size()));
            System.out.println("max = " + worseList.stream().max(Double::compareTo));
        }

        System.out.println("Better List");
        System.out.println(betterList);
        if (!betterList.isEmpty()) {
            double sum = betterList.stream().reduce(0d, (x, y) -> x+y);
            double avg = sum/betterList.size();
            System.out.println("min = " + betterList.stream().min(Double::compareTo));
            System.out.println("avg = " + avg);
            System.out.println("max = " + betterList.stream().max(Double::compareTo));
        }


    }

    private static void logStat() {
        Logger.writeMsg("worse = " + countWorse);

        Logger.writeMsg("better = " + countBetter);
        Logger.writeMsg("WORSE LIST");
        Logger.writeMsg(worseList.toString());
        if (!worseList.isEmpty()) {
            double sum = worseList.stream().reduce(0d, (x, y) -> x+y);
            Logger.writeMsg("min = " + worseList.stream().min(Double::compareTo));
            Logger.writeMsg("avg = " + sum/worseList.size());
            Logger.writeMsg("max = " + worseList.stream().max(Double::compareTo));
        }

        Logger.writeMsg("Better List");
        Logger.writeMsg(betterList.toString());
        if (!betterList.isEmpty()) {
            double sum = betterList.stream().reduce(0d, (x, y) -> x+y);
            double avg = sum/betterList.size();
            Logger.writeMsg("min = " + betterList.stream().min(Double::compareTo));
            Logger.writeMsg("avg = " + avg);
            Logger.writeMsg("max = " + betterList.stream().max(Double::compareTo));
        }


    }

    public static void agentCount() {
        for (int i = 1; i <= 10; i++) {
            Constants.CLUSTER_SIZE = i * Constants.BRIGADE_COUNT * Constants.WELL_COUNT;
            System.out.println("i=" + i);
            Logger.writeMsg("i=" + i);

            countBetter = 0;
            countWorse = 0;
            worseList.clear();
            betterList.clear();
            parallel();
            printStat();
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

            countBetter = 0;
            countWorse = 0;
            worseList.clear();
            betterList.clear();
            parallel();
            printStat();
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

                countBetter = 0;
                countWorse = 0;
                worseList.clear();
                betterList.clear();
                parallel();
                printStat();
                logStat();
            }
        }


//                PhiTest.PHI_MIN = i;
//                PhiTest.PHI_MAX = i + step;
//                LambdaTest.LAMBDA_MIN = j;
//                LambdaTest.LAMBDA_MAX = j + step;
//                System.out.println("phi = [" + PhiTest.PHI_MIN + "," + PhiTest.PHI_MAX + "]");
//                System.out.println("lambda = [" + LambdaTest.LAMBDA_MIN + "," + LambdaTest.LAMBDA_MAX + "]");


//                Constants.MAX_PHI = i;
//                Constants.MAX_LAMBDA = j;
//                System.out.println("MAX_PHI = " + Constants.MAX_PHI + "  MAX_LAMBDA = " + Constants.MAX_LAMBDA);

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

    public static Runnable getRun(int tests) {
        return new Runnable() {
            @Override
            public void run() {


                for (int k = 0; k < tests; k++) {
                    System.out.println(k);
//                    Well.setCount(0);
                    RandomPlan rp = new RandomPlan(Constants.BRIGADE_COUNT, Constants.WELL_COUNT, Constants.REPAIR_PERIOD, Constants.FULL_PERIOD);
//                    rp.getWells().forEach(System.out::println);
//                    CustomPlan rp = new CustomPlan();

                    double naiveProfit = getNaiveProfit(rp.getBrigades(), rp.getWells());
                    double randomChoiseProfit = getRandomChoiseProfit(rp.getBrigades(), rp.getWells());
                    double swarmProfit = getSwarmProfit(rp.getBrigades(), rp.getWells());


//                    rp.getWells().forEach(System.out::println);
//                    System.out.println(best +"  "+sortProfit);
//                    System.out.println(swarmSolutionBest+"  "+swarmProfit);

                    if (naiveProfit > swarmProfit || randomChoiseProfit > swarmProfit) {
                        countWorse++;
                        rp.getWells().forEach(System.out::println);
                        System.out.println("sort " + "  " + naiveProfit);
                        System.out.println("random " + "  " + randomChoiseProfit);
                        System.out.println("swarm " + "  " + swarmProfit);
                        System.out.println("WORSE");
                        double betterProfit = naiveProfit > randomChoiseProfit ? naiveProfit : randomChoiseProfit;
                        worseList.add((betterProfit - swarmProfit) / betterProfit);
                    }

                    if (swarmProfit > naiveProfit && swarmProfit > randomChoiseProfit) {
                        countBetter++;
                        double betterProfit = naiveProfit > randomChoiseProfit ? naiveProfit : randomChoiseProfit;
                        double profit = (swarmProfit - betterProfit) / betterProfit;
                        betterList.add(profit);
                    }

//                    if (profit.compareTo(new double(0.05)) < 0) {
//                        rp.getWells().forEach(System.out::println);
//                        System.out.println(best);
//                        System.out.println(swarmSolutionBest);
//                    }
//                    System.out.println("sort " + "  " + sortProfit);
//                    System.out.println("swarm " + "  " + swarmProfit);

                }


            }
        };
    }

    private static double getNaiveProfit(List<Brigade> brigadePlans, List<Well> wells) {
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

    private static double getRandomChoiseProfit(List<Brigade> brigadePlans, List<Well> wells) {
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

    private static double getSwarmProfit(List<Brigade> brigadePlans, List<Well> wells) {
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

    public static void parallel() {
        int threads = 10;
        int count = tests / threads;
        long start = System.currentTimeMillis();
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        for (int k = 0; k < threads; k++) {
            pool.execute(getRun(count));
        }
        awaitTerminationAfterShutdown(pool);
        long end = System.currentTimeMillis();
//        System.out.println("TIME = " + (end - start));
//        System.out.println("worse = " + countWorse);
//        System.out.println("better = " + countBetter);

    }
}

