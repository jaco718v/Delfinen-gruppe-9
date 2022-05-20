package membership;

import utilities.Enum;

public abstract class RecordTime {
    private final String memberID;
    private final Enum.SwimDiscipline SWIM_DISCIPLINE;
    private final double RECORD_IN_SECONDS;
    private final String DATE_OF_RECORD;


    protected RecordTime(String memberID, Enum.SwimDiscipline swim_discipline, double record_in_seconds, String date_of_record) {
        this.memberID = memberID;
        SWIM_DISCIPLINE = swim_discipline;
        RECORD_IN_SECONDS = record_in_seconds;
        DATE_OF_RECORD = date_of_record;
    }

    public String getDATE_OF_RECORD() {
        return DATE_OF_RECORD;
    }

    public double getRECORD_IN_SECONDS() {
        return RECORD_IN_SECONDS;
    }

    public String getMemberID() {
        return memberID;
    }

    public Enum.SwimDiscipline getSWIM_DISCIPLINE() {
        return SWIM_DISCIPLINE;
    }
}
