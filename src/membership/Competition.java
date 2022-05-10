package membership;

public class Competition extends RecordTime{
  private SwimDiscipline swimDiscipline;
  private double timeInSeconds;
  private String convention;
  private String place;
  private int dayOfMonth;
  private int month;
  private int year;

  public Competition(double timeInSeconds, String convention, String place, int dayOfMonth, int month, int year) {
    this.timeInSeconds = timeInSeconds;
    this.convention = convention;
    this.place = place;
    this.dayOfMonth = dayOfMonth;
    this.month = month;
    this.year = year;
  }
}
