package utilities;

import java.util.Comparator;

public class RecordComparator implements Comparator<String[]> {
  @Override
  public int compare(String[] o1, String[] o2) {
    return (int)(Double.parseDouble(o1[2])*100 - Double.parseDouble(o2[2])*100);
  }
}
