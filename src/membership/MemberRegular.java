package membership;

public class MemberRegular extends Member {

    private RecordTimeRegular butterflyRecord;
    private RecordTimeRegular crawlRecord;
    private RecordTimeRegular backCrawlRecord;
    private RecordTimeRegular breastRecord;

    public MemberRegular(String name, int age, boolean active) {
        super(name, age, active);
    }
}
