package retrieval;

import loader.DBConnection;
import indexing.*;

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

import java.util.Vector;
import java.sql.SQLException;

public class TClassificator {
  int K;

  int eta_min, eta_max;
  char sex;

  float weightloss, dryweight, dialysistimeduration;
  String addfarmtreatment, vascularaccess;
  char outcome;

  TLinkedList risultati;
  DBConnection db;

  public TClassificator(DBConnection db) {
    this.db= db;

    eta_min= eta_max= -1;
    sex= 'N';

    weightloss= dryweight= dialysistimeduration= -1;
    addfarmtreatment= ""; vascularaccess= "";
    outcome= 'N';

    risultati= new TLinkedList();
  }

  private void estrazione_K () {
    String sqlStatement, optionEta, optionSex;
    double DMAX = -1;
    int dimL = 0;

    risultati.init();

    //  Lancio della Query e recupero dei risultati

    sqlStatement = "Select r_patient.*, r_ssession.* from (r_patient natural join r_ssession)";
    optionEta = "";
    optionSex = "";

    if (eta_min>= 0) {
      optionEta = " where r_patient.birth_date >= '01/01/" + eta_min + "' and r_patient.birth_date <= '12/31/" + eta_max+"'";
    }
    if (sex != ' ') {
      optionSex = "(r_patient.sex)::text = ('" + sex + "')::text";
      if (optionEta.compareTo("") != 0) optionSex = " and " + optionSex;
      else optionSex = " where " + optionSex;
    }
    sqlStatement = sqlStatement + optionEta + optionSex;

    try {
      db.RunQuery(sqlStatement);

      System.out.println("\n Dati Estratti dalla query : ");
      while(db.nextRecord()) {
        System.out.println(db.getRowValueInt("patientid") + " " + db.getRowValueInt("ssessionid")
                           + " " + db.getRowValueString("sex") + " " + db.getRowValueString("weigthloss"));
        double d= calcola_distanza();
        if ((DMAX == -1) || (d < DMAX) || (dimL < K)) {
          DMAX= risultati.insertSortedAndTrunc(null,db.getRowValueInt("patientid"),db.getRowValueInt("ssessionid"), 0, d, K);
          if (dimL < K) dimL++;
        }
      }
      System.out.println("\n Casi Classificati : ");
      TResultNode nodo = risultati.getFirst();
      while (nodo != null) {
        System.out.println("  Paziente : " + nodo.getLinkPaziente() +
                           " - Sessione : " + nodo.getLinkDialisi());
        nodo= nodo.getNext();
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  private double calcola_distanza(){
    double d=0;

    if (weightloss!=-1.0)
      d+=Math.pow((db.getRowValueDouble("weigthloss")-weightloss),2);
    if (dryweight!=-1.0)
      d+=Math.pow((db.getRowValueDouble("dryweight")-dryweight),2);
    if (dialysistimeduration!=-1.0)
      d+=Math.pow((db.getRowValueDouble("dialysistimeduration")-dialysistimeduration),2);

    if (!(addfarmtreatment.equals("")))
      d+=distanza_addfarmtreatment(db.getRowValueString("addfarmtreatment"));
    if (!(vascularaccess.equals("")))
      d+=distanza_vascularaccess(db.getRowValueString("vascularaccess"));
    if (outcome != ' ')
      d+=distanza_outcome(db.getRowValueChar("outcome"));

    return d;
  }


 public double distanza_addfarmtreatment(String s){
   if (s.equals(addfarmtreatment)) return 0;
   return 1;
 }

 public double distanza_vascularaccess(String s){
   if (s.equals(vascularaccess)) return 0;
   return 1;
 }

 public double distanza_outcome(char c){
   if (c == outcome) return 0;
   if (c == '0') {
     if (outcome == '1') return 0.3;
     if (outcome == '2') return 1;
   }

   if (c == '1') {
     if (outcome == '0') return 0.3;
     if (outcome == '2') return 0.7;
   }

   if (c == '2') {
     if (outcome == '1') return 0.7;
     if (outcome == '0') return 1;
   }

   return 0;
 }

  private void visitaConMarcatura (TNodo nodo) {
    if (nodo != null) {
      nodo.classificato= risultati.searchKey(nodo.getLinkPaziente(), nodo.getLinkDialisi());
      visitaConMarcatura (nodo.left);
      visitaConMarcatura (nodo.right);
    }
  }

  private void marcatura (TKDTree[] kdTree) {
    int currentTree;
    TNodo root;

    for (int i= 0; i < kdTree.length; i++) {
      if (kdTree[i] != null) {
        root = kdTree[i].getRoot();
        visitaConMarcatura(root);
      }
    }
  }

  private void visitaConMarcatura (TTVNode nodo) {
    if (nodo != null) {
      nodo.classificato= risultati.searchKey(nodo.getLinkPaziente(), nodo.getLinkDialisi());
      for (int figli= 0; figli < nodo.getLengthChilds(); figli++)
        visitaConMarcatura (nodo.getChild(figli));
    }
  }

  private void marcatura (TTVTree[] tvTree) {
    int currentTree;
    TTVNode root;

    for (int i= 0; i < tvTree.length; i++) {
      if (tvTree[i] != null) {
        root = tvTree[i].getRoot();
        visitaConMarcatura(root);
      }
    }
  }

  public void setParameters (int eta_min, int eta_max, char sex,
                             float weightloss, float dryweight, float dialysistimeduration,
                             String addfarmtreatment, String vascularaccess,
                             char outcome) {
    this.eta_min= eta_min;
    this.eta_max= eta_max;
    this.sex= sex;

    this.weightloss= weightloss;
    this.dryweight= dryweight;
    this.dialysistimeduration= dialysistimeduration;
    this.addfarmtreatment= addfarmtreatment;
    this.vascularaccess= vascularaccess;
    this.outcome= outcome;
  }

  public void calculate (int K, TKDTree[] kdTree, TTVTree[] tvTree) {
    this.K= K;

    estrazione_K ();
    marcatura (kdTree);
    marcatura (tvTree);
  }
}
