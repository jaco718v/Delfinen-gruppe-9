package membership;

import java.util.ArrayList;

public class MemberCompetitive extends Member {
    private SwimDiscipline swimDiscipline;
    private ArrayList<Competition> competitions;

    public MemberCompetitive(String name, int age, boolean active) {
        super(name, age, active);
    }
}
