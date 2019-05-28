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
    this.setBounds(300,300,600,400);
    this.setVisible(true);
  }

  public JPanel getPanelCentre(Echequier e)
  {
    JPanel jp=new JPanel();
    jp.setLayout(new GridLayout(8,8));
    JButton bt;
    BoutonListener blis=new BoutonListener();
    int couleur=1;
    for(int i=0;i<64;i++)
    {
      if(e.get_case(i)==null)
      {
        bt=new JButton(" ");
      }else
      {
        bt=new JButton(e.get_case(i).toString());
      }

      if(couleur==1)
      {
        if(i%8==0)
        {
          bt.setBackground(Color.WHITE);
          bt.setForeground(Color.BLACK);
          couleur=1;
        }else
        {
          bt.setBackground(Color.BLACK);
          bt.setForeground(Color.WHITE);
          couleur=0;
        }
      }else
      {
        if(i%8==0)
        {
          bt.setBackground(Color.BLACK);
          bt.setForeground(Color.WHITE);
          couleur=0;
        }else
        {
          bt.setBackground(Color.WHITE);
          bt.setForeground(Color.BLACK);
          couleur=1;
        }
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
