package membership;

public class RecordTimeCompetitive extends RecordTime {
    private final String convention;
    private final int placing;

    public RecordTimeCompetitive(Enum.SwimDiscipline swimDiscipline, double recordInSeconds, int dayOfMonth, int month, int year, String convention, int place) {
        super(swimDiscipline, recordInSeconds, dayOfMonth, month, year);
        this.convention = convention;
        this.placing = place;
    }

    public String getConvention() {
        return convention;
    }

    public int getPlacing() {
        return placing;
    }
}
