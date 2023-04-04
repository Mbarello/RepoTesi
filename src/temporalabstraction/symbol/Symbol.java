package temporalabstraction.symbol;

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

import java.awt.*;

public class Symbol {
  int signal;
  String symbol;
  String description;
  Color color;

  public Symbol() {
    signal= 0;
    symbol= new String ();
    description= new String ();
  }

  public Symbol(int Signal, String SSymbol, String Descr, float colorR, float colorG, float colorB) {
    signal= Signal;
    symbol= SSymbol;
    description= Descr;
    color= new Color(colorR, colorG, colorB);
  }

  public Symbol(int Signal, String SSymbol, String Descr, Color ccolor) {
    signal= Signal;
    symbol= SSymbol;
    description= Descr;
    color= ccolor;
  }

  public void setSignal(int Signal) {
    signal= Signal;
  }

  public void setSymbol(String SSymbol) {
    symbol= SSymbol;
  }

  public void setDescription(String Descr) {
    description= Descr;
  }

  public void setColor(float colorR, float colorG, float colorB) {
    color= new Color(colorR, colorG, colorB);
  }

  public int getSignal() {
    return signal;
  }

  public String getSymbol() {
    return symbol;
  }

  public String getDescription() {
    return description;
  }

  public Color getColor () {
    return color;
  }

  public float getColorR () {
    return color.getRed();
  }

  public float getColorG () {
    return color.getGreen();
  }

  public float getColorB () {
    return color.getBlue();
  }

}
