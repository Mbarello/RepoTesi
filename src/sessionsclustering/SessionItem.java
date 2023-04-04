package sessionsclustering;

import temporalabstraction.instance.TimeInterval;

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
public class SessionItem {
  // id univoco dell'item

  int idPaziente, idSessione;

  // features patient-related

  int eta;
  char sesso;
  int pesoIniziale;

  // features session-related

  int pesoFinale;
  int caloPesoTotale;
  int caloPeso;
  int codiceMacchina;
  TimeInterval durata;
  String trattamento;
  char outcome;
  double pressioneVenosa;
  double pressioneArteriosa;
  double flusso;
  double pressioneSistolica;
  double pressioneDiastolica;
  double frequenzaCardiaca;
  double hb;
  double ve;
  double conducibilita;
  double qf;
  double tmp;

  public SessionItem() {
  }

  public SessionItem(int idpaz, int idsess) {
    idPaziente= idpaz;
    idSessione= idsess;
  }


  // metodi SET

  public void setIdPaziente(int idpaz) {
    idPaziente= idpaz;
  }

  public void setIdSessione(int idsess) {
    idSessione= idsess;
  }

  public void setEta(int et) {
    eta= et;
  }

  public void setSesso(char sex) {
    sesso= sex;
  }

  public void setPesoIniziale(int peso) {
    pesoIniziale= peso;
  }

  public void setPesoFinale(int peso) {
    pesoFinale= peso;
  }

  public void setCaloPesoTotale(int peso) {
    caloPesoTotale= peso;
  }

  public void setCaloPeso(int peso) {
    caloPeso= peso;
  }

  public void setCodiceMacchina(int codice) {
    codiceMacchina= codice;
  }

  public void setDurata(TimeInterval dur) {
    durata= dur;
  }

  public void setTrattamento(String tratt) {
    trattamento= tratt;
  }

  public void setOutcome(char out) {
    outcome= out;
  }

  public void setPressioneVenosa(double press) {
    pressioneVenosa= press;
  }

  public void setPressioneArteriosa(double press) {
    pressioneArteriosa= press;
  }

  public void setFlusso(double press) {
    flusso= press;
  }

  public void setPressioneSistolica(double press) {
    pressioneSistolica= press;
  }

  public void setPressioneDiastolica(double press) {
    pressioneDiastolica= press;
  }

  public void setFrequenzaCardiaca(double press) {
    frequenzaCardiaca= press;
  }

  public void setHB(double press) {
    hb= press;
  }

  public void setVE(double press) {
    ve= press;
  }

  public void setConducibilita(double press) {
    conducibilita= press;
  }

  public void setQF(double press) {
    qf= press;
  }

  public void setTMP(double press) {
    tmp= press;
  }



  // metodi GET


  public int setIdPaziente() {
    return idPaziente;
  }

  public int setIdSessione() {
    return idSessione;
  }

  public int setEta() {
    return eta;
  }

  public char setSesso() {
    return sesso;
  }

  public int setPesoIniziale() {
    return pesoIniziale;
  }

  public int setPesoFinale() {
    return pesoFinale;
  }

  public int setCaloPesoTotale() {
    return caloPesoTotale;
  }

  public int setCaloPeso() {
    return caloPeso;
  }

  public int setCodiceMacchina() {
    return codiceMacchina;
  }

  public TimeInterval setDurata() {
    return durata;
  }

  public String setTrattamento() {
    return trattamento;
  }

  public char setOutcome() {
    return outcome;
  }

  public double setPressioneVenosa() {
    return pressioneVenosa;
  }

  public double setPressioneArteriosa() {
    return pressioneArteriosa;
  }

  public double setFlusso() {
    return flusso;
  }

  public double setPressioneSistolica() {
    return pressioneSistolica;
  }

  public double setPressioneDiastolica() {
    return pressioneDiastolica;
  }

  public double setFrequenzaCardiaca() {
    return frequenzaCardiaca;
  }

  public double setHB() {
    return hb;
  }

  public double setVE() {
    return ve;
  }

  public double setConducibilita() {
    return conducibilita;
  }

  public double setQF() {
    return qf;
  }

  public double setTMP() {
    return tmp;
  }

}
