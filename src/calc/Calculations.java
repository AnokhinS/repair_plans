package calc;

import beans.Brigade;
import beans.Well;
import common.Constants;
import utils.PlanUtils;

import java.util.*;

public class Calculations {

    public static double calcProfit(double[] weights, List<Well> wells){
        for (int i = 0; i < weights.length; i++) {
            if (weights[i] <0 ){
                int k =3;
            }
        }
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
}
