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
public class dataPointDistance implements KDistance {
  public double getDistance (ClusterItem object1, ClusterItem object2, int[] weights) {
    testDataPoint DP1, DP2;
    int I, weightSum;
    double distanza;

    DP1= (testDataPoint)object1.getDataPoint();
    DP2= (testDataPoint)object2.getDataPoint();

    distanza= 0.0;
    weightSum= 0;

    for (I= 0; I < DP1.getNumPoints(); I++) {
      distanza+= weights[I] * (java.lang.Math.abs((double)DP1.getPoint(I) - (double)DP2.getPoint(I)) / 100);
      weightSum+= weights[I];
    }

    distanza= distanza / weightSum;

    return distanza;
  }
}
