package membership;

import java.util.ArrayList;

public class CompetitiveMember extends Member{
  private ArrayList<SwimDiscipline> swimDiscipline = new ArrayList<>();
  private RecordTime butterflyRecord;
  private RecordTime crawlRecord;
  private RecordTime backCrawl;
  private RecordTime breastRecord;
  private ArrayList<Competition> competitions = new ArrayList<>();

  public CompetitiveMember(String name, int age, boolean active) {
    super(name, age, active);
  }

}
