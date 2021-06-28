package params;

public class Lambda {

    public static double LAMBDA_MIN = 0.6;
    public static double LAMBDA_MAX = 1;

    public static double getRandom(){
        return LAMBDA_MIN + Math.random()*(LAMBDA_MAX-LAMBDA_MIN);
    }
}
