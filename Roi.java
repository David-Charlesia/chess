import java.awt.Image;
import java.io.*;

public class Roi extends Piece implements Serializable
{
  private static final long serialVersionUID = 6L;

  public Roi(){} //constructeur vide

  public Roi(boolean etat,int couleur,int x,int y,Image img,String loc_img)
  {
    super(etat,couleur,x,y,img,loc_img);
  }

  public Roi(Roi Roi)
  {
    super(Roi);
  }

  public String toString()
  {
    return "R"+this.get_couleur();
  }

  public int pion_id(){return 2;}

  public boolean mouv_possible(int x,int y)
  {
    //le roi peut aller dans toutes les directions mais seulement d'1 case
    //donc dès que mouv_x ou/et mouv_y égale à 1, c'est bon

    int mouv_x=x-this.get_x(); //de combien de case il bouge sur l'axe x
    int mouv_y=y-this.get_y(); //de combien de case il bouge sur l'axe y

    mouv_x=Math.abs(mouv_x);//valeur absolue de mouv_x
    mouv_y=Math.abs(mouv_y);//valeur absolue de mouv_y

    if (mouv_x==0 && mouv_y==0)
    {
      return false;
    }

    if(mouv_x==1 && mouv_y==1)
    {
      return true;
    }
    if(mouv_x==1 && mouv_y==0)
    {
      return true;
    }
    if(mouv_x==0 && mouv_y==1)
    {
      return true;
    }

    return false;
  }

  public boolean roque_possible()
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
