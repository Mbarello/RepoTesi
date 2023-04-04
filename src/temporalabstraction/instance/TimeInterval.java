package temporalabstraction.instance;

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

import java.sql.Timestamp;

public class TimeInterval {
  Timestamp start;
  Timestamp end;

  public TimeInterval() {
    start = new Timestamp (0);
    end = new Timestamp (0);
  }

  public TimeInterval(long Start, long End) {
    start = new Timestamp (Start);
    end = new Timestamp (End);
  }

  public void setTime(long Start, long End) {
    start.setTime(Start);
    end.setTime(End);
  }

  public long getStart (){
    return start.getTime();
  }

  public int getStartH () {
    int startH = (int)(this.getStart() / 3600000);

    return startH;
  }

  public int getStartM () {
    int startM = (int)((this.getStart() / 1000) - ((long)this.getStartH() * 3600));

    return (int)(startM / 60);
  }

  public int getStartS () {
    int startS = (int)((this.getStart() / 1000) - ((long)this.getStartH() * 3600));

    return (startS - (this.getStartM() * 60));
  }

  public long getEnd (){
    return end.getTime();
  }

  public int getEndH () {
    int startH = (int)(this.getEnd() / 3600000);

    return startH;
  }

  public int getEndM () {
    int startM = (int)((this.getEnd() / 1000) - ((long)this.getEndH() * 3600));

    return (int)(startM / 60);
  }

  public int getEndS () {
    int startS = (int)((this.getEnd() / 1000) - ((long)this.getEndH() * 3600));

    return (startS - (this.getEndM() * 60));
  }

  public Timestamp getStartStamp (){
    return start;
  }

  public Timestamp getEndStamp (){
    return end;
  }

  public long length(){
    return (this.getEnd() - this.getStart());
  }

  public int overlaps (TimeInterval ti, double maxOverlapAllowed) {
    // valori di ritorno:
    // 0: no overlap: intervalli disgiunti
    // 1: overlap parziale accettabile
    // 2: overlap parziale non accettabile
    // 3: overlap totale -> non accettabile
    TimeInterval INT = this.intersect(ti);

    // Se gli intervalli son disgiunti non c'e overlap
    if (INT.length() <= 0) return 0;

    // Se l'overlap e completo, allora non e accettabile
    if ((INT.getStart() >= ti.getStart()) && (INT.getStart() >= this.getStart())
       && (INT.getEnd() <= ti.getEnd()) && (INT.getEnd() <= this.getEnd()))
       return 3;

    // Se l'overlap parziale supera la soglia, allora non e accettabile
    if (INT.length() > maxOverlapAllowed) return 2;

    // L'overlap parziale e accettabile
    return 1;
  }

  public TimeInterval intersect (TimeInterval ti){
    long Start = ti.getStart();
    if (this.getStart() > Start) Start = this.getStart();

    long End = ti.getEnd();
    if (this.getEnd() < End) End = this.getEnd();

    TimeInterval result = new TimeInterval(Start, End);
    return result;
  }

}
