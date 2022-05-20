package membership;

import utilities.Enum;

public abstract class RecordTime {
    private final String memberID;
    private final String name;
    private final Enum.SwimDiscipline SWIM_DISCIPLINE;
    private final double RECORD_IN_SECONDS;
    private final String DATE_OF_RECORD;


    protected RecordTime(String memberID, String name, Enum.SwimDiscipline swim_discipline, double record_in_seconds, String date_of_record) {
        this.memberID = memberID;
        this.name = name;
        SWIM_DISCIPLINE = swim_discipline;
        RECORD_IN_SECONDS = record_in_seconds;
        DATE_OF_RECORD = date_of_record;
    }
}
