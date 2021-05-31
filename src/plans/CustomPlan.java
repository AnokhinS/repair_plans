package plans;

import beans.Brigade;
import beans.Well;
import calc.Calculations;
import common.Constants;
import coordination.Coordination;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CustomPlan extends RandomPlan{

    public CustomPlan() {
        super(Constants.BRIGADE_COUNT,Constants.WELL_COUNT,Constants.REPAIR_PERIOD,Constants.FULL_PERIOD);
    }

    @Override
    protected void initWells() {
        wells.add(new Well(8,38,168));
        wells.add(new Well(8,1,2));
        wells.add(new Well(4,5,24));
//        wells.add(new Well(11,25,42));
//        wells.add(new Well(12,41,162));
//        wells.add(new Well(3,10,18));
//        wells.add(new Well(8,26,51));
//        wells.add(new Well(9,27,40));
//        wells.add(new Well(5,10,18));
//        wells.add(new Well(8,44,85));
//        wells.add(new Well(2,29,38));
//        wells.add(new Well(3,24,31));

    }
}
