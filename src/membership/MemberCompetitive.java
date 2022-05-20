package membership;

import utilities.Enum;

import java.util.ArrayList;

public class MemberCompetitive extends Member {
    private Enum.SwimDiscipline swimDiscipline;
    private ArrayList<RecordTimeCompetition> competitions = new ArrayList<>();
    private ArrayList<RecordTimePractice> bestPracticeRecords;

    public MemberCompetitive(String id, String name, String birthDate, boolean active) {
        super(id, name, birthDate, active);
    }

    public void AddRecordCompetition(RecordTimeCompetition record){
        this.competitions.add(record);
    }

    public void AddRecordPractice(RecordTimePractice record){
        this.bestPracticeRecords.add(record);
    }

    public Enum.SwimDiscipline getSwimDiscipline() {
        return swimDiscipline;
    }

    public ArrayList<RecordTimePractice> getBestPracticeRecords() {
        return bestPracticeRecords;
    }

    public ArrayList<RecordTimeCompetition> getCompetitions() {
        return competitions;
    }

    public void setCompetitions(ArrayList<RecordTimeCompetition> competitions) {
        this.competitions = competitions;
    }
}
