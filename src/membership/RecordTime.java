package membership;

import utilities.Enum;

public abstract class RecordTime {
    private final Enum.SwimDiscipline SWIM_DISCIPLINE;
    private final double RECORD_IN_SECONDS;
    private final String DATE_OF_RECORD;

    public RecordTime(Enum.SwimDiscipline SWIM_DISCIPLINE, double RECORD_IN_SECONDS, String DATE_OF_RECORD) {
        this.SWIM_DISCIPLINE = SWIM_DISCIPLINE;
        this.RECORD_IN_SECONDS = RECORD_IN_SECONDS;
        this.DATE_OF_RECORD = DATE_OF_RECORD;
    }
}
