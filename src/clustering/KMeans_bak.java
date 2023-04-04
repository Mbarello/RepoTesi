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

public class KMeans_bak {
  private DataPointArray items;
  private ArrayList centroids;
  private KDistance distance;
  private int KValue;

  public KMeans_bak() {
    items= new DataPointArray();
    centroids= new ArrayList();
  }

  public KMeans_bak(DataPointArray Items, KDistance Distance) {
    items= Items;
    distance= Distance;
  }

  public void setKDistance (KDistance Distance) {
    distance= Distance;
  }

  public void addItem (Object newObject) {
    ClusterItem newClusterItem = new ClusterItem(newObject, 0, 0);
    items.addDataPoint(newClusterItem);
  }

  public void addItem (Object newObject, int position) {
    ClusterItem newClusterItem = new ClusterItem(newObject, 0, 0);
    items.addDataPoint(newClusterItem, position);
  }

  public void setItem (Object newObject, int position) {
    ClusterItem newClusterItem = new ClusterItem(newObject, 0, 0);
    items.setDataPoint(newClusterItem, position);
  }

  public Object getItem (int position) {
    return items.getDataPoint(position).getDataPoint();
  }

  public int getItemCluster (int position) {
    return items.getDataPoint(position).getCluster();
  }

  public double getItemDistance (int position) {
    return items.getDataPoint(position).getDistance();
  }

  public void calculate (int k, int[] weights) {
    boolean centroidsChanged = true;
    int I, K, nextCentroid, nearestCentroid, iteration;
    java.util.Random rnd = new java.util.Random();
    double distanzaCorrente, distanzaMinima;
    ClusterItem itemCorrente;
    Cluster[] clusters;

    // Genera un array di K centroids M1..Mk scelti in modo casuale tra tutte le items

    KValue= k;

    clusters= new Cluster[KValue];

    double M[] = new double[KValue];
    int N[] = new int[KValue];
    double DIST[] = new double[KValue];
    ClusterItem CM[] = new ClusterItem[KValue];
    int numCentroids[] = new int[KValue];

    centroids= new ArrayList(KValue);
    for (I= 0; I < KValue; I++) {
      nextCentroid= rnd.nextInt(items.getNumDataPoint());
      clusters[I].setCentroid(items.getDataPoint(nextCentroid));

//      centroids.add(I, items.getDataPoint(nextCentroid));
      numCentroids[I]= nextCentroid;
    }

    // Mentre almeno una centroide � cambiato:

    iteration= 1;
    while (centroidsChanged) {
      iteration++;
      System.out.println("Iterazione: " + iteration);

      System.out.print("Centroidi correnti: ");
      for (I= 0; I < KValue; I++) {
        System.out.print(numCentroids[I] + "   ");
      }
      System.out.println();


        //     Resetta i cluster correnti
        //     Nessun centroide � cambiato

      centroidsChanged= false;

      //     Per ogni item I:
      //         Calcola la distanza tra I e ogni centroide
      //         Assegna I al cluster del centroide di distanza minore

      for (I= 0; I < items.getNumDataPoint(); I++) {
        distanzaMinima= 1;
        nearestCentroid= 0;

        for (K= 0; K < KValue; K++) {
          distanzaCorrente= distance.getDistance(items.getDataPoint(I), clusters[K].getCentroid(), weights);

//          distanzaCorrente= distance.getDistance(items.getDataPoint(I), (ClusterItem)centroids.get(K), weights);

          if (distanzaCorrente < distanzaMinima) {
            distanzaMinima= distanzaCorrente;
            nearestCentroid= K;
          }
        }

        items.getDataPoint(I).setCluster(nearestCentroid);
        items.getDataPoint(I).setDistance(distanzaMinima);

        clusters[nearestCentroid].addItem(items.getDataPoint(I));
      }

      //     Per ogni cluster K:
      //         Calcolo la media di tutte le distanze dal centroide
      //         Il centroide candidato Ck � quello a distanza minore rispetto alla media
      //         se Ck <> Mk, allora Mk= Ck, e almeno un centroide � cambiato

      //         Calcolo la media di tutte le distanze dal centroide


      //     Per ogni cluster K:
      //         Calcolo il nuovo centroide Ck, rappresentativo delle items nel cluster
      //         se Ck <> Mk, allora Mk= Ck, e almeno un centroide e' cambiato

      //         Calcolo la media di tutte le distanze dal centroide

      for (K= 0; K < KValue; K++) {
        M[K]= 0.0;
        N[K]= 0;
      }

      for (I= 0; I < items.getNumDataPoint(); I++) {
        itemCorrente= items.getDataPoint(I);
        M[itemCorrente.getCluster()]+= itemCorrente.getDistance();
        N[itemCorrente.getCluster()]++;
      }

      for (K= 0; K < KValue; K++) {
        if (N[K] != 0) M[K]= M[K] / N[K];
      }

      //         Il centroide candidato Ck � quello a distanza minore rispetto alla media

      for (K= 0; K < KValue; K++) {
        DIST[K]= 1.0;
        CM[K]= null;
      }

      for (I= 0; I < items.getNumDataPoint(); I++) {
        itemCorrente= items.getDataPoint(I);
        distanzaCorrente= java.lang.Math.abs(itemCorrente.getDistance() -
                                             M[itemCorrente.getCluster()]);

        if (distanzaCorrente < DIST[itemCorrente.getCluster()]) {
          DIST[itemCorrente.getCluster()]= distanzaCorrente;
          CM[itemCorrente.getCluster()]= itemCorrente;
          numCentroids[itemCorrente.getCluster()]= I;
        }
      }

      //         se Ck <> Mk, allora Mk= Ck, e almeno un centroide � cambiato

      for (K= 0; K < KValue; K++) {
        if (CM[K] != null) {
          itemCorrente= (ClusterItem)centroids.get(K);
          if (CM[K] != itemCorrente) {
            centroids.add(K, CM[K]);
            centroidsChanged= true;
          }
        }
      }
    }
  }

}
