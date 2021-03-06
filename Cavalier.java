import java.awt.Image;
import java.io.*;

public class Cavalier extends Piece implements Serializable
{

  private static final long serialVersionUID = 2L;

  public Cavalier(){} //constructeur vide

  public Cavalier(boolean etat,int couleur,int x,int y,Image img,String loc_img)
  {
    super(etat,couleur,x,y,img,loc_img);
  }


  public Cavalier(Cavalier Cavalier)
  {
    super(Cavalier);
  }

  public String toString()
  {
    return "C"+this.get_couleur();
  }

  public boolean mouv_possible(int x , int y)
  {
  	int mouv_x=x-this.get_x(); //de combien de case il bouge sur l'axe x
    int mouv_y=y-this.get_y(); //de combien de case il bouge sur l'axe y

    mouv_x=Math.abs(mouv_x);//valeur absolue de mouv_x
    mouv_y=Math.abs(mouv_y);//valeur absolue de mouv_y


    if (mouv_x==1 && mouv_y==2)
    {
      return true;
    }

    if (mouv_x==2 && mouv_y==1)
    {
      return true;
    }
	  return false;
  }

  public int[] direction(int x,int y)
  {
    int[] tab=new int[2];
    tab[0]=3;
    tab[1]=3;
    return tab;//car le cavalier saute les pièces sur son chemin
  }

}
