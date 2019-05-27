import java.util.Scanner;

public class Echequier
{
  private Piece[] cases;

  public Echequier()
  {
    this.cases=new Piece[64];

    //Pièces blanches :
    this.cases[0]=new Tour(false,1,0,0);
    this.cases[1]=new Cavalier(false,1,1,0);
    this.cases[2]=new Fou(false,1,2,0);
    this.cases[3]=new Dame(false,1,3,0);
    this.cases[4]=new Roi(false,1,4,0);
    this.cases[5]=new Fou(false,1,5,0);
    this.cases[6]=new Cavalier(false,1,6,0);
    this.cases[7]=new Tour(false,1,7,0);

    for(int i=8;i<16;i++)
    {
      this.cases[i]=new Pion(false,1,(i-8),1);
    }

    //Pièces noirs :
    this.cases[56]=new Tour(false,0,0,7);
    this.cases[57]=new Cavalier(false,0,1,7);
    this.cases[58]=new Fou(false,0,2,7);
    this.cases[59]=new Roi(false,0,3,7);
    this.cases[60]=new Dame(false,0,4,7);
    this.cases[61]=new Fou(false,0,5,7);
    this.cases[62]=new Cavalier(false,0,6,7);
    this.cases[63]=new Tour(false,0,7,7);

    for(int i=48;i<56;i++)
    {
      this.cases[i]=new Pion(false,0,(i-48),6);
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


  public boolean mouv_possible(Piece p, int dest_x, int dest_y)
  {
    if(this.get_case(dest_x,dest_y)!=null)
    {
      if(this.get_case(dest_x,dest_y).get_couleur()==p.get_couleur())
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

  public void type_mouv(Piece p,int dest_x,int dest_y)
  {
    if(this.get_case(dest_x,dest_y)==null)//déplacer
    {
      this.deplacer(p,dest_x,dest_y);
      if(p.promotion_possible())
      {
        this.promotion(p);//promotion
      }
    }
    //else if(this.get_case(dest_x,dest_y).toString()=="") à développer pour roque

    else//manger
    {
      this.manger(p,dest_x,dest_y);
    }
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


  public boolean bouger(Piece p,int dest_x,int dest_y)
  {
    if(this.mouv_possible(p,dest_x,dest_y))
    {
      if(this.est_echec_mine(p,dest_x,dest_y))
      {
        return false;
      }
      this.type_mouv(p,dest_x,dest_y);
      return true;
    }
    return false;
  }


  public void manger(Piece p,int x,int y)
  {
    int x_sup=p.get_x();
    int y_sup=p.get_y();

    this.set_case(x,y,p);

    this.set_case(x_sup,y_sup);
  }

  public boolean roque_possible(Roi r,Tour t)
  {
    return r.get_etat() && t.get_etat();
  }

  public void roque(Roi r,Tour t)
  {
    if (roque_possible(r,t))
    {
      System.out.println("Yeah");
    }
  }

  public void promotion(Piece p)//déplacer le pion avant de faire la promotion
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
  }


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

      if(!(this.bouger(this.get_case(x,y),dest_x,dest_y)))
      {
        System.out.println("Recommencer");
      }else
      {
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
}
