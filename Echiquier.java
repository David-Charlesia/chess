public class Echiquier
{
  private Piece[] cases; //contient toutes les pièces de l'échiquier
  private int joueur; //contient le num du joueur qui joue

  //************CONSTRUCTEURS************

  public Echiquier()
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

  //************GET & SET************

  public Piece get_case(int x,int y) //renvoie la pièce placer sur la case (x,y)
  {
    return this.cases[x+y*8];
  }

  public Piece get_case(int i) //renvoie la pièce placer sur la case[i]
  {
    return this.cases[i];
  }

  public Roi get_roi(int couleur) //renvoie le roi de la couleur placer en paramètre
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
  }

  public void set_case(int x,int y,Piece p) //modifie les valeur de la pièce (x,y) et la place sur la case (x,y)
  {
    if(p!=null)
    {
      p.set_x(x);
      p.set_y(y);
    }
    this.cases[x+y*8]=p;
  }

  public void set_case(int x,int y)//met null dans la case (x,y)
  {
    this.cases[x+y*8]=null;
  }

  //************METHODES************

  public void deplacer(Piece p,int dest_x,int dest_y) //déplace une pièce d'une case à une autre
  {
    int origin_x=p.get_x();//x originel
    int origin_y=p.get_y();//y originel

    this.set_case(dest_x,dest_y,p);
    this.set_case(origin_x,origin_y);
  }

  //-----------------------------------

  public boolean roque_possible(Roi r,Tour t)
  {
    return r.get_etat() && t.get_etat();
  }

  //-----------------------------------

  public void roque(Roi r,Tour t)
  {
    if (roque_possible(r,t))
    {
      System.out.println("Yeah");
    }
  }

  //-----------------------------------

  public boolean est_echec_mine(Piece p,int dest_x,int dest_y)//vérifie si le mouv va mettre en echec son propre roi
  //méthode a appelé avant de bouger une Pièces
  //si le mouv met son propre roi en échec ou pas
  //vérifier avant si mouv possible
  {
    int couleur=p.get_couleur();
    Roi r=this.get_roi(couleur);

    int old_x=p.get_x();//ancien x de la pièce bouger
    int old_y=p.get_y();//ancien y de la pièce bouger

    if(this.get_case(dest_x,dest_y)!=null)//si on mange
    {
      Piece p_old=this.get_case(dest_x,dest_y); //ancienne pièce présente avant manger
    }

    this.mouvement(p,dest_x,dest_y);

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

  //-----------------------------------

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

  //-----------------------------------

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

  //-----------------------------------

  public void mouvement(Piece p,int dest_x,int dest_y)//effectue automatiquement le mouvement approprié
  {
    if(this.get_case(dest_x,dest_y)==null)//déplacer
    {
      this.deplacer(p,dest_x,dest_y);
      /*if(p.promotion_possible())
      {
        this.promotion(p);//promotion
      }*/
    }
    //else if(this.get_case(dest_x,dest_y).toString()=="") à développer pour roque

    else//manger
    {
      this.manger(p,dest_x,dest_y);
    }
  }

  //-----------------------------------


}
