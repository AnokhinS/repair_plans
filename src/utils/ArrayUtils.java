package utils;

public class ArrayUtils {
    public static double[] add(double[] a, double[] b) {
        if (a.length != b.length) {
            throw new RuntimeException();
        }
        double[] res = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            res[i] = a[i] + b[i];
        }
        return res;
    }

    public static boolean equals(double[] a, double[] b) {
        if (a.length != b.length) {
            return false;
        }
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }

    public static double[] mul(double k, double[] a) {
        double[] res = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            res[i] = k * a[i];
        }
        return res;
    }

    public static double[] sub(double[] a, double[] b) {
        return add(a, mul(-1, b));
    }
}
