package membership;

import java.util.ArrayList;

public class MemberCompetitive extends Member {
    private Enum.SwimDiscipline swimDiscipline;
    private ArrayList<RecordTimeCompetitive> competitions;

    public MemberCompetitive(String name, int age, boolean active) {
        super(name, age, active);
    }

    public ArrayList<RecordTimeCompetitive> getCompetitions() {
        return competitions;
    }

    public void setCompetitions(ArrayList<RecordTimeCompetitive> competitions) {
        this.competitions = competitions;
    }
}
