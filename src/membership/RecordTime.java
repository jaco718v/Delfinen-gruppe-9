package membership;

public abstract class RecordTime {
    private final Enum.SwimDiscipline swimDiscipline;
    private final double recordInSeconds;
    private final int dayOfMonth;
    private final int month;
    private final int year;

    public RecordTime(Enum.SwimDiscipline swimDiscipline, double recordInSeconds, int dayOfMonth, int month, int year) {
        this.swimDiscipline = swimDiscipline;
        this.recordInSeconds = recordInSeconds;
        this.dayOfMonth = dayOfMonth;
        this.month = month;
        this.year = year;
    }

    public Enum.SwimDiscipline getSwimDiscipline() {
        return swimDiscipline;
    }

    public double getRecordInSeconds() {
        return recordInSeconds;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}
