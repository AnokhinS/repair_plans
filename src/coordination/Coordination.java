package coordination;

import beans.Brigade;
import beans.Well;
import utils.MatrixUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Coordination {

    public static boolean hasConflicts(Map<Brigade,List<Well>> map){
        Set<Well> repairedWells = new HashSet<>();
        for (Map.Entry<Brigade, List<Well>> e : map.entrySet()){
            for (Well w : e.getValue()){
                if (repairedWells.contains(w)){
                    return true;
                }
                else {
                    repairedWells.add(w);
                }
            }
        }
        return false;
    }

    private static List<double[][]> others(List<double[][]> fullList, double[][] obj){
        List<double[][]> res = new ArrayList<>(fullList);
        res.remove(obj);
        return res;
    }

    private static int getMaxIndex(double[] arr){
        double max = 0;
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            if(max < arr[i]){
                max = arr[i];
                index = i;
            }
        }
        return index;
    }

}
