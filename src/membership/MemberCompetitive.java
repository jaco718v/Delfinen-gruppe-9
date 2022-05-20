package membership;

import utilities.Enum;

import java.util.ArrayList;

public class MemberCompetitive extends Member {
    private Enum.SwimDiscipline swimDiscipline;
    private ArrayList<RecordTimeCompetition> competitions = new ArrayList<>();
    private ArrayList<RecordTimePractice> bestPracticeRecords = new ArrayList<>();

    public MemberCompetitive(String memberID, String name, String birthDate, boolean isActive, boolean isCompetitive) {
        super(memberID, name, birthDate, isActive, isCompetitive);
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
