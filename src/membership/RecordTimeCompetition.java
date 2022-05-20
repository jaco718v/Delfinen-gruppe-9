package membership;

import utilities.Enum;

public class RecordTimeCompetition extends RecordTime {
    private final String convention;
    private final int placing;

    public RecordTimeCompetition(String memberID, Enum.SwimDiscipline swim_discipline, double record_in_seconds, String date_of_record, String convention, int placing) {
        super(memberID, swim_discipline, record_in_seconds, date_of_record);
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
