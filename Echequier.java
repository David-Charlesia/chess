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
      this.cases[i]=new Pion(false,1,i,1);
    }

    //Pièces noirs :
    this.cases[63]=new Tour(false,0,0,7);
    this.cases[62]=new Cavalier(false,0,1,7);
    this.cases[61]=new Fou(false,0,2,7);
    this.cases[60]=new Dame(false,0,3,7);
    this.cases[59]=new Roi(false,0,4,7);
    this.cases[58]=new Fou(false,0,5,7);
    this.cases[57]=new Cavalier(false,0,6,7);
    this.cases[56]=new Tour(false,0,7,7);

    for(int i=55;i>47;i--)
    {
      this.cases[i]=new Pion(false,0,i,6);
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

  public void set_case(int x,int y,Piece p)//cette méthode est utilisé pour les méthodes manger
  //la piece passer en paramètre
  {
    p.set_x(x);
    p.set_y(y);
    this.cases[x+y*8]=p;
  }

  public void set_case(int x,int y)//met null dans la case
  {
    this.cases[x+y*8]=null;
  }




  public boolean mouv_possible(Piece p,int dest_x,int dest_y)//vérifier si Piece p est nécessaire en fonction du p
  {
    if (p.mouv_possible(dest_x,dest_y))
    {
      //en fonction de la direction, faire une boucle while x!=dest_x... pour parcourir la distance
      //et vérifier qu'il n'y a pas de pièce sur la route qui bloque le passage.

      //faire un if TYPE OBJECT cavalier pour faire d'une façcon différente

      int[] tab=p.direction(dest_x,dest_y);
      int x=p.get_x();
      int y=p.get_y();

      while(x!=dest_x && y!=dest_y)//vérifier cette condition après l'ajout de la classe cavalier
      {
        x=x+tab[0];
        y=y+tab[1];

        if(this.cases[x+y*8]!=null) //vérifier ce que le this pointe
        {
          return false;
        }
      }
      return true;
    }
    return false;
  }


  public void bouger(Piece p,int dest_x,int dest_y)
  {
    if(p.manger_possible(dest_x,dest_y))//appelle la méthode dans Pion si c'est un pion, sinon dans Pièces
    //si c'est dans pièce, ça return false, si c'est dans Pion, ça exécute la méthode dans pion.
    {
      this.manger(p,dest_x,dest_y);
    }

    if(this.manger_possible(p,dest_x,dest_y))
    {
      this.manger(p,dest_x,dest_y);
    }

    if(this.mouv_possible(p,dest_x,dest_y))
    {
      int x=p.get_x();
      int y=p.get_y();

      this.set_case(dest_x,dest_y,p);
      this.set_case(x,y);

      if(p.promotion_possible())
      {
        Scanner sc = new Scanner(System.in);
        System.out.println("Saisissez un choix(1=Cavalier, 2=Dame, 3=Fou, 4=Tour) : ");
        int choix=sc.nextInt();
        this.promotion(p,choix);
      }
    }

  }



  public boolean manger_possible(Piece p,int x,int y)
  {
    if(this.cases[x+y*8]!=null)
    {
      if(this.cases[x+y*8].get_couleur()!=p.get_couleur())
      {
        return true;
      }
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

  public Piece x_y_to_piece(int x,int y)
  {
    return this.cases[x+y*8];
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

  public void promotion(Piece p,int choix)//déplacer le pion avant de faire la promotion
  {
    //choix : 1=Cavalier, 2=Dame, 3=Fou, 4=Tour
    int x=p.get_x();
    int y=p.get_y();
    int couleur=p.get_couleur();
    this.cases[x+y*8]=null;

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
  public void afficher()
  {
    String ch="\n                                                                        ";
    ch+="\n                                                                        ";
    ch+="\n                                                                        ";


    for(int y=0;y<8;y++)
    {
      ch+="\n         |       |       |       |       |       |       |       |       |";
      ch+="\n    "+y+"    |";
      for(int x=0;x<8;x++)
      {
        if(this.get_case(x,y)!=null)
        {
          ch+="   "+this.get_case(x,y).toString()+"   |";
        }
        else
        {
            ch+="       |";
        }
      }
      ch+="\n         |       |       |       |       |       |       |       |       |";

    }

    ch+="\n         |       |       |       |       |       |       |       |       |";
    ch+="\n         |   a   |   b   |   c   |   d   |   e   |   f   |   g   |   h   |";
    ch+="\n         |       |       |       |       |       |       |       |       |";

    System.out.println(ch);
   }
}
