package beans;

public class Repair {
    private static int count=0;
    private int id=count++;

    private Well well;
    private double startDate;

    public Repair(Well well, double startDate) {
        this.well = well;
        this.startDate = startDate;
    }

    public int getEndDate(){
        return (int) (startDate + well.getRepairDuration());
    }

    public int getRepairDuration(){
        return (int) well.getRepairDuration();
    }

    public Well getWell() {
        return well;
    }

    public void setWell(Well well) {
        this.well = well;
    }

    public double getStartDate() {
        return startDate;
    }

    public void setStartDate(int startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "Repair{" +
                "id=" + id +
                ", well=" + well +
                ", startDate=" + startDate +
                '}';
    }

}
