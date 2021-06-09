package calc;

import beans.Brigade;
import beans.Well;
import common.Constants;
import utils.PlanUtils;

import java.util.*;

public class Calculations {

    public static double calcProfit(double[] weights, List<Well> wells){
        return calcProfit(PlanUtils.createPlan(weights,wells),wells);
    }

    public static double calcProfit(List<Brigade> brigades, List<Well> wells){
        double res = 0;
        List<Well> wellCopy = new ArrayList<>(wells);
        for (Brigade b : brigades){
            int day = 0;
            List<Well> repairs = b.getRepairedWells();
            for (Well w : repairs){
                res += w.getDebetBefore()* day;
                day += w.getRepairDuration();
                res += w.getDebetAfter()*(Constants.FULL_PERIOD - day);
                wellCopy.remove(w);
            }
        }
        for (Well w : wellCopy){
            res += w.getDebetBefore() * Constants.FULL_PERIOD;
        }
        return res;
    }

    public static void main(String[] args) {
        List<Well> wells = Arrays.asList(
                new Well(5,5,16),
                new Well(4,37,93),
                new Well(3,24,49));
        double[] X = {0,0.07,0.93};
        double profit = Calculations.calcProfit(X, wells);
        System.out.println(profit);
    }


}
