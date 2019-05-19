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

    e.afficher();

    e.jouer();
    //e.bouger(e.get_case(0,1),0,3);
    //e.bouger(e.get_case(1,6),1,4);
    //e.afficher();
    //System.out.println(e.get_case(0,1).toString());
    //e.bouger(e.get_case(0,1),0,3);
    //System.out.println(e.mouv_possible(e.get_case(0,0),0,2));

    //System.out.println(e.get_case(0,0).mouv_possible(0,2));
    //int[] tab=e.get_case(1,6).direction(1,4);
    //System.out.println(tab[0]+","+tab[1]);
    //System.out.println(e.get_case(1,6).get_couleur());


  }
}
