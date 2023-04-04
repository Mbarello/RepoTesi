package testclustering;

import clustering.*;

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
public class testCluster extends prototypeCluster {
  public testCluster() {
    super();
  }

  public testCluster(ClusterItem Centroid, DataPointArray Items) {
    super (Centroid, Items);
  }

  public ClusterItem calculateNewCentroid () {
    ClusterItem newCentroid = new ClusterItem();
    int I, K, numPunti;
    testDataPoint currentDataPoint;
    double[] means;

    currentDataPoint= (testDataPoint)this.getCentroid().getDataPoint();
    numPunti= currentDataPoint.getNumPoints();

    means = new double[numPunti];

    for (I= 0; I < numPunti; I++) {
      means[I]= 0.0;
    }

    for (I= 0; I < this.getNumItems(); I++) {
      currentDataPoint= (testDataPoint)this.getItem(I).getDataPoint();
      for (K= 0; K < numPunti; K++) {
        means[K]+= currentDataPoint.getPoint(K);
      }
    }

    for (K= 0; K < numPunti; K++) {
      means[K]= means[K] / this.getNumItems();
    }

    currentDataPoint= new testDataPoint(numPunti);
    for (K= 0; K < numPunti; K++) {
      currentDataPoint.setPoint(K, means[K]);
    }

    newCentroid = new ClusterItem(currentDataPoint, 0, 0);

    return newCentroid;
  }

  public boolean equalsCentroid (ClusterItem Centroid2) {
    testDataPoint point1, point2;
    boolean result = true;
    int I, numpunti;

    point1= (testDataPoint)this.getCentroid().getDataPoint();
    point2= (testDataPoint)Centroid2.getDataPoint();
    numpunti= point1.getNumPoints();

    for (I = 0; I < numpunti; I++) {
      if (point1.getPoint(I) != point2.getPoint(I)) result= false;
    }

    return result;
  }

}
