import java.util.Scanner;
import java.util.ArrayList;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.*;


public class Echequier implements Serializable
{
  private Piece[] cases;
  private int joueur=1;

  public Echequier(int i){}//echiquier vide pour test

  public Echequier()
  {
    try
    {
      this.cases=new Piece[64];

      //Pièces blanches :
      this.cases[0]=new Tour(false,1,0,0,ImageIO.read(getClass().getResource("img/chess-rok-white.png")),"img/chess-rok-white.png");
      this.cases[1]=new Cavalier(false,1,1,0,ImageIO.read(getClass().getResource("img/chess-knight-white.png")),"img/chess-knight-white.png");
      this.cases[2]=new Fou(false,1,2,0,ImageIO.read(getClass().getResource("img/bishop-white.png")),"img/bishop-white.png");
      this.cases[3]=new Dame(false,1,3,0,ImageIO.read(getClass().getResource("img/chess-queen-white.png")),"img/chess-queen-white.png");
      this.cases[4]=new Roi(false,1,4,0,ImageIO.read(getClass().getResource("img/chess-king-white.png")),"img/chess-king-white.png");
      this.cases[5]=new Fou(false,1,5,0,ImageIO.read(getClass().getResource("img/bishop-white.png")),"img/bishop-white.png");
      this.cases[6]=new Cavalier(false,1,6,0,ImageIO.read(getClass().getResource("img/chess-knight-white.png")),"img/chess-knight-white.png");
      this.cases[7]=new Tour(false,1,7,0,ImageIO.read(getClass().getResource("img/chess-rok-white.png")),"img/chess-rok-white.png");

      for(int i=8;i<16;i++)
      {
        this.cases[i]=new Pion(false,1,(i-8),1,ImageIO.read(getClass().getResource("img/chess-pawn-white.png")),"img/chess-pawn-white.png");
      }

      //Pièces noirs :
      this.cases[56]=new Tour(false,0,0,7,ImageIO.read(getClass().getResource("img/chess-rok.png")),"img/chess-rok.png");
      this.cases[57]=new Cavalier(false,0,1,7,ImageIO.read(getClass().getResource("img/chess-knight.png")),"img/chess-knight.png");
      this.cases[58]=new Fou(false,0,2,7,ImageIO.read(getClass().getResource("img/bishop.png")),"img/bishop.png");
      this.cases[59]=new Roi(false,0,3,7,ImageIO.read(getClass().getResource("img/chess-king.png")),"img/chess-king.png");
      this.cases[60]=new Dame(false,0,4,7,ImageIO.read(getClass().getResource("img/chess-queen.png")),"img/chess-queen.png");
      this.cases[61]=new Fou(false,0,5,7,ImageIO.read(getClass().getResource("img/bishop.png")),"img/bishop.png");
      this.cases[62]=new Cavalier(false,0,6,7,ImageIO.read(getClass().getResource("img/chess-knight.png")),"img/chess-knight.png");
      this.cases[63]=new Tour(false,0,7,7,ImageIO.read(getClass().getResource("img/chess-rok.png")),"img/chess-rok.png");

      for(int i=48;i<56;i++)
      {
        this.cases[i]=new Pion(false,0,(i-48),6,ImageIO.read(getClass().getResource("img/chess-pawn.png")),"img/chess-pawn.png");
      }
    } catch (Exception ex)
    {
      System.out.println(ex);
    }
  }

  public boolean case_valide(int x,int y)
  {
    if(x>-1 && x<7)
    {
      if(y>-1 && y<7)
      {
        return true;
      }
    }
    return false;
  }

  //les méthodes set et get sont appelé sur un échequier
  public Piece get_case(int x,int y)
  {
    return this.cases[x+y*8];
  }

  public Piece get_case(int i)
  {
    return this.cases[i];
  }

  public void set_case(int x,int y,Piece p)//cette méthode est utilisé pour les méthodes manger
  //la piece passer en paramètre
  {
    if(p!=null)
    {
      p.set_x(x);
      p.set_y(y);
    }
    this.cases[x+y*8]=p;
  }

  public void set_case(int x,int y)//met null dans la case
  {
    this.cases[x+y*8]=null;
  }

