package utilities;

import membership.RecordTimePractice;

import java.util.Comparator;

public class RecordComparator implements Comparator<RecordTimePractice> {
  @Override
  public int compare(RecordTimePractice o1, RecordTimePractice o2) {
    return (int)(o1.getRECORD_IN_SECONDS()*100 - o2.getRECORD_IN_SECONDS()*100);
  }
}
