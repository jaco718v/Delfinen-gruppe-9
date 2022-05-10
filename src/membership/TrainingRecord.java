package membership;

public class TrainingRecord extends RecordTime{
  private double recordInSeconds;
  private int dayOfMonth;
  private int month;
  private int year;

  public TrainingRecord(double rekordTid, int dayOfMonth, int month, int year) {
    this.recordInSeconds = rekordTid;
    this.dayOfMonth = dayOfMonth;
    this.month = month;
    this.year = year;
  }
}