  public Roi get_roi(int couleur)
  {
    String ch="R"+couleur;
    for(int i=0;i<63;i++)
    {
      if(this.cases[i]!=null)
      {
        if(this.cases[i].toString().equals(ch))
        {
          return (Roi)this.cases[i];
        }
      }
    }
    return null;
  }

  public int get_tour()
  {
    return this.joueur;
  }


  public boolean mouv_possible(Piece p, int dest_x, int dest_y)
  {
    if(this.get_case(dest_x,dest_y)!=null)
    {
      if(this.get_case(dest_x,dest_y).get_couleur()==p.get_couleur())
      {
        return false;
      }
      if(p.manger_possible(dest_x,dest_y))
      {
        return true;
      }else if(p.pion_id()==1)
      {
        return false;
      }
    }

    if(p.mouv_possible(dest_x,dest_y))
    {
      int[] tab=p.direction(dest_x,dest_y);

      dest_x=dest_x-tab[0];
      dest_y=dest_y-tab[1];

      if(tab[0]==3 && tab[1]==3)
      {
        return true;
      }

      int x=p.get_x();
      int y=p.get_y();

      while(dest_x!=x || dest_y!=y)
      {
        x=x+tab[0];
        y=y+tab[1];

        if(this.get_case(x,y)!=null)
        {
          return false;
        }
      }
      return true;
    }
    return false;
  }


  public void deplacer(Piece p,int dest_x,int dest_y)
  {
    int origin_x=p.get_x();//x originel
    int origin_y=p.get_y();//y originel

    this.set_case(dest_x,dest_y,p);
    this.set_case(origin_x,origin_y);
  }

  public void change_joueur()
  {
    if(this.joueur==1)
    {
      this.joueur=0;
    }else
    {
      this.joueur=1;
    }
  }

  public void type_mouv(Piece p,int dest_x,int dest_y)
  {
    if(this.get_case(dest_x,dest_y)==null)//déplacer
    {
      this.deplacer(p,dest_x,dest_y);
      /*if(p.promotion_possible())
      {
        this.promotion(p);//promotion
      }*/
    }else if(roque_possible(p,this.get_case(dest_x,dest_y)))
    {
      this.roque(p,this.get_case(dest_x,dest_y));
    }

    else//manger
    {
      this.manger(p,dest_x,dest_y);
    }
  }

  /*public int case_roque(Piece roi,Piece tour)
  {

  }*/

  public void roque(Piece roi,Piece tour)
  {
    //int[] dir_roi=roi.direction(tour.get_x(),tour.get_y());
    //int[] dir_tour=tour.direction(roi.get_x(),roi.get_y());

    if(roi.get_couleur()==0)
    {
      if(tour.get_x()<roi.get_x())
      {
        this.deplacer(roi,1,7);
        this.deplacer(tour,2,7);
      }else
      {
        this.deplacer(roi,5,7);
        this.deplacer(tour,4,7);
      }
    }else
    {
      if(tour.get_x()<roi.get_x())
      {
        this.deplacer(tour,3,0);
        this.deplacer(roi,2,0);
      }else
      {
        this.deplacer(roi,6,0);
        this.deplacer(tour,5,0);
      }
    }
    tour.set_etat();
  }

  public boolean roque_possible(Piece roi,Piece tour)
  {
    if(roi==null || tour==null)
    {
      return false;
    }
    if(roi.get_couleur()!=tour.get_couleur())
    {
      return false;
    }
    if(roi.roque_possible())
    {
      if(tour.roque_possible_tour())
      {
        if(mouv_roque_possible(roi,tour))
        {
          return true;
        }
      }
    }
    return false;
  }

  public boolean mouv_roque_possible(Piece p,Piece tour)
  {
    int[] tab=p.direction(tour.get_x(),tour.get_y());

    int x=p.get_x();
    x=x+tab[0];
    while(x!=tour.get_x())
    {
      if(this.get_case(x,p.get_y())!=null)
      {
        if(this.get_case(x,p.get_y())!=tour)
        {
          return false;
        }
      }
      x=x+tab[0];
    }
    return true;
  }

