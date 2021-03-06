import java.awt.Image;
import java.io.*;

public class Tour extends Piece implements Serializable
{
  private static final long serialVersionUID = 7L;
  public Tour(){} //constructeur vide

  public Tour(boolean etat,int couleur,int x,int y,Image img,String loc_img)
  {
    super(etat,couleur,x,y,img,loc_img);
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
    int mouv_y=y-this.get_y(); //de combien de case il bouge sur l'axe y

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

  public boolean roque_possible_tour()
  {
    if(!this.get_etat())
    {
      return true;
    }else
    {
      return false;
    }
  }

}
