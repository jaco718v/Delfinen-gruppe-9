package membership;

import java.util.ArrayList;

public class MemberRegular extends Member {

    private RecordTimeRegular butterflyRecord;
    private RecordTimeRegular crawlRecord;
    private RecordTimeRegular backCrawlRecord;
    private RecordTimeRegular breastRecord;
    private ArrayList<RecordTimeRegular> bestPracticeRecords;

    public MemberRegular(String name, int age, boolean active) {
        super(name, age, active);
    }
}
