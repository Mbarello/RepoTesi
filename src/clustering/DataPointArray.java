package clustering;

import java.util.ArrayList;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: 2004</p>
 *
 * <p>Company: </p>
 *
 * @author Giorgio Leonardi
 * @version 1.1
 */
public class DataPointArray {
  private ArrayList clusterItems;

  public DataPointArray() {
    clusterItems= new ArrayList();
  }

  public DataPointArray(int dimensione) {
    clusterItems= new ArrayList(dimensione);
  }

  public void addDataPoint (ClusterItem item) {
    clusterItems.add(item);
  }

  public void addDataPoint (ClusterItem item, int position) {
    clusterItems.add(position, item);
  }

  public void setDataPoint (ClusterItem item, int position) {
    clusterItems.set(position, item);
  }

  public ClusterItem getDataPoint (int position) {
    return (ClusterItem)clusterItems.get(position);
  }

  public int getNumDataPoint() {
    return clusterItems.size();
  }

}