  public boolean est_echec_mine(Piece p,int dest_x,int dest_y)
  //méthode a appelé avant de bouger une Pièces
  //si le mouv met son propre roi en échec ou pas
  //vérifier avant si mouv possible
  {
    int couleur=p.get_couleur();
    Roi r=this.get_roi(couleur);
    Piece p_old=null;//ancienne pièce présente avant manger

    int old_x=p.get_x();//ancien x de la pièce bouger
    int old_y=p.get_y();//ancien y de la pièce bouger

    if(this.get_case(dest_x,dest_y)!=null)//si on mange
    {
      p_old=this.get_case(dest_x,dest_y);
    }

    this.type_mouv(p,dest_x,dest_y);

    for(int i=0;i<64;i++)
    {
      if(this.cases[i]!=null)
      {
        if(this.cases[i].get_couleur()!=couleur)
        {
          if(this.mouv_possible(this.cases[i],r.get_x(),r.get_y()))
          {
            this.set_case(dest_x,dest_y,p_old);
            this.set_case(old_x,old_y,p);
            return true;
          }
        }
      }
    }
    this.set_case(dest_x,dest_y,p_old);
    this.set_case(old_x,old_y,p);
    return false;
  }

  public boolean est_echec(int couleur)
  {
    Roi r=this.get_roi(couleur);

    for(int i=0;i<64;i++)
    {
      if(this.cases[i]!=null)
      {
        if(this.cases[i].get_couleur()!=couleur)
        {
          if(this.mouv_possible(this.cases[i],r.get_x(),r.get_y()))
          {
            return true;
          }
        }
      }
    }
    return false;
  }

  public boolean bouger_ok(Piece p,int dest_x,int dest_y)
  {
    if(!(this.joueur==p.get_couleur()))
    {
      return false;
    }
    if(this.get_case(dest_x,dest_y)!=null)
    {
      if(roque_possible(p,this.get_case(dest_x,dest_y)))
      {
        return true;
      }
    }

    if(this.est_echec(p.get_couleur()))
    {
      if(this.mouv_possible(p,dest_x,dest_y))
      {
        if(this.est_echec_mine(p,dest_x,dest_y))
        {
          return false;
        }
        return true;
      }
      return false;
    }

    if(this.mouv_possible(p,dest_x,dest_y))
    {
      if(this.est_echec_mine(p,dest_x,dest_y))
      {
        return false;
      }
      return true;
    }
    return false;
  }

  public boolean pat(int couleur)
  {
    String r="R"+couleur;
    for(int x=0;x<64;x++)
    {
      if(this.cases[x]!=null)
      {
        if(this.cases[x].toString()!=r && this.cases[x].get_couleur()==couleur)
        {
          return true;
        }
      }
    }
    for(int i=0;i<64;i++)
    {
      if(this.cases[i]!=null)
      {
        for(int j=0;j<8;j++)
        {
          for(int k=0;k<8;k++)
          {
            if(this.bouger_ok(this.cases[i],j,k))
            {
              return true;
            }
          }
        }
      }
    }
    return false;
  }

  public boolean mat(int couleur)//couleur du roi en echec //vérifier avant si roi echec
  {
    for(int k=0;k<64;k++)
    {
      if(this.cases[k]!=null)
      {
        for(int i=0;i<8;i++)
        {
          for(int j=0;j<8;j++)
          {
            if(this.bouger_ok(this.cases[k],i,j))
            {
              return false;
            }
          }
        }
      }
    }
    return true;
  }

  public void bouger(Piece p,int dest_x,int dest_y)
  {
    this.type_mouv(p,dest_x,dest_y);
    p.set_etat();
    if(this.joueur==1)
    {
      this.joueur=0;
    }else
    {
      this.joueur=1;
    }
  }


  public void manger(Piece p,int x,int y)
  {
    int x_sup=p.get_x();
    int y_sup=p.get_y();

    this.set_case(x,y,p);

    this.set_case(x_sup,y_sup);
  }

