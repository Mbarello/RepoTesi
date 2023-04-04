package clustering;

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
public class ClusterItem {
  private Object dataPoint;
  private int cluster;
  private double distance;

  public ClusterItem() {
    dataPoint= new Object();
    cluster= 0;
    distance= 0;
  }

  public ClusterItem(Object datapoint, int cluster, double distance) {
    this.dataPoint= datapoint;
    this.cluster= cluster;
    this.distance= distance;
  }

  public void setDataPoint (Object datapoint) {
    dataPoint= datapoint;
  }

  public void setCluster (int clust) {
    cluster= clust;
  }

  public void setDistance (double dist) {
    distance= dist;
  }

  public Object getDataPoint () {
    return dataPoint;
  }

  public int getCluster () {
    return cluster;
  }

  public double getDistance () {
    return distance;
  }

}
