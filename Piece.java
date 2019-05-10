public class Piece
{
  private boolean etat; //false si n'a jamais joué et true sinon
  private int couleur; //1 pour noir et 0 pour blanc
  private int x;
  private int y;

  public Piece(){} //constructeur vide

  public Piece(boolean etat,int couleur,int x,int y)
  {
    this.etat=etat;
    this.couleur=couleur;
    this.x=x;
    this.y=y;
  }

  public Piece(Piece piece)
  {
    this.etat=piece.etat;
    this.couleur=piece.couleur;
    this.x=piece.x;
    this.y=piece.y;
  }

  public void se_deplacer(int x,int y)
  {
    this.x=x;
    this.y=y;
    this.etat=true;
  }

  public int get_x()
  {
    return this.x;
  }

  public int get_y()
  {
    return this.y;
  }

  public void set_x(int x)
  {
    this.x=x;
  }

  public void set_y(int y)
  {
    this.y=y;
  }

  public boolean get_etat()
  {
    return this.etat;
  }

  public int get_couleur()
  {
    return this.couleur;
  }

  public boolean mouv_possible(int x,int y){return true;}//à redéfinir pour chaque pièce

  public boolean manger_possible(int dest_x,int dest_y){return false;}

  public boolean promotion_possible(int x,int y)//méthode redéfini dans Pion uniquement
  {
    return false;
  }

  public int[] direction(int x,int y)
  {

    //retourne un tableau[2] avec [0]=-1,0 ou 1 sur x et pareil avec [1] sur y
    //donne la direction du déplacement

    int mouv_x=x-this.get_x(); //de combien de case il bouge sur l'axe x
    int mouv_y=y-this.get_y(); //de combien de case il bouge sur l'axe y
    int[] tab=new int[2];

    tab[0]=mouv_x;
    tab[1]=mouv_y;
    return tab;
  }
}
