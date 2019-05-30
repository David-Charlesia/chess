import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.JOptionPane;

public class IHM extends JFrame
{
  //private JButton[63] plateau;
  Echequier e;
  Buttonv2[] bt_tab=new Buttonv2[64];
  int bt_select=0;
  Buttonv2 bt_before;
  Piece p_select;
  JPanel jp_centre;
  BoutonListener blis=new BoutonListener();

  public IHM(Echequier e)
  {
    super("Echec");
    this.e=e;
    this.setLayout(new BorderLayout());
    getPanelCentre();
    this.add(jp_centre,BorderLayout.CENTER);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setBounds(300,300,600,400);
    this.setVisible(true);
  }

  public Buttonv2 init_bt(String name, Piece p,int x,int y)
  {
    Buttonv2 bt=new Buttonv2(name,p,x,y);
    bt.addActionListener(blis);

    bt_tab[x+y*8]=bt;

    return bt;
  }

  public void getPanelCentre()
  {
    jp_centre=new JPanel();
    jp_centre.setLayout(new GridLayout(8,8));
    Buttonv2 bt;

    for(int y=0;y<8;y++)
    {
      for(int x=0;x<8;x++)
      {
        if(e.get_case(x,y)==null)
        {
          bt=new Buttonv2(" ",null,x,y);
        }else
        {
          bt=new Buttonv2(e.get_case(x,y).toString(),e.get_case(x,y),x,y);
        }


        bt.addActionListener(blis);

        bt_tab[x+y*8]=bt;

        jp_centre.add(bt);
      }
    }

    init_backcolor_bt();
  }

  public void init_backcolor_bt()
  {
    int couleur=1;
    for(int i=0;i<64;i++)
    {
      if(couleur==1)
      {
        if(i%8==0)
        {
          bt_tab[i].setBackground(Color.WHITE);
          bt_tab[i].setForeground(Color.BLACK);
          couleur=1;
        }else
        {
          bt_tab[i].setBackground(Color.BLACK);
          bt_tab[i].setForeground(Color.WHITE);
          couleur=0;
        }
      }else
      {
        if(i%8==0)
        {
          bt_tab[i].setBackground(Color.BLACK);
          bt_tab[i].setForeground(Color.WHITE);
          couleur=0;
        }else
        {
          bt_tab[i].setBackground(Color.WHITE);
          bt_tab[i].setForeground(Color.BLACK);
          couleur=1;
        }
      }
    }
  }

  class BoutonListener implements ActionListener
  {

    public void actionPerformed(ActionEvent ae)
    {
      //------------------------------
      //ajouter switch case ici :


      //------------------------------
      Buttonv2 b=(Buttonv2)ae.getSource();//bouton qui déclenche l'action
      if(bt_select==0)//si aucun bouton n'est sélectionner
      {
        Piece p=b.getPieceButton();//return la piece du bouton qui déclenche l'action
        p_select=p;//met dans p_select la piece p

        ArrayList<Integer> tab=e.all_mouv_possible(p);
        //Iterator<Integer> ite=tab.iterator();
        //int i;
        Buttonv2 bt;

        for(int i:tab)
        {
          bt=bt_tab[i];
          bt.setBackground(Color.BLUE);
        }
        if(tab.size()>0)
        {
          bt_select=1;
        }
        bt_before=b;
      }else//(bt_select==1)
      {
        if(b.equals(bt_before))
        {
          bt_select=0;
          init_backcolor_bt();
        }else if(e.bouger_ok(p_select,b.get_x(),b.get_y()))
        {
          e.bouger(p_select,b.get_x(),b.get_y());
          init_backcolor_bt();
          bt_select=0;

          //b=init_bt(p_select.toString(),p_select,p_select.get_x(),p_select.get_y());
          //getPanelCentre();

          b.actu_bt(p_select.toString(),p_select);
          bt_before.actu_bt(" ",null);

          //jp_centre.updateUI();


        }
      }

    }
  }

  class Buttonv2 extends JButton
  {
    private Piece p;
    private int x;
    private int y;

    public Buttonv2(String name, Piece p,int x,int y)
    {
        super(name);
        this.p=p;
        this.x=x;
        this.y=y;
        //this.nb=x+y*8;
    }

    public void actu_bt(String name, Piece p)
    {
      this.setText(name);
      this.p=p;
    }

    public void set_p_bt(Piece p)
    {
      this.p=p;
    }

    public void set_x_bt(int x)
    {
      this.x=x;
    }

    public void set_y_bt(int y)
    {
      this.y=y;
    }

    public Piece getPieceButton()
    {
        return this.p;
    }


    public int get_x()
    {
      return this.x;
    }
    public int get_y()
    {
      return this.y;
    }

    /*
    public int get_nb(int nb)
    {
      return this.nb;
    }
    */
}


}
