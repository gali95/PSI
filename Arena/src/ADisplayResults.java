/**
 * Created by Lach on 2016-11-12.
 */
public class ADisplayResults extends AHeritage {

    int playerScore,CPUScore;

    public ADisplayResults()
    {
        playerScore = 0;
        CPUScore = 0;
    }
    public void Display()
    {
        System.out.println(playerScore+":"+CPUScore);
    }
    public void GameEnded(double characterPoints)
    {
        if(characterPoints==1) playerScore++;
        if(characterPoints==-1) CPUScore++;
        if(characterPoints==0)
        {
            playerScore++;
            CPUScore++;
        }
        Display();
    }

}
