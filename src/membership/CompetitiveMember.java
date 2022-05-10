package membership;

import java.util.ArrayList;

public class CompetitiveMember extends Member{
  private ArrayList<Enum.SwimDiscipline> swimDiscipline = new ArrayList<>();
  private RecordTime butterflyRecord;
  private RecordTime crawlRecord;
  private RecordTime backCrawl;
  private RecordTime breastRecord;
  private ArrayList<RecordTimeCompetitive> competitions = new ArrayList<>();

  public CompetitiveMember(String name, int age, boolean active) {
    super(name, age, active);
  }

}
