public class Fou extends Piece
{

  public Fou(){} //constructeur vide

  public Fou(boolean etat,int couleur,int x,int y)
  {
    super(etat,couleur,x,y);
  }

  public Fou(Fou fou)
  {
    super(fou);
  }

  public boolean mouv_possible(int x,int y)
  {
    int mouv_x=x-this.get_x(); //de combien de case il bouge sur l'axe x
    int mouv_y=y-this.get_y(); //de combien de case il bouge sur l'axe y

    mouv_x=Math.abs(mouv_x);//valeur absolue de mouv_x
    mouv_y=Math.abs(mouv_y);//valeur absolue de mouv_y

    if (mouv_x==0 && mouv_y==0)
    {
      return false;
    }

    if (mouv_x==mouv_y)
    {
      return true;
    }
    return false;

  }
}
