package membership;

import java.util.ArrayList;

public class MemberCompetitive extends Member {
    private Enum.SwimDiscipline swimDiscipline;
    private ArrayList<RecordTimeCompetitive> competitions;

    public MemberCompetitive(String name, int age, boolean active) {
        super(name, age, active);
    }
}
