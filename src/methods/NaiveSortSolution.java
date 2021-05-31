package methods;

import beans.Brigade;
import beans.Well;
import utils.PlanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NaiveSortSolution {
    private List<Well> wells;
    private Function<List<Well>, List<Well>> sortFunc;

    public NaiveSortSolution(List<Well> wells, Function<List<Well>, List<Well>> sortFunc) {
        this.wells = wells;
        this.sortFunc = sortFunc;
    }

    public List<Brigade> findBest(){
        List<Well> wellList = sortFunc.apply(wells);
        return PlanUtils.createPlan(wellList);
    }
}
