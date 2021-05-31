package coordination;

import beans.Brigade;
import beans.Repair;
import beans.Well;
import utils.MatrixUtils;
import utils.PlanUtils;

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

    public static boolean hasConflicts(List<Brigade> brigadePlans, Function<Brigade, double[][]> func, List<Well> wells){
        Map<Brigade, List<Well>> map = new HashMap<>();
        for (Brigade b : brigadePlans){
            List<Repair> repairs = null;
            List<Well> collect = repairs.stream().map(r -> r.getWell()).collect(Collectors.toList());
            map.put(b, collect);
        }
        return hasConflicts(map);
    }

    public static List<double[][]> coord(List<double[][]> plans, List<Well> wells){
        List<double[][]> res = new ArrayList<>();
        for (int i = 0; i < plans.size(); i++) {
            res.add(MatrixUtils.copy(plans.get(i)));
        }
        Map<double[][],List<Integer>> usedRows = new HashMap<>();
        for (double[][] brigade : res){
            usedRows.put(brigade, new ArrayList<>());
        }
        for (int row = 0; row < res.get(0).length; row++) {
            int stop = updateRow(res, row, usedRows, wells);
            if(stop == -1){
                break;
            }
            else if (stop == -2){
                continue;
            }
            else {
                row--;
            }
        }
        return res;
    }

    private static int updateRow(List<double[][]> plans, int row,
               Map<double[][],List<Integer>> usedRows, List<Well> wells){
        int res = -2;
        Map<Integer,List<double[][]>> maxCols = new HashMap<>();
        for (double[][] brigade : plans) {
            int maxIndex = getMaxIndex(brigade[row]);
            if(maxCols.containsKey(maxIndex)){
                maxCols.get(maxIndex).add(brigade);
            }
            else {
                List<double[][]> list = new ArrayList<>();
                list.add(brigade);
                maxCols.put(maxIndex, list);
            }
        }
        for (Integer col : maxCols.keySet()){
            List<double[][]> brigades = maxCols.get(col);
            if(brigades.size()>1){
                Map<Double, double[][]> brigadeMap = new HashMap<>();
                for (double[][] br : brigades){
                    List<Repair> repairs = null;
                    double dur = 0;
                    for (int i = 0; i < row; i++) {
                        dur += repairs.get(i).getRepairDuration();
                    }
                    brigadeMap.put(dur,br);
                }
                double min = brigadeMap.keySet().stream().min(Double::compareTo).orElse(-1d);
                double[][] bestBrigade = brigadeMap.get(min);
                List<double[][]> brigadesForUpdate = others(brigades, bestBrigade);
                for (double[][] brigade : brigadesForUpdate) {
                    double[] tmp = brigade[row];
                    int i = row +1;
                    while (usedRows.get(brigade).contains(i)){
                        i++;
                    }
                    if(i < brigade.length){
                        brigade[row] = brigade[i];
                        brigade[i] = tmp;
                        usedRows.get(brigade).add(i);
                        res = i;
                    }
                    else {
                        res = -1;
                    }
                }
            }
        }
        return res;
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
