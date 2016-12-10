package AActualGame;

import Interfaces.AHeritage;
import Matho.Vector2;
import NNetworks.DoubleEvolutionNetwork.NPCNetwork;

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
    public String enemy1,enemy2;
    public NPCNetwork choosen;
    public int choosenRange;

    public AGame()
    {
        roundStarted = false;
        h1 = null;
        h2 = null;
        timeLimited = true;
        gameStartTime = 60;
        repeatGame = true;
        wipeAll = false;
    }

    public AGame(String enemy1, String enemy2, NPCNetwork choosen,int choosenRange)
    {
        this.enemy1 = enemy1;
        this.enemy2 = enemy2;
        this.choosen = choosen;
        this.choosenRange = choosenRange;

        roundStarted = false;
        h1 = null;
        h2 = null;
        timeLimited = true;
        gameStartTime = 60;
        repeatGame = true;
        wipeAll = false;

    }

    public AHeritage CreateH1()
    {
        h1 = new ADisplayResults();
        return h1;
    }
    public AHeritage CreateH2()
    {
        h2 = new AEmptyHeritage();
        return h2;
    }

    private ACharacter CreateFirst()
    {
        if(enemy1.equals("Gracz"))
        {
            is1player = true;
            return CreatePlayer();
        }
        else if(enemy1.equals("ProsteSI"))
        {
            is1player = false;
            return CreateSimpleSI();
        }
        else
        {
            is1player = false;
            return CreateNNSi();
        }
    }

    private ACharacter CreateSecond()
    {
        if(enemy2.equals("Gracz"))
        {
            is2player = true;
            return CreatePlayer();
        }
        else if(enemy2.equals("ProsteSI"))
        {
            is2player = false;
            return CreateSimpleSI();
        }
        else
        {
            is2player = false;
            return CreateNNSi();
        }
    }

    public ACharacter CreateFirstDuelist()
    {
        is1player = false;
        return CreateNNSi();
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
    public ACharacter CreateNNSi(){
        ANeuralNetworkSIEnemy ret = null;
        try {
            ret = new ANeuralNetworkSIEnemy();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ret.setRange(choosenRange);
        ret.setSi(choosen);
        return ret;
    }

    public void StartGame()
    {

        if(roundStarted) return;



        spawnedFirst = CreateFirst();
        spawnedFirst.jury = this;
        if(is1player) targetLocation.player = (APlayer)spawnedFirst;
        targetLocation.InsertObject(spawnedFirst,(int)spawnPlace1.x,(int)spawnPlace1.y);
        if(h1 != null) spawnedFirst.watashiwasoru = h1;
        else spawnedFirst.watashiwasoru = CreateH1();

        //long tiem = System.nanoTime();


        spawnedSecond = CreateSecond();
        spawnedSecond.jury = this;
        if(is2player) targetLocation.player = (APlayer)spawnedSecond;
        targetLocation.InsertObject(spawnedSecond, (int)spawnPlace2.x,(int)spawnPlace2.y);
        if(h2 != null) spawnedSecond.watashiwasoru = h2;
        else spawnedSecond.watashiwasoru = CreateH2();
        spawnedFirst.enemy = spawnedSecond;
        spawnedSecond.enemy = spawnedFirst;
        if(timeLimited)
        {
            gameRemainingTime = gameStartTime;
        }
        roundStarted = true;

        //System.out.println((double)(System.nanoTime() - tiem)/1000000000);

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
    public double GetRemainingTime()
    {
        return gameRemainingTime/gameStartTime;
    }


}
