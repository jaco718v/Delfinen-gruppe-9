package membership;

public class Member {
  private String name;
  private int age;
  private boolean active;
  //private ageGroup

  public Member(String name, int age, boolean active){
    this.name=name;
    this.age=age;
    this.active=active;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  public boolean getActive() {
    return active;
  }
}
