import java.io.IOException;

/**
 * Created by Lach on 2016-11-12.
 */
public class AGame {

    public AMap targetLocation;
    public Vector2 spawnPlace1,spawnPlace2;
    public double gameStartTime,gameRemainingTime;
    public ACharacter spawnedFirst,spawnedSecond;
    public Boolean roundStarted;
    public Boolean timeLimited;
    public Boolean is1player,is2player;
    public AHeritage h1,h2;
    public Boolean repeatGame,wipeAll;

    public AGame()
    {
        roundStarted = false;
        h1 = null;
        h2 = null;
        timeLimited = true;
        gameStartTime = 20;
        repeatGame = true;
        wipeAll = false;
    }

    public AHeritage CreateH1()
    {
        h1 = new ADisplayResults();
        return h1;
        //TODO nowo utworzone AHeritage ma byc przypisane do h1
    }
    public AHeritage CreateH2()
    {
        h2 = new AHeritage();
        return h2;
    }

    public ACharacter CreateFirstDuelist()
    {
        is1player = true;
        return CreatePlayer();
    }
    public ACharacter CreateSecondDuelist()
    {
        is2player = false;
        return CreateSimpleSI();
    }
    public ACharacter CreatePlayer()
    {
        try {
            return new APlayer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ACharacter CreateSimpleSI()
    {
        try {
            return new ABasicSIEnemy();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ACharacter CreateNNSi()
    {
        return null;
    }

    public void StartGame()
    {
        if(roundStarted) return;

        spawnedFirst = CreateFirstDuelist();
        spawnedFirst.jury = this;
        if(is1player) targetLocation.player = (APlayer)spawnedFirst;
        targetLocation.InsertObject(spawnedFirst,(int)spawnPlace1.x,(int)spawnPlace1.y);
        if(h1 != null) spawnedFirst.watashiwasoru = h1;
        else spawnedFirst.watashiwasoru = CreateH1();
        spawnedSecond = CreateSecondDuelist();
        spawnedSecond.jury = this;
        if(is2player) targetLocation.player = (APlayer)spawnedSecond;
        targetLocation.InsertObject(spawnedSecond, (int)spawnPlace2.x,(int)spawnPlace2.y);
        if(h2 != null) spawnedSecond.watashiwasoru = h2;
        else spawnedSecond.watashiwasoru = CreateH2();
        if(timeLimited)
        {
            gameRemainingTime = gameStartTime;
        }
        roundStarted = true;

    }
    public void EndGame()
    {
        if(!roundStarted) return;
        roundStarted = false;
        if(!spawnedFirst.garbageDay)
        {
            spawnedFirst.Destroy();
        }
        if(!spawnedSecond.garbageDay)
        {
            spawnedSecond.Destroy();
        }
        spawnedFirst.location.GarbageRoutine();

        h1.GameEnded(CountResultFor(spawnedFirst));
        h2.GameEnded(CountResultFor(spawnedSecond));

        spawnedFirst = null;
        spawnedSecond = null;
        wipeAll = true;

    }

    public void TimeRunOut()
    {
        EndGame();
    }
    public void MyRoutine(double dTime)
    {
        if(timeLimited) {
            gameRemainingTime -= dTime;
            if (gameRemainingTime <= 0) {
                TimeRunOut();
            }
        }
        if(wipeAll)
        {
            wipeAll = false;
            targetLocation.RemoveAllObjects();
            if(repeatGame) StartGame();
        }
    }
    public double CountResultFor(ACharacter target)
    {
        ACharacter asked,other;
        if(target==spawnedFirst)
        {
            asked = spawnedFirst;
            other = spawnedSecond;
        }
        else
        {
            asked = spawnedSecond;
            other = spawnedFirst;
        }
        if(asked.HP > 0 && other.HP > 0)
        {
            return 0;
        }
        else if(asked.HP > 0)
        {
            return 1;
        }
        else
        {
            return -1;
        }

    }


}
