import java.awt.Image;
import java.io.*;

public class Dame extends Piece implements Serializable
{
  public Dame(){}

  public Dame(boolean etat,int couleur,int x,int y,Image img)
  {
    super(etat,couleur,x,y,img);
  }

  public Dame(Dame Dame)
  {
    super(Dame);
  }

  public String toString()
  {
    return "D"+this.get_couleur();
  }

  public boolean mouv_possible(int x,int y)
  {
    //la reine peut aller dans toutes les directions.

    int mouv_x=x-this.get_x(); //de combien de case il bouge sur l'axe x
    int mouv_y=y-this.get_y(); //de combien de case il bouge sur l'axe y

    mouv_x=Math.abs(mouv_x);//valeur absolue de mouv_x
    mouv_y=Math.abs(mouv_y);//valeur absolue de mouv_y


    if(mouv_x==0 && mouv_y==0)
    {
      return false;
    }
    //elle peut se déplacer comme un fou :
    if (mouv_x==mouv_y)
    {
      return true;
    }

    //elle peut se déplacer comme une tour :
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
}
