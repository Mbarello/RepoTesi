package visual;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: 2003</p>
 * <p>Company: </p>
 * @author Giorgio Leonardi
 * @version 1.0
 */

public class TMessageDialog extends JDialog {
  public TMessageDialog(Frame parent, String title, boolean modal, String text[],
                        String buttons[], WindowListener wh, ActionListener bh) {
    super (parent, title, modal);
    int textLines = text.length;
    int numButtons = buttons.length;
    JPanel textPanel = new JPanel ();
    JPanel buttonPanel = new JPanel();
    textPanel.setLayout(new GridLayout (textLines, 1));
    for (int i= 0; i < textLines; i++) textPanel.add (new JLabel (text[i]));
    for (int i= 0; i < numButtons; i++) {
      JButton b = new JButton (buttons[i]);
      b.addActionListener(bh);
      buttonPanel.add(b);
    }

    getContentPane().add ("North", textPanel);
    getContentPane().add ("South", buttonPanel);
    pack();
    addWindowListener (wh);
  }

}
