package membership;

import utilities.Enum;

import java.util.ArrayList;

public class MemberCompetitive extends Member {
    private ArrayList<Enum.SwimDiscipline> swimDiscipliner = new ArrayList<>();
    private ArrayList<RecordTimeCompetitive> competitions = new ArrayList<>();
    private ArrayList<RecordTimeRegular> bestPracticeRecords;

    public MemberCompetitive(String name, int age, boolean active) {
        super(name, age, active);
    }

    public ArrayList<RecordTimeRegular> getBestPracticeRecords() {
        return bestPracticeRecords;
    }

    public ArrayList<RecordTimeCompetitive> getCompetitions() {
        return competitions;
    }

    public void setCompetitions(ArrayList<RecordTimeCompetitive> competitions) {
        this.competitions = competitions;
    }
}
