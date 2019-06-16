import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.JOptionPane;
import java.awt.Image;
import javax.imageio.ImageIO;

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
  PromoListener plis=new PromoListener();
  Buttonv2 b_promo;
  JDialog jop;
  EndListener elis=new EndListener();

  public IHM(Echequier e)
  {
    super("Echec");
    this.e=e;
    this.setLayout(new BorderLayout());
    getPanelCentre();
    this.add(jp_centre,BorderLayout.CENTER);
    this.add(getPanelBouton(),BorderLayout.SOUTH);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setBounds(300,300,800,600);
    this.setVisible(true);
  }

  public Buttonv2 init_bt(String name, Piece p,int x,int y)
  {
    Buttonv2 bt=new Buttonv2(p.get_img(),p,x,y);
    bt.addActionListener(blis);

    bt_tab[x+y*8]=bt;

    return bt;
  }

  public JPanel getPanelBouton()
  {
    JPanel jp=new JPanel();

    JButton sauvegarder=new JButton("Sauvegarder");
    JButton charger=new JButton("Charger");

    BoutonSudListener bsl=new BoutonSudListener();

    sauvegarder.addActionListener(bsl);
    charger.addActionListener(bsl);

    jp.add(sauvegarder);
    jp.add(charger);

    return jp;
  }

  public void getPanelCentre()
  {
    try
    {
      jp_centre=new JPanel();
      jp_centre.setLayout(new GridLayout(8,8));
      Buttonv2 bt;

      for(int y=7;y>-1;y--)
      {
        for(int x=7;x>-1;x--)
        {
          if(e.get_case(x,y)==null)
          {
            bt=new Buttonv2(ImageIO.read(getClass().getResource("img/null.png")),null,x,y);
          }else
          {
            bt=new Buttonv2(e.get_case(x,y).get_img(),e.get_case(x,y),x,y);
          }


          bt.addActionListener(blis);

          bt_tab[x+y*8]=bt;

          jp_centre.add(bt);
        }
      }
      //init_img_size();
      init_backcolor_bt();
    }catch (Exception ex)
    {
      System.out.println(ex);
    }
  }

  public Buttonv2 get_but(int x,int y)
  {
    for(int z=0;z<64;z++)
    {
      if(bt_tab[z].get_x()==x && bt_tab[z].get_y()==y)
      {
        return bt_tab[z];
      }
    }
    return null;
  }

  public void actualiser()
  {
    for(int x=0;x<8;x++)
    {
      for(int y=0;y<8;y++)
      {
        get_but(x,y).actu_bt(e.get_case(x,y));
      }
    }
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
          bt_tab[i].setBackground(new Color(255,222,173));
          //bt_tab[i].setForeground(Color.BLACK);
          couleur=1;
        }else
        {
          bt_tab[i].setBackground(new Color(222,184,135));
          //bt_tab[i].setForeground(Color.WHITE);
          couleur=0;
        }
      }else
      {
        if(i%8==0)
        {
          bt_tab[i].setBackground(new Color(222,184,135));
          //bt_tab[i].setForeground(Color.WHITE);
          couleur=0;
        }else
        {
          bt_tab[i].setBackground(new Color(255,222,173));
          //bt_tab[i].setForeground(Color.BLACK);
          couleur=1;
        }
      }
    }
  }

  public Buttonv2 get_Button(Piece p)
  {
    for(int i=0;i<64;i++)
    {
      if(bt_tab[i].getPieceButton()!=null)
      {
        if(bt_tab[i].getPieceButton().equals(p))
        {
          return bt_tab[i];
        }
      }
    }
    return null;
  }

  public void echec_aff(int couleur,Buttonv2 b)
  {
    Buttonv2 roi=get_Button(e.get_roi(couleur));
    roi.setBackground(Color.RED);

    for(int i=0;i<64;i++)
    {
      if(bt_tab[i].getPieceButton()!=null)
      {
        if(e.mouv_possible(bt_tab[i].getPieceButton(),e.get_roi(couleur).get_x(),e.get_roi(couleur).get_y()))
        {
          bt_tab[i].setBackground(Color.ORANGE);
        }
      }
    }
  }

  public void actu_bt_roque()
  {
    for(int x=0;x<8;x++)
    {
      if(e.get_case(x,0)!=get_but(x,0).getPieceButton())
      {
        get_but(x,0).actu_bt(e.get_case(x,0));
      }
    }
    for(int y=0;y<8;y++)
    {
      if(e.get_case(y,7)!=get_but(y,7).getPieceButton())
      {
        get_but(y,7).actu_bt(e.get_case(y,7));
      }
    }
  }

  public void get_dialogue_pat()
  {

    JDialog pat_f=new JDialog();
    pat_f.setLayout(new GridLayout(2,1));
    pat_f.setSize(200,200);

    JLabel jlabel = new JLabel("La partie c'est fini car aucun des joueur ne peut mettre en echec et mat l'autre. (Situation de PAT) Egalite");
    pat_f.add(jlabel);

    JButton pat_b=new JButton("OK");
    pat_b.addActionListener(elis);

    pat_f.setVisible(true);
  }

  public void end_game(int j)
  {
    JDialog end=new JDialog();
    end.setLayout(new GridLayout(2,1));
    end.setSize(400,100);
    JLabel jlabel;

    if(j==1)
    {
      jlabel=new JLabel("La partie est finie. Les blancs a gagne la partie !");
    }else
    {
      jlabel=new JLabel("La partie est finie. Les noirs a gagne la partie !");
    }
    end.add(jlabel);

    JButton end_b=new JButton("OK");
    end_b.addActionListener(elis);

    end.add(end_b);

    end.setVisible(true);
  }

  public void quitter()
  {
    dispose();
  }

  public void promotion(int color,Piece p)
  {
    jop=new JDialog();
    jop.setLayout(new GridLayout(2,3));
    jop.setSize(200,200);

    Buttonv2 promo_Cavalier;
    Buttonv2 promo_Reine;
    Buttonv2 promo_Fou;
    Buttonv2 promo_Pion;
    Buttonv2 promo_Tour;

    int x=p.get_x();
    int y=p.get_y();

    //Buttonv2(Image img, Piece p,int x,int y)
    try
    {
      if(color==1)
      {
        promo_Cavalier=new Buttonv2(ImageIO.read(getClass().getResource("img/chess-knight-white.png")),new Cavalier(true,1,x,y,ImageIO.read(getClass().getResource("img/chess-knight-white.png")),"img/chess-knight-white.png"),x,y);
        promo_Reine=new Buttonv2(ImageIO.read(getClass().getResource("img/chess-queen-white.png")),new Dame(true,1,x,y,ImageIO.read(getClass().getResource("img/chess-queen-white.png")),"img/chess-queen-white.png"),x,y);
        promo_Fou=new Buttonv2(ImageIO.read(getClass().getResource("img/bishop-white.png")),new Fou(true,1,x,y,ImageIO.read(getClass().getResource("img/bishop-white.png")),"img/bishop-white.png"),x,y);
        promo_Pion=new Buttonv2(ImageIO.read(getClass().getResource("img/chess-pawn-white.png")),new Pion(true,1,x,y,ImageIO.read(getClass().getResource("img/chess-pawn-white.png")),"img/chess-pawn-white.png"),x,y);
        promo_Tour=new Buttonv2(ImageIO.read(getClass().getResource("img/chess-rok-white.png")),new Tour(true,1,x,y,ImageIO.read(getClass().getResource("img/chess-rok-white.png")),"img/chess-rok-white.png"),x,y);

      }else
      {
        promo_Cavalier=new Buttonv2(ImageIO.read(getClass().getResource("img/chess-knight.png")),new Cavalier(true,0,x,y,ImageIO.read(getClass().getResource("img/chess-knight.png")),"img/chess-knight.png"),x,y);
        promo_Reine=new Buttonv2(ImageIO.read(getClass().getResource("img/chess-queen.png")),new Dame(true,0,x,y,ImageIO.read(getClass().getResource("img/chess-queen.png")),"img/chess-queen.png"),x,y);
        promo_Fou=new Buttonv2(ImageIO.read(getClass().getResource("img/bishop.png")),new Fou(true,0,x,y,ImageIO.read(getClass().getResource("img/bishop.png")),"img/bishop.png"),x,y);
        promo_Pion=new Buttonv2(ImageIO.read(getClass().getResource("img/chess-pawn.png")),new Pion(true,0,x,y,ImageIO.read(getClass().getResource("img/chess-pawn.png")),"img/chess-pawn.png"),x,y);
        promo_Tour=new Buttonv2(ImageIO.read(getClass().getResource("img/chess-rok.png")),new Tour(true,0,x,y,ImageIO.read(getClass().getResource("img/chess-rok.png")),"img/chess-rok.png"),x,y);
      }

      promo_Cavalier.setBackground(new Color(255,222,173));
      promo_Reine.setBackground(new Color(222,184,135));
      promo_Fou.setBackground(new Color(255,222,173));
      promo_Pion.setBackground(new Color(222,184,135));
      promo_Tour.setBackground(new Color(222,184,135));

      jop.add(promo_Cavalier);
      jop.add(promo_Reine);
      jop.add(promo_Fou);
      jop.add(promo_Pion);
      jop.add(promo_Tour);

      promo_Cavalier.addActionListener(plis);
      promo_Reine.addActionListener(plis);
      promo_Fou.addActionListener(plis);
      promo_Pion.addActionListener(plis);
      promo_Tour.addActionListener(plis);

    }catch(Exception e)
    {
      System.out.println(e);
    }

    jop.setVisible(true);
  }

  class PromoListener implements ActionListener
  {
    public void actionPerformed(ActionEvent pe)
    {
      Buttonv2 pbt=(Buttonv2)pe.getSource();

      b_promo.remplacer(pbt);

      jop.dispose();
    }
  }

  class EndListener implements ActionListener
  {
    public void actionPerformed(ActionEvent en)
    {
      quitter();
      dispose();
    }
  }

  public void charger()
  {
    JFrame frame=new JFrame("");
    JFileChooser dialogue=new JFileChooser(new File("."));
    int returnval=dialogue.showOpenDialog(this);
    if(returnval==JFileChooser.APPROVE_OPTION)
    {
      File file=dialogue.getSelectedFile();
      try
      {
        e.charger(file);
      }catch(IOException e)
      {
        System.out.println(e);
      }catch(ClassNotFoundException cnfe)
      {
        System.out.println(cnfe);
      }
    }
  }

  public void sauver()
  {
    //JFrame parentFrame = new JFrame();

    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setCurrentDirectory(new File("."));
    fileChooser.setDialogTitle("Sauvgarder");

    int userSelection = fileChooser.showSaveDialog(this);

    if (userSelection == JFileChooser.APPROVE_OPTION)
    {
      File fileToSave = fileChooser.getSelectedFile();
      try
      {
        e.sauvegarder(fileToSave);
      }catch(IOException ioe)
      {
        System.out.println(ioe);
      }
    }
  }

  class BoutonSudListener implements ActionListener
  {
    public void actionPerformed(ActionEvent aes)
    {
      String choix=aes.getActionCommand();

      if(choix=="Sauvegarder")
      {
        sauver();

      }
      if(choix=="Charger")
      {
        charger();
        actualiser();
      }
    }
  }

  class BoutonListener implements ActionListener
  {
    int color_adv;

    public void actionPerformed(ActionEvent ae)
    {
      //------------------------------
      //ajouter switch case ici :


      //------------------------------
      Buttonv2 b=(Buttonv2)ae.getSource();//bouton qui déclenche l'action
      if(bt_select==0)//si aucun bouton n'est sélectionner
      {

        if(!e.pat(e.get_tour()))
        {
          get_dialogue_pat();
        }

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
          if(e.roque_possible(p_select,e.get_case(b.get_x(),b.get_y())))
          {
            e.roque(p_select,e.get_case(b.get_x(),b.get_y()));

            actu_bt_roque();

            b.actu_bt(null);
            bt_before.actu_bt(null);
            e.change_joueur();
          }else
          {
            e.bouger(p_select,b.get_x(),b.get_y());
            b.actu_bt(p_select);
            bt_before.actu_bt(null);
          }

          init_backcolor_bt();
          bt_select=0;

          if(p_select.promotion_possible())
          {
            b_promo=b;
            promotion(1,p_select);
          }

          if(p_select.get_couleur()==0)
          {
            color_adv=1;
          }else if(p_select.get_couleur()==1)
          {
            color_adv=0;
          }
          if(e.est_echec(color_adv))
          {
            echec_aff(color_adv,b);
            if(e.mat(color_adv))
            {
              end_game(p_select.get_couleur());
            }
          }else if(!e.pat(color_adv))
          {
            get_dialogue_pat();
          }

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
    private Image img;

    public Buttonv2(Image img, Piece p,int x,int y)
    {
        super(new ImageIcon(img));
        this.p=p;
        this.x=x;
        this.y=y;
        this.img=img;
        //this.nb=x+y*8;
    }

    public void remplacer(Buttonv2 btv)
    {
      this.setIcon(new ImageIcon(btv.get_image()));
      this.actu_bt(btv.getPieceButton());
    }

    public Image get_image()
    {
      return this.img;
    }

    public void set_img(Image img)
    {
      this.img=img;
    }

    public void actu_bt(Piece p)
    {
      this.p=p;
      if(p==null)
      {
        try
        {
          this.img=ImageIO.read(getClass().getResource("img/null.png"));
          this.setIcon(new ImageIcon(this.img));
        }catch(Exception e)
        {
          System.out.println(e);
        }
      }else
      {
        try
        {
          this.img=ImageIO.read(getClass().getResource(p.get_loc_img()));
          this.setIcon(new ImageIcon(this.img));
        }catch(Exception e)
        {
          System.out.println(e);
        }
      }
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
}


}
