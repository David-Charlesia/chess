class Cavalier extends Piece
{
  public Cavalier(){} //constructeur vide

  public Cavalier(boolean etat,int couleur,int x,int y)
  {
    super(etat,couleur,x,y);
  }

  public Cavalier(Cavalier Cavalier)
  {
    super(Cavalier);
  }

  public String toString()
  {
    return "C";
  }

  public boolean mouv_possible(int x , int y)
  {
  	int mouv_x=x-this.get_x(); //de combien de case il bouge sur l'axe x
    int mouv_y=x-this.get_y(); //de combien de case il bouge sur l'axe y

    mouv_x=Math.abs(mouv_x);//valeur absolue de mouv_x
    mouv_y=Math.abs(mouv_y);//valeur absolue de mouv_y


    if (mouv_x==1 && mouv_y==2)
    {
      return true;
    }

    else if (mouv_x==2 && mouv_y==1)
    {
      return true;
    }
	return false;
  }

}
