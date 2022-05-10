package membership;

public class MemberRegular extends Member {

    private TrainingRecord butterflyRecord;
    private TrainingRecord crawlRecord;
    private TrainingRecord backCrawlRecord;
    private TrainingRecord breastRecord;

    public MemberRegular(String name, int age, boolean active) {
        super(name, age, active);
    }
}
