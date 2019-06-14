import java.io.*;

public class Test
{
  public static void main(String[] args)
  {
    //Piece p1=new Piece(true,1,2,3);

    //Piece p1_copy=new Piece(p1);

    //Pion p2=new Pion(false,5,6,7);

    //Pion p2_copy=new Pion(p2);

    //int a=-1;

    //System.out.println("a="+a);

    //a=Math.abs(a);

    //System.out.println("a="+a);

    Echequier e=new Echequier();

    /*e.bouger(e.get_case(3,1),3,3);
    e.bouger(e.get_case(2,0),6,4);
    e.bouger(e.get_case(4,6),4,4);

    e.afficher();
    try
    {
      e.sauvegarder("test.tmp");
    }catch(IOException ie)
    {
      System.out.println(ie);
    }

    e=new Echequier();
    e.afficher();

    try
    {
      e.charger("test.tmp");
    }catch(IOException ie)
    {
      System.out.println(ie);
    }catch(ClassNotFoundException cnfe)
    {
      System.out.println(cnfe);
    }
    e.afficher();*/
    //System.out.println(e.mouv_possible2(e.get_case(6,4),3,7));

    IHM fenetre=new IHM(e);





    //e.jouer();

  }
}
