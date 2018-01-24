package AActualGame.ATicTacToe;

import AverTimeMeasure.AvgTimeMeasure;

/**
 * Created by Lach on 2016-12-31.
 */
public class AGameLogic {

    int[] fields;
    ATicTacToePlayer[] players;
    int id_counter;

    public AGameLogic()
    {
        fields = new int[9];
        players = new ATicTacToePlayer[2];
        id_counter = 0;
    }
    public void SetPlayer(ATicTacToePlayer pl,int slot)
    {
        players[slot] = pl;
        pl.SetID(++id_counter);
    }
    public void RemovePlayer(int slot)
    {
        players[slot] = null;
    }
    public void ResetFields()
    {
        for(int i=0;i<fields.length;i++) fields[i] = 0;
    }
    public void ResetScores()
    {
        if(players[0] != null) players[0].SetScore(0);
        if(players[1] != null) players[1].SetScore(0);
    }
    public void SetCharacters(boolean firstGetsCircle)
    {
        if(firstGetsCircle) {
            players[0].SetCharacter(0);
            players[1].SetCharacter(1);
        }
        else
        {
            players[0].SetCharacter(0);
            players[1].SetCharacter(1);
        }

    }



}
