public class IHM
{
  public void afficher(Echequier e)
  {
    String ch="\n                                                                        ";
    ch+="\n                                                                        ";
    ch+="\n                                                                        ";


    for(int y=0;y<8;y++)
    {
      ch+="\n         |       |       |       |       |       |       |       |       |";
      ch+="\n    "+y+"    |";
      for(int x=0;x<8;x++)
      {
        ch+="   "+e.get_case(x,y).toString()+"   |";
      }
      ch+="\n         |       |       |       |       |       |       |       |       |";

    }

    ch+="\n                                                                 ";
    ch+="\n|   a   |   b   |   c   |   d   |   e   |   f   |   g   |   h   |";
    ch+="\n                                                                 ";

    System.out.println(ch);
   }
}
