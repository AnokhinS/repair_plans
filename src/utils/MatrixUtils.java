package utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MatrixUtils {

    public static List<Integer> wrongRows(double[][] X, double[] a) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < X.length; i++) {
            if (!isValidRow(X, i, a[i])) {
                list.add(i);
            }
        }
        return list;
    }

    public static List<Integer> wrongRows(double[][] X) {
        double[] a = new double[X.length];
        for (int i = 0; i < X.length; i++) {
            a[i] = 1;
        }
        return wrongRows(X, a);
    }


    public static List<Integer> wrongCols(double[][] X, double[] a) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < X.length; i++) {
            if (!isValidCol(X, i, a[i])) {
                list.add(i);
            }
        }
        return list;
    }

    public static double[][] transpose(double[][] X){
        double[][] res = new double[X.length][];
        for (int i = 0; i < X.length; i++) {
            res[i] = new  double[X[i].length];
            for (int j = 0; j < X[i].length; j++) {
                res[i][j] = X[j][i];
            }
        }
        return res;
    }

    public static double[][] transposeRev(double[][] X){
        double[][] res = new double[X.length][];
        for (int i = 0; i < X.length; i++) {
            res[i] = new  double[X[i].length];
            for (int j = 0; j < X[i].length; j++) {
                res[i][j] = X[X.length-1-j][X.length-1-i];
            }
        }
        return res;
    }

    public static double[][] getOppositeMatrix(double[][] X){
        boolean rev = Math.random()<0.5;
        return rev ? transposeRev(X) : transpose(X);
    }

    public static List<Integer> wrongCols(double[][] X) {
        double[] a = new double[X.length];
        for (int i = 0; i < X.length; i++) {
            a[i] = 1;
        }
        return wrongCols(X, a);
    }

    public static boolean checkMatrix(double[][] X, double[] a, double[] b) {
        return wrongRows(X, a).isEmpty() && wrongCols(X, b).isEmpty();
    }


    public static boolean isValidCol(double[][] X, int col, double value) {
        double v = sumOfCol(X, col);
        return v < value * 1.00001 && v > value * 0.99999;
    }

    public static boolean isValidRow(double[][] X, int row, double value) {
        double v = sumOfRow(X, row);
        return v < value * 1.00001 && v > value * 0.99999;
    }

    public static double sumOfCol(double[][] X, int col) {
        double sum = 0;
        for (int i = 0; i < X.length; i++) {
            sum += X[i][col];
        }
        return sum;
    }

    public static double sumOfRow(double[][] X, int row) {
        double sum = 0;
        for (int i = 0; i < X[row].length; i++) {
            sum += X[row][i];
        }
        return sum;
    }

    public static double[][] add(double[][] a, double[][] b) {
        if (a.length != b.length || a[0].length != b[0].length) {
            throw new RuntimeException();
        }
        double[][] res = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                res[i][j] = a[i][j] + b[i][j];
            }
        }
        return res;
    }

    public static boolean equals(double[][] a, double[][] b) {
        if (a.length != b.length || a[0].length != b[0].length) {
            return false;
        }
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                if (a[i][j] != b[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public static double[][] mul(double k, double[][] a) {
        double[][] res = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                res[i][j] = k * a[i][j];
            }
        }
        return res;
    }

    public static double[][] sub(double[][] a, double[][] b) {
        return add(a, mul(-1, b));
    }


    public static void print(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(BigDecimal.valueOf(matrix[i][j]).setScale(3, BigDecimal.ROUND_HALF_EVEN) + "  ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }

    public static void print(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + "             ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }

    public static boolean allZeros(double[][] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                if (a[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public static double[][] rounded(double[][] matrix) {
        double[][] round = new double[matrix.length][matrix.length];
        List<Integer> visitedCols = new ArrayList<>();
        for (int i = 0; i < round.length; i++) {
            double max = -1;
            int ind = -1;
            for (int j = 0; j < round.length; j++) {
                if (max < matrix[i][j] && !visitedCols.contains(j)) {
                    max = matrix[i][j];
                    ind = j;
                }
            }
            try {
                round[i][ind] = 1;
            } catch (Exception e) {
                e.printStackTrace();
            }

            visitedCols.add(ind);
        }
        return round;
    }

    public static double[][] simpleRound(double[][] matrix) {
        double[][] round = new double[matrix.length][matrix.length];
        for (int i = 0; i < round.length; i++) {
            double max = -1;
            int ind = -1;
            for (int j = 0; j < round.length; j++) {
                if (max < matrix[i][j]) {
                    max = matrix[i][j];
                    ind = j;
                }
            }
            round[i][ind] = 1;
        }
        return round;
    }

    public static double[][] subMatrix(double[][] X, int fromRow, int toRow, int fromCol, int toCol){
        double[][] res = new double[toRow-fromRow+1][toCol-fromCol+1];
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[i].length; j++) {
                res[i][j] = X[fromRow+i][fromCol+j];
            }
        }
        return res;
    }

    public static boolean isBoolMatrix(double[][] X) {
        for (int i = 0; i < X.length; i++) {
            for (int j = 0; j < X[i].length; j++) {
                if (X[i][j] != 0 && X[i][j] != 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public static double[][] copy(double[][] arr) {
        double[][] res = new double[arr.length][];
        for (int i = 0; i < arr.length; i++) {
            res[i] = new double[arr[i].length];
            for (int j = 0; j < arr[i].length; j++) {
                res[i][j] = arr[i][j];
            }
        }

        return res;
    }

    public static boolean containsNegative(double[][] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                if (a[i][j] < 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void print(List<double[][]> plan) {
        int maxRows = plan.stream().map(arr -> arr.length).max((o1, o2) -> o1 - o2).orElse(0);
        for (int i = 0; i < maxRows; i++) {
            String row = "";
            for (double[][] b : plan) {
                for (int j = 0; j < maxRows; j++) {
                    if (b.length <= maxRows) {
                        row += b[i][j] + "   ";
                    }
                }
                row += "      ";
            }
            System.out.println(row);
        }
        System.out.println();
        System.out.println();
    }
}
