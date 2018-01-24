package AActualGame.ATicTacToe;

/**
 * Created by Lach on 2017-01-01.
 */
public interface ATicTacToePlayer {

    public void YourTurn();
    public void EnemyTurn();
    public void SetGameResult(int result);
    public int GetMove();
    public int GetMoveState();
    public void SetTurnTime(float actualTime);
    public void SetID(int id);
    public int GetID();
    public void SetScore(int score);
    public int GetScore();
    public int GetCharacter();
    public void SetCharacter(int character);

}
