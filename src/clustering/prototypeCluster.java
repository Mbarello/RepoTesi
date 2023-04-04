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
public class prototypeCluster implements Cluster {
  private ClusterItem centroid;
  private DataPointArray items;

  public prototypeCluster() {
    centroid= new ClusterItem();
    items= new DataPointArray();
  }

  public prototypeCluster(ClusterItem Centroid, DataPointArray Items) {
    centroid= Centroid;
    items= Items;
  }

  public void initItems() {
    items = new DataPointArray();
  }

  public void setCentroid (ClusterItem Centroid) {
    centroid= Centroid;
  }

  public ClusterItem getCentroid () {
    return centroid;
  }

  public void addItem (ClusterItem Item) {
    items.addDataPoint(Item);
  }

  public void setItem (ClusterItem Item, int N) {
    items.setDataPoint(Item, N);
  }

  public ClusterItem getItem (int N) {
    return items.getDataPoint(N);
  }

  public int getNumItems () {
    return items.getNumDataPoint();
  }

  public ClusterItem calculateNewCentroid () {
    ClusterItem newCentroid = new ClusterItem();

    // da sovraccaricare: si calcolano qui le feature del nuovo centroide
    // del cluster corrente e si restituisce il nuovo centroide

    return newCentroid;
  }

  public boolean equalsCentroid (ClusterItem Centroid2) {

      // da sovraccaricare: si ritorna il fatto che il centroide corrente
      // sia uguale (true) o diverso (false) dal centroide Centroid2

    return true;
  }

}
