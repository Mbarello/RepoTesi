package temporalabstraction.expected;

import temporalabstraction.symbol.*;

import java.sql.Timestamp;/**
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
public class ExpectedJoint extends ExpectedSymbol {
  JointSymbol symbol;

  public ExpectedJoint() {
    symbol= new JointSymbol();
    minimumDuration= new Timestamp(0);
    maximumDuration= new Timestamp(0);
  }

  public ExpectedJoint(JointSymbol ssymbol, long min, long max) {
    symbol= ssymbol;
    minimumDuration= new Timestamp(min);
    maximumDuration= new Timestamp(max);
  }

  public JointSymbol getJointSymbol() {
    return symbol;
  }

}
