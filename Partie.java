import java.io.*;

public class Partie
{
  Echequier e;
  int joueur1;//0 pour noir et 1 pour blanc
  int joueur2;//0 pour noir et 1 pour blanc
  private ArrayList<Histoire> histoire;

  public Partie()
  {
    this.e=new Echequier();
    this.joueur1=0;
    this.joueur2=0;
    ArrayList<Histoire> histoire = new ArrayList<String>();
  }

  public void jouer(){}



    public void Historique(Piece p,int x, int y)
    {
      String ch= p.toString()+","+x+","+y;
      histoire.add(ch);


    }

  public void sauvegard()
  {
    //try
    //{
     FileOutputStream fichier = new FileOutputStream("historique.txt");
     ObjetOutputStream out = new ObjetOutputStream(fichier);
     out.writeObjet(al);
     out.close();
     fichier.close();
     System.out.println("La partie est sauvegarder.");
    /*}

   catch (FileOutFoundException o )
   {
     o.printStackTrace();
   }

   catch (IOException i)
   {
     i.printStackTrace();
   }*/

 }
  public void chargement()
  {
    String path = "historique.txt";
    

  }
}
