package membership;

public class RecordTimeCompetitive extends RecordTime {
    private final String convention;
    private final String place;

    public RecordTimeCompetitive(Enum.SwimDiscipline swimDiscipline, double recordInSeconds, int dayOfMonth, int month, int year, String convention, String place) {
        super(swimDiscipline, recordInSeconds, dayOfMonth, month, year);
        this.convention = convention;
        this.place = place;
    }

    public String getConvention() {
        return convention;
    }

    public String getPlace() {
        return place;
    }
}
