package loader;

import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: 2003</p>
 * <p>Company: </p>
 * @author Giorgio Leonardi
 * @version 1.0
 */


public class TXBase {

  class fieldHeader {
    public String fieldName;
    public char dataType;
    public int displacement;
    public int length;
    public byte decimal;
  }

  RandomAccessFile dbf;
  String fileName, access;

  int dbfType;
  int dbfLastUpdateYear;
  int dbfLastUpdateMonth;
  int dbfLastUpdateDay;
  int dbfNumberRecs;
  int dbfDataPosition;
  int dbfDataLength;
  int dbfNumberFields;
  fieldHeader[] dbfFields;
  private int curRec = 1;

  int bufferDialisi, bufferSegnale, bufferTStamp;
  double bufferValue;

  public TXBase (String FileName, String Access) {
    fileName= FileName;
    access= Access;
  }

  public int readBackwardsInt () throws EOFException, IOException {
    int ch4 = dbf.read();
    int ch3 = dbf.read();
    int ch2 = dbf.read();
    int ch1 = dbf.read();
    if ((ch1 | ch2 | ch3 | ch4) < 0)
      throw new EOFException();
    int val = ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));

    return val;
  }

  public int readBackwardsUnsignedShort () throws EOFException, IOException {
    int ch2 = dbf.read();
    int ch1 = dbf.read();
    if ((ch1 | ch2) < 0)
      throw new EOFException();
    int val = (ch1 << 8) + (ch2 << 0);

    return val;
  }

  public void use () throws IOException {
    // Apertura dell'archivio
    dbf = new RandomAccessFile (fileName, access);
    // Informazioni dell'header
    dbfType=dbf.readUnsignedByte();
    dbfLastUpdateYear =(byte)dbf.read();
    dbfLastUpdateMonth=(byte)dbf.read();
    dbfLastUpdateDay  =(byte)dbf.read();
    dbfNumberRecs     =readBackwardsInt();
    dbfDataPosition   =readBackwardsUnsignedShort();
    dbfDataLength     =readBackwardsUnsignedShort();
    dbfNumberFields   =(dbfDataPosition-33)/32;
    dbf.seek(32);

    // Struttura campi
    byte fieldNameBuffer[] = new byte[11];
    int  locn=0;

    //Il primo campo e' il campo di deleted. Non ? in struttura, ma
    //esiste e se contiene '*' il record e' cancellato, se ' ' il record
    //e' valido
    dbfFields = new fieldHeader[dbfNumberFields+1];
    dbfFields[0] = new fieldHeader();
    dbfFields[0].fieldName="@DELETED@";
    dbfFields[0].dataType='C';
    dbfFields[0].displacement=0;
    locn+= (dbfFields[0].length=1);
    dbfFields[0].decimal=0;

    // Ciclo di lettura dei campi
    for (int i=1; i<=dbfNumberFields; i++) {
      dbfFields[i] = new fieldHeader();
      // Nome
      dbf.read(fieldNameBuffer);
      dbfFields[i].fieldName= new String(fieldNameBuffer);
      int posZero = dbfFields[i].fieldName.indexOf( 0 );
      dbfFields[i].fieldName = dbfFields[i].fieldName.substring(0,posZero);
      // Tipo
      dbfFields[i].dataType=(char)dbf.read();
      // Lunghezza
      dbf.skipBytes(4);
      dbfFields[i].displacement=locn;
      locn+=(dbfFields[i].length=dbf.read());
      // Decimali
      if( dbfFields[i].dataType=='N' )
        dbfFields[i].decimal=(byte)dbf.read();
      else {
        dbfFields[i].decimal = 0;
        int len = (byte)dbf.read();
        dbfFields[i].length += len*256;
      }
      dbf.skipBytes(14);
    }
  }

  public void close () throws IOException {
    dbf.close();
  }

  public int getCurrentRecord () {
    return curRec;
  }

  public int getNumRecords () {
    return (dbfNumberRecs + 1);
  }

  public void first () {
    curRec= 1;
  }

  public void last () {
    curRec= dbfNumberRecs;
  }

  public void next () {
    curRec++;
    if (curRec > dbfNumberRecs + 1)
      curRec= dbfNumberRecs + 1;
  }

  public void previous () {
    curRec--;
    if (curRec < 1)
      curRec= 1;
  }

  public void goTo (int n) {
    curRec= n;
    if (curRec > dbfNumberRecs + 1)
      curRec= dbfNumberRecs + 1;
    if (curRec < 1)
      curRec= 1;
  }

  public String getStringField (int Field) throws IOException {
    byte dataBuffer[] = new byte[dbfFields[Field].length];

    dbf.seek(dbfDataPosition + ((curRec - 1) * dbfDataLength) + dbfFields[Field].displacement);
    dbf.read(dataBuffer);
    String valore = new String(dataBuffer);

    return valore;
  }

  public int getIntegerField (int Field) throws IOException {
    byte dataBuffer[] = new byte[dbfFields[Field].length];

    dbf.seek(dbfDataPosition + ((curRec - 1) * dbfDataLength) + dbfFields[Field].displacement);
    dbf.read(dataBuffer);
    String valore = new String(dataBuffer);

    return (int)(Double.parseDouble(valore));
  }

  public double getDoubleField (int Field) throws IOException {
    byte dataBuffer[] = new byte[dbfFields[Field].length];

    dbf.seek(dbfDataPosition + ((curRec - 1) * dbfDataLength) + dbfFields[Field].displacement);
    dbf.read(dataBuffer);
    String valore = new String(dataBuffer);

    return Double.parseDouble(valore);
  }

  public void firstReadLine () throws IOException {
    byte dataBuffer1[] = new byte[dbfFields[1].length];
    byte dataBuffer2[] = new byte[dbfFields[2].length];
    byte dataBuffer3[] = new byte[dbfFields[3].length];
    byte dataBuffer4[] = new byte[dbfFields[4].length];
    String valore;

    dbf.seek(dbfDataPosition + ((curRec - 1) * dbfDataLength) + dbfFields[1].displacement);

    dbf.read(dataBuffer1);
    valore = new String(dataBuffer1);
    bufferDialisi= (int)(Double.parseDouble(valore));

    dbf.read(dataBuffer2);
    valore = new String(dataBuffer2);
    bufferSegnale= (int)(Double.parseDouble(valore));

    dbf.read(dataBuffer3);
    valore = new String(dataBuffer3);
    bufferTStamp= (int)(Double.parseDouble(valore));

    dbf.read(dataBuffer4);
    valore = new String(dataBuffer4);
    bufferValue= Double.parseDouble(valore);

    curRec++;
  }

  public void nextReadLine () throws IOException {
    byte dataDisplacement[] = new byte[dbfFields[1].displacement];
    byte dataBuffer1[] = new byte[dbfFields[1].length];
    byte dataBuffer2[] = new byte[dbfFields[2].length];
    byte dataBuffer3[] = new byte[dbfFields[3].length];
    byte dataBuffer4[] = new byte[dbfFields[4].length];
    String valore;

    dbf.read(dataDisplacement);

    dbf.read(dataBuffer1);
    valore = new String(dataBuffer1);
    bufferDialisi= (int)(Double.parseDouble(valore));

    dbf.read(dataBuffer2);
    valore = new String(dataBuffer2);
    bufferSegnale= (int)(Double.parseDouble(valore));

    dbf.read(dataBuffer3);
    valore = new String(dataBuffer3);
    bufferTStamp= (int)(Double.parseDouble(valore));

    dbf.read(dataBuffer4);
    valore = new String(dataBuffer4);
    bufferValue= (double)(Double.parseDouble(valore));

    curRec++;
  }

  public int getDialisi () {
    return bufferDialisi;
  }

  public int getSegnale () {
    return bufferSegnale;
  }

  public int getTStamp () {
    return bufferTStamp;
  }

  public double getValue () {
    return bufferValue;
  }

  public boolean eof () {
    return (curRec == dbfNumberRecs + 1);
  }

  public int search (double value, int field) throws IOException {
    int result= 0;
    boolean trovato= false;
    int precCurr= this.getCurrentRecord();

    this.first();
    while ((trovato == false) && (this.eof() == false)) {
      result++;
      this.goTo(result);
      if (this.getDoubleField(field) == value)
        trovato= true;
    }

    this.goTo(precCurr);

    if (trovato) return result;
    else return -1;
  }

  public int bSearch (double value, int field) throws IOException {
    int inf, sup, mid;
    boolean trovato;
    int precCurr= this.getCurrentRecord();
    double readValue;

    mid= 0;
    inf= 1;
    sup= this.getNumRecords ();
    trovato= false;

    while ((inf != sup) && (trovato == false)) {
      mid= (int)((inf + sup) / 2);
      this.goTo(mid);
      readValue= this.getDoubleField(field);

      if (readValue == value) trovato= true;
      else if (readValue < value) inf= mid + 1;
      else sup= mid;
    }

    this.goTo(precCurr);

    if (trovato)
      return mid;
    else return -1;
  }

}
