import java.awt.Image;

public class Pion extends Piece
{


  //Faire la promotion !!!!!


  public Pion(){} //constructeur vide

  public Pion(boolean etat,int couleur,int x,int y,Image img)
  {
    super(etat,couleur,x,y,img);
  }

  public Pion(Pion pion)
  {
    super(pion);
  }

  public String toString()
  {
    return "P"+this.get_couleur();
  }

  public boolean mouv_possible(int x,int y)
  {
    int mouv_x=x-this.get_x(); //de combien de case il bouge sur l'axe x
    int mouv_y=y-this.get_y(); //de combien de case il bouge sur l'axe y

    if (mouv_x==0 && mouv_y==0)
    {
      return false;
    }

    if(this.get_couleur()==0)
    {
      mouv_x=mouv_x*-1;
      mouv_y=mouv_y*-1;
    }

    if(mouv_x==0)
    {
      if(mouv_y==2 && this.get_etat()==false)
      {
        return true;
      }
      if(mouv_y==1)
      {
        return true;
      }
    }
    return false;
  }

  public boolean manger_possible(int dest_x,int dest_y)
  {
    int mouv_x=dest_x-this.get_x(); //de combien de case il bouge sur l'axe x
    int mouv_y=dest_y-this.get_y(); //de combien de case il bouge sur l'axe y

    if(this.get_couleur()==0)
    {
      mouv_x=mouv_x*-1;
      mouv_y=mouv_y*-1;
    }

    mouv_x=Math.abs(mouv_x);

    if(mouv_y==1 && mouv_x==1)
    {
      return true;
    }

    return false;
  }

  public int pion_id(){return 1;}

  /*public int[] direction(int x,int y)
  {

    //retourne un tableau[2] avec [0]=-1,0 ou 1 sur x et pareil avec [1] sur y
    //donne la direction du déplacement

    int mouv_x=x-this.get_x(); //de combien de case il bouge sur l'axe x
    int mouv_y=y-this.get_y(); //de combien de case il bouge sur l'axe y
    int[] tab=new int[2];

    if(this.get_couleur()==0)
    {
      tab[0]=mouv_x*-1;
      tab[1]=mouv_y*-1;
    }
    if(this.get_couleur()==1)
    {
      tab[0]=mouv_x;
      tab[1]=mouv_y;
    }
    return tab;
  }*/

  public boolean promotion_possible()
  {
    int y=this.get_y();
    if((y==0 && this.get_couleur()==0) || (y==7 && this.get_couleur()==1))
    {
      return true;
    }
    return false;
  }

}
