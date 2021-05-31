package common;

import beans.Well;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class WellPriority {

    public static List<Well> subDesc(List<Well> wells){
        List<Well> newList = new ArrayList<>(wells);
        newList.sort(Comparator.comparingDouble(w -> -(w.getDebetAfter() - w.getDebetBefore())));
        return newList;
    }

    public static List<Well> subDivDesc(List<Well> wells){
        List<Well> newList = new ArrayList<>(wells);
        newList.sort(Comparator.comparingDouble(w -> -(w.getDebetAfter() - w.getDebetBefore())/w.getRepairDuration()));
        return newList;
    }

}