  /*public void promotion(Piece p)//déplacer le pion avant de faire la promotion
  {
    //choix : 1=Cavalier, 2=Dame, 3=Fou, 4=Tour
    int x=p.get_x();
    int y=p.get_y();
    int couleur=p.get_couleur();
    this.cases[x+y*8]=null;

    Scanner sc = new Scanner(System.in);
    System.out.println("Saisissez un choix(1=Cavalier, 2=Dame, 3=Fou, 4=Tour) : ");
    int choix=sc.nextInt();
    sc.close();

    if(choix==1)
    {
      this.cases[x+y*8]=new Cavalier(false,couleur,x,y);
    }
    if(choix==2)
    {
      this.cases[x+y*8]=new Dame(false,couleur,x,y);
    }
    if(choix==3)
    {
      this.cases[x+y*8]=new Fou(false,couleur,x,y);
    }
    if(choix==4)
    {
      this.cases[x+y*8]=new Tour(false,couleur,x,y);
    }
  }*/


  public void jouer()
  {
    Scanner sc=new Scanner(System.in);
    boolean rep=true;
    String rep2="";
    int x;
    int y;
    int dest_x;
    int dest_y;

    while(rep)
    {
      System.out.println("X origin = ");
      x=sc.nextInt();

      System.out.println("Y origin = ");
      y=sc.nextInt();

      System.out.println("dest_x = ");
      dest_x=sc.nextInt();

      System.out.println("dest_y = ");
      dest_y=sc.nextInt();

      if(!(this.bouger_ok(this.get_case(x,y),dest_x,dest_y)))
      {
        System.out.println("Recommencer");
      }else
      {
        this.bouger(this.get_case(x,y),dest_x,dest_y);
        System.out.println("Continuer ?(o pour oui et n pour non) : ");
        rep2=sc.nextLine();
        if(rep2=="o")
        {
          rep=true;
        }
        if(rep2=="n")
        {
          rep=false;
        }
      }
      this.afficher();
    }
    sc.close();
  }

  public ArrayList<Integer> all_mouv_possible(Piece p)
  {
    ArrayList<Integer> tab=new ArrayList<Integer>();

    for(int x=0;x<8;x++)
    {
      for(int y=0;y<8;y++)
      {
        if(this.bouger_ok(p,x,y))
        {
          tab.add(x+y*8);
        }
      }
    }
    return tab;
  }

  public void afficher()
  {
    String ch="\n--------------------------------------------------------------------------";


    for(int y=7;y>-1;y--)
    {
      ch+="\n         |       |       |       |       |       |       |       |       |";
      ch+="\n    "+y+"    |";
      for(int x=0;x<8;x++)
      {
        if(this.get_case(x,y)!=null)
        {
          ch+="   "+this.get_case(x,y).toString()+"  |";
        }
        else
        {
            ch+="       |";
        }
      }
      ch+="\n         |       |       |       |       |       |       |       |       |";
      ch+="\n--------------------------------------------------------------------------";

    }

    ch+="\n         |       |       |       |       |       |       |       |       |";
    ch+="\n         |   a   |   b   |   c   |   d   |   e   |   f   |   g   |   h   |";
    ch+="\n         |       |       |       |       |       |       |       |       |";

    System.out.println(ch);
   }


   /*public void sauvegarder(String nomF)
   {
     try
     {
       File f=new File(nomF);
       if(f.exists() && !f.isDirectory())
       {
         f.delete();
       }
       Iterator<Piece> ite=this.cases.iterator();
       Piece p;
       BufferedWriter bf=new BufferedWriter(new FileWriter (nomF,true));

       f.write(this.joueur);
       f.newLine();

       while(ite.hasNext())
       {
         p=ite.next();

       }
     }
   }*/

   public void sauvegarder(File chemin) throws IOException
   {
     FileOutputStream fos = new FileOutputStream(chemin);
     ObjectOutputStream oos = new ObjectOutputStream(fos);
     oos.writeObject(this.cases);
     oos.writeObject(this.joueur);
     oos.close();
   }

   public void charger(File chemin) throws IOException, ClassNotFoundException
   {
     FileInputStream fis = new FileInputStream(chemin);
     ObjectInputStream ois = new ObjectInputStream(fis);
     this.cases=(Piece[])ois.readObject();
     this.joueur=(int)ois.readObject();
     ois.close();
   }
}
