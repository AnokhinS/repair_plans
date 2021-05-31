package utils;

import beans.Brigade;
import beans.Repair;
import beans.Well;
import calc.Calculations;
import common.Constants;
import common.WellPriority;
import coordination.Coordination;
import methods.NaiveSortSolution;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PlanUtils {

    public static List<Brigade> createPlan(double[] weights, List<Well> wells){
//        Map<Double, List<Well>> map = new HashMap<>();
//        for (int i = 0; i < weights.length; i++) {
//            if(map.containsKey(weights[i])){
//                map.get(weights[i]).add(wells.get(i));
//            }
//            else {
//                map.put(weights[i], Arrays.asList(wells.get(i)));
//            }
//        }
//        double[] sorted = Arrays.stream(weights).sorted().toArray();

        Map<Well, Double> map = new HashMap<>();
        for (int i = 0; i < wells.size(); i++) {
            map.put(wells.get(i),weights[i]);
        }
        List<Well> sortedWells = wells.stream().sorted(Collections.reverseOrder((w1,w2)->Double.compare(map.get(w1),map.get(w2)))).collect(Collectors.toList());
        return createPlan(sortedWells);
    }

    public static List<Brigade> createPlan(List<Well> wells){
        List<Brigade> brigades = new ArrayList<>();
        for (int i = 0; i < Constants.BRIGADE_COUNT; i++) {
            brigades.add(new Brigade(Constants.REPAIR_PERIOD));
        }
        for (Well w : wells){
            Brigade firstBrigade = brigades.stream().sorted((a,b)->a.willBeReady()-b.willBeReady()).findFirst().get();
            if(firstBrigade.willBeReady() + w.getRepairDuration() <= firstBrigade.getRepairPeriod()){
                firstBrigade.getRepairedWells().add(w);
            }
        }
        return brigades;
    }


    public static void main(String[] args) {
        List<Well> wells = Arrays.asList(new Well(), new Well(), new Well());
        wells.forEach(System.out::println);
        System.out.println();
        System.out.println();
        double[] X = {0.3,0.2,0.5};
        List<Brigade> plan = createPlan(X, wells);
        for (Brigade b : plan){
            b.getRepairedWells().forEach(System.out::println);
            System.out.println();
        }
    }
}
