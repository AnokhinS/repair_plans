package utils;

public class Utils {
    public static String toString(double[] x){
        String res = "";
        for (int i = 0; i < x.length; i++) {
            if (i!=0){
                res+=", ";
            }
            res+=x[i];
        }
        return "["+res+"]";
    }
    public static String toString(int[] x){
        String res = "";
        for (int i = 0; i < x.length; i++) {
            if (i!=0){
                res+=", ";
            }
            res+=x[i];
        }
        return "["+res+"]";
    }
}
