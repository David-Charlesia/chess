public class Tour extends Piece
{
  public Tour(){} //constructeur vide

  public Tour(boolean etat,int couleur,int x,int y)
  {
    super(etat,couleur,x,y);
  }

  public Tour(Tour tour)
  {
    super(tour);
  }

  public String toString()
  {
    return "T"+this.get_couleur();
  }

  public boolean mouv_possible(int x,int y)
  {
    //la tour bouge en ligne droite et sur un axe à la fois.

    int mouv_x=x-this.get_x(); //de combien de case il bouge sur l'axe x
    int mouv_y=x-this.get_y(); //de combien de case il bouge sur l'axe y

    if (mouv_x==0 && mouv_y==0)
    {
      return false;
    }

    if(mouv_x==0 && mouv_y!=0)
    {
      return true;
    }
    if(mouv_x!=0 && mouv_y==0)
    {
      return true;
    }
    return false;
  }
  public int[] direction(int x,int y)
  {
    int mouv_x=x-this.get_x(); //de combien de case il bouge sur l'axe x
    int mouv_y=y-this.get_y(); //de combien de case il bouge sur l'axe y
    int[] tab=new int[2];

    if (mouv_x>0)
    {
      tab[0]=1;
      tab[1]=0;
    }
    if (mouv_y>0)
    {
      tab[0]=0;
      tab[1]=1;
    }
    return tab;
  }

}
