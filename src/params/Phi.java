package params;

public class Phi {
    public static double PHI_MIN = 0.2;
    public static double PHI_MAX = 0.3;


    public static double getRandom(){
        return PHI_MIN + Math.random()*(PHI_MAX-PHI_MIN);
    }




}
