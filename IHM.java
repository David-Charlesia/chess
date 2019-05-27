import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.JOptionPane;

public class IHM extends JFrame
{
  //private JButton[63] plateau;

  public IHM(Echequier e)
  {
    super("Echec");
    this.setLayout(new BorderLayout());
    this.add(this.getPanelCentre(e),BorderLayout.CENTER);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setVisible(true);
  }

  public JPanel getPanelCentre(Echequier e)
  {
    JPanel jp=new JPanel();
    JButton bt;
    BoutonListener blis=new BoutonListener();

    for(int i=0;i<64;i++)
    {
      if(e.get_case(i)==null)
      {
        bt=new JButton(" ");
      }else
      {
        bt=new JButton(e.get_case(i).toString());
      }
      bt.addActionListener(blis);

      jp.add(bt);
    }

    return jp;
  }

  class BoutonListener implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {}
  }


}
