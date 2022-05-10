package membership;

public class RecordTimeRegular extends RecordTime {
  private SwimDiscipline swimDiscipline;
  private double recordInSeconds;
  private int dayOfMonth;
  private int month;
  private int year;

  public RecordTimeRegular(SwimDiscipline discipline, double recordTime, int dayOfMonth, int month, int year) {
    this.swimDiscipline = discipline;
    this.recordInSeconds = recordTime;
    this.dayOfMonth = dayOfMonth;
    this.month = month;
    this.year = year;
  }
}
