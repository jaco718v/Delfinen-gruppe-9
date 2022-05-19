package membership;

import utilities.Enum;

public class RecordTimeCompetition extends RecordTime {
    private final String convention;
    private final int placing;

    public RecordTimeCompetition(Enum.SwimDiscipline SWIM_DISCIPLINE, double RECORD_IN_SECONDS, String DATE_OF_RECORD, String convention, int placing) {
        super(SWIM_DISCIPLINE, RECORD_IN_SECONDS, DATE_OF_RECORD);
        this.convention = convention;
        this.placing = placing;
    }

    public String getConvention() {
        return convention;
    }

    public int getPlacing() {
        return placing;
    }
}
