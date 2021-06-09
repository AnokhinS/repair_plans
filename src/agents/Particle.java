package agents;

import beans.Brigade;
import beans.Well;
import calc.Calculations;
import common.Constants;
import params.Lambda;
import params.Phi;
import utils.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Particle {
    private static int count = 0;
    private int id = count++;

    private double[] X = new double[Constants.WELL_COUNT];
    private double[] B = new double[Constants.WELL_COUNT];
    private double[] V = new double[Constants.WELL_COUNT];

    private List<Well> wells;

    public Particle(List<Well> wells) {
        this.wells = wells;
        createRandom();
    }


    public void createRandom() {
        double val =1;
        List<Integer> available = new ArrayList<>();
        for (int i = 0; i < wells.size(); i++) {
            available.add(i);
        }
        while (val != 0){
            double v = Math.random()*val;
            if(available.size() == 1){
                v = val;
            }
            int ind = (int) (Math.random()*available.size());
            X[available.get(ind)] = v;
            val-=v;
            available.remove(ind);
        }
        B = X.clone();
    }

    public void mutation() {
        int i = (int) (Math.random()*X.length);
        int j = (int) (Math.random()*X.length);
        while (i==j){
            j = (int) (Math.random()*X.length);
        }
        double res = Math.min(X[i],X[j]) * Constants.MUTATION_VALUE;
        X[i]-=res;
        X[j]+=res;
    }

    public double[] initV(double[] G) {
        double phi1 = getPhi1(X, B, G);
        double phi2 = getPhi2(X, B, G);
        return ArrayUtils.add(ArrayUtils.mul(phi1, ArrayUtils.sub(B, X)), ArrayUtils.mul(phi2, ArrayUtils.sub(G, X)));
    }

    private double getPhi1(double[] X, double[] B, double[] G) {
        double phi1 = 0;
        if (!ArrayUtils.equals(B, X) && !ArrayUtils.equals(G, X)) {
            phi1 = Phi.getRandom();

        }
        if (!ArrayUtils.equals(B, X) && ArrayUtils.equals(G, X)) {
            phi1 = 1;
        }
        if (ArrayUtils.equals(B, X) && !ArrayUtils.equals(G, X)) {
            phi1 = 0;
        }
        return phi1;
    }

    private double getPhi2(double[] X, double[] B, double[] G) {
        return 1 - getPhi1(X, B, G);
    }

    public void search(double[] G) {
        if (ArrayUtils.equals(X, G)) {
            int i = 0;
        }
        double lambda1 = 0;
        double lambda2 = 0;
        if (ArrayUtils.equals(B, X) && ArrayUtils.equals(G, X)) {
            lambda1 = 1;
        } else {
            lambda1 = Lambda.getRandom();
        }
        lambda2 = 1 - lambda1;


        V = ArrayUtils.add(ArrayUtils.mul(lambda1, V), ArrayUtils.mul(lambda2, ArrayUtils.add(ArrayUtils.mul(getPhi1(X, B, G), ArrayUtils.sub(B, X)),
                ArrayUtils.mul(getPhi2(X, B, G), ArrayUtils.sub(G, X)))));
        X = ArrayUtils.add(X, V);
        double[] oldX = X.clone();
        double oldProfit = Calculations.calcProfit(X,wells);


        double currentProfit = currentProfit();
        double bestProfit = bestProfit();
        if (currentProfit > bestProfit) {
            B = X.clone();
        }

    }

    public double currentProfit() {
        double profit = Calculations.calcProfit(X,wells);
        return profit;
    }

    public double bestProfit() {
        double profit = Calculations.calcProfit(B,wells);
        return profit;
    }

    public void print() {
        System.out.println("Agent " + id);
        System.out.println("current "+ Utils.toString(X));
        System.out.println("best "+ Utils.toString(B));
        System.out.println();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double[] getX() {
        return X;
    }

    public void setX(double[] x) {
        X = x;
    }

    public double[] getB() {
        return B;
    }

    public void setB(double[] b) {
        B = b;
    }

    public double[] getV() {
        return V;
    }

    public void setV(double[] v) {
        V = v;
    }

    public List<Well> getWells() {
        return wells;
    }

    public void setWells(List<Well> wells) {
        this.wells = wells;
    }
}
