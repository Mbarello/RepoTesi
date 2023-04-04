package temporalabstraction.tamodule;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * @author Giorgio Leonardi
 */

import java.util.*;

public class SICollection {
  int numSegnali;
  int idPaziente, idDialisi;
  SignalInstance arSignal[];
  checkInstances checkT[];
  checkInstances checkS[];

  public SICollection(int nsegnali, int paziente, int dialisi)
  {
    int i;
    numSegnali= nsegnali;
    idPaziente= paziente;
    idDialisi= dialisi;
    arSignal= new SignalInstance[numSegnali];
    checkT = new checkInstances[numSegnali];
    checkS = new checkInstances[numSegnali];

    for (i= 0; i < arSignal.length; i++) arSignal[i] = new SignalInstance (i);
    for (i= 0; i < checkT.length; i++) checkT[i] = new checkInstances ();
    for (i= 0; i < checkS.length; i++) checkS[i] = new checkInstances ();
  }

  public int getIdPaziente () {
    return idPaziente;
  }

  public int getIdDialisi () {
    return idDialisi;
  }

  public int getNumSignals () {
    return numSegnali;
  }

  public void setSignalInstance(int I, SignalInstance Inst) {
    arSignal[I]= Inst;
  }

  public SignalInstance getSignalInstance(int I) {
    return arSignal[I];
  }

  public checkInstances getTrendInstances(int I) {
    return checkT[I];
  }

  public checkInstances getStateInstances(int I) {
    return checkS[I];
  }

}
