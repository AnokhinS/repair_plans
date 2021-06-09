package info;

public class Info{
    private double value;
    private double time;

    public void addValue(double v) {
        value+=v;
    }

    public double getValue() {
        return value;
    }

    public void addTime(double v) {
        time+=v;
    }

    public double getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "{" +
                "value=" + value +
                ", time=" + time +
                '}';
    }
}
