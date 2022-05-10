package membership;

public class RecordTimeCompetitive extends RecordTime{
  private Enum.SwimDiscipline swimDiscipline;
  private double timeInSeconds;
  private String convention;
  private String place;
  private int dayOfMonth;
  private int month;
  private int year;

  public RecordTimeCompetitive(double timeInSeconds, String convention, String place, int dayOfMonth, int month, int year) {
    this.timeInSeconds = timeInSeconds;
    this.convention = convention;
    this.place = place;
    this.dayOfMonth = dayOfMonth;
    this.month = month;
    this.year = year;
  }
}
