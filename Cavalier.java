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
}
