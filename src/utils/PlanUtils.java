package utils;

import beans.Brigade;
import beans.Well;
import common.Constants;

import java.util.*;
import java.util.stream.Collectors;

public class PlanUtils {

    public static List<Brigade> createPlan(double[] weights, List<Well> wells){
        Map<Well, Double> map = new HashMap<>();
        for (int i = 0; i < wells.size(); i++) {
            map.put(wells.get(i),weights[i]);
        }
        List<Well> sortedWells = wells.stream().sorted(Collections.reverseOrder((w1,w2)->Double.compare(map.get(w1),map.get(w2)))).collect(Collectors.toList());
        List<Well> withoutNegative = new ArrayList<>(sortedWells);
        for (Well w : sortedWells){
            if(map.get(w) < 0){
                withoutNegative.remove(w);
            }
        }
        return createPlan(withoutNegative);
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
}
