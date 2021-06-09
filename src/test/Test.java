package test;

import beans.Brigade;
import beans.Well;
import calc.Calculations;
import common.Constants;
import common.WellPriority;
import methods.NaiveSortSolution;
import methods.RandomMethod;
import methods.SwarmSolution;
import plans.CustomPlan;
import plans.RandomPlan;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        int tests =100;
        List<Integer> list =new ArrayList<>();
        for (int i = 0; i < tests; i++) {
            list.add(i);
        }
        System.out.println(list.subList(95,105));

    }
}
