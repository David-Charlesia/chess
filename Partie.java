import java.io.*;

public class Partie
{
  Echequier e;
  int joueur1;//0 pour noir et 1 pour blanc
  int joueur2;//0 pour noir et 1 pour blanc

  public Partie()
  {
    this.e=new Echequier();
    this.joueur1=0;
    this.joueur2=0;
  }

  public void jouer(){}

  public void sauvegard( Piece p , int x , int y)
  {
    try
    {
      PrintWriter aEcrie = new PrintWriter(BufferedWiriter(new FileWiter("partie_Sauv.txt")));
      aEcrie.write(this.piece , this.x , this.y);
      aEcrie.close();
    }
    catch (IOException d)
    {
      System.out.println("ERREUR" + d);
    }
  }

  public void chargement()
  {
    String path = "partie_Sauv.txt";

  }
}
