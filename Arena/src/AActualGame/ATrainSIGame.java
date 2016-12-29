package AActualGame;

import AverTimeMeasure.TempClass;
import GUIStuff.MainWindow;
import GUIStuff.ProgressLabel;
import GeneticAlghorithm.basicClassesInterfaces.Geneable;
import GeneticAlghorithm.defaultImplementations.AGameSi.OnlyWeights.NeuronNetworkWeights;
import Interfaces.AHeritage;
import Matho.Vector2;
import NNetworks.DoubleEvolutionNetwork.NPCNetwork;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Lach on 2016-12-03.
 */
public class ATrainSIGame extends AGame implements Runnable{

    public int mapSizeX,mapSizeY;
    public int gamesToPlay;
    public NPCNetwork source;
    public int sourceRange;
    double roundTick;
    Boolean finishedGames;
    Boolean initialized;
    public Boolean interrupt;
    public ProgressLabel progressu;
    public MainWindow fata;
    public Boolean thatWasLast;
    private Geneable selected;
    public boolean weightsOnly;

    public AHeritage CreateH1()
    {
        h1 = source;
        return h1;
    }
    public ATrainSIGame()
    {
        super();
        spawnPlace1 = new Vector2();
        spawnPlace2 = new Vector2();
        finishedGames = false;
        initialized = false;
        interrupt = true;
    }
    public void InitGame(NPCNetwork source,int sourceRange, int mapSizeX, int mapSizeY, int gamesToPlay, double roundTime, double roundTick)
    {
        this.source = source;
        this.sourceRange = sourceRange;
        this.mapSizeX = mapSizeX;
        this.mapSizeY = mapSizeY;
        this.gamesToPlay = gamesToPlay;
        this.gameStartTime = roundTime;
        this.roundTick = roundTick;
        CreateMyMap();
        initialized = true;
        weightsOnly = false;
    }
    public void InitGame(NPCNetwork[] sources, Geneable weights,int sourceRange, int mapSizeX, int mapSizeY, int gamesToPlay, double roundTime, double roundTick, int threadsNum)
    {
        this.source = sources[(int)(Thread.currentThread().getId()%threadsNum)];
        source.SetGenes(weights.GetGenes());
        selected = weights;
        this.sourceRange = sourceRange;
        this.mapSizeX = mapSizeX;
        this.mapSizeY = mapSizeY;
        this.gamesToPlay = gamesToPlay;
        this.gameStartTime = roundTime;
        this.roundTick = roundTick;
        CreateMyMap();
        initialized = true;
        weightsOnly = true;
    }
    private void CreateMyMap()
    {
        targetLocation = new AMap();
        targetLocation.InitEmpty(mapSizeX,mapSizeY,0);
        targetLocation.zarzadzacz = this;
    }
    private void RandomizeSpawns()
    {
        Random rand = new Random();
        spawnPlace1.x = rand.nextInt(mapSizeX);
        spawnPlace1.y = rand.nextInt(mapSizeY);
        spawnPlace2.x = rand.nextInt(mapSizeX);
        spawnPlace2.y = rand.nextInt(mapSizeY);
        while((int)spawnPlace1.y == (int)spawnPlace2.y)spawnPlace2.y = rand.nextInt(mapSizeY);

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

    public ACharacter CreateNNSi(){
        ANeuralNetworkSIEnemy ret = null;
        try {
            ret = new ANeuralNetworkSIEnemy();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ret.setSi(source);
        ret.setRange(sourceRange);
        if(weightsOnly) ret.thingToGrade = selected;
        return ret;
    }

    public double CountResultFor(ACharacter target)
    {
        double results = 0;
        /*
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

        results += asked.HP;
        results += ((other.HP - 100) * -1)*1;
        if(other.HP <= 0)
        {
            results += 300*3;
            results += GetRemainingTime() * 300;
        }
        */
        return results;
    }

    public void StartGame()
    {

        if(roundStarted) return;
        if(gamesToPlay<=0)
        {
            finishedGames = true;
            return;
        }

        gamesToPlay--;

        RandomizeSpawns();

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
        spawnedFirst.enemy = spawnedSecond;
        spawnedSecond.enemy = spawnedFirst;
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


        if(!weightsOnly) {
            h1.GameEnded(CountResultFor(spawnedFirst));
            h2.GameEnded(CountResultFor(spawnedSecond));
        }
        else
        {
            selected.SetGrades(selected.GetGrades()+CountResultFor(spawnedFirst));
        }

        if( progressu.Increase())
        {
            fata.przerwijFlag=false;

        }

        spawnedFirst = null;
        spawnedSecond = null;
        wipeAll = true;

    }

    @Override
    public void run() {

        if(!initialized) return;
        source.gamesInProcess = true;
        StartGame();
        while (!finishedGames && interrupt)
        {
            targetLocation.PhysicRoutine(roundTick);
        }
        source.gamesInProcess = false;
        if(thatWasLast)
        {
            fata.SortBreeder();
            fata.koniecTest = true;
        }

    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(8);
        for(int i=0;i<1000;i++)
        {
            TempClass fajn = new TempClass();
            executor.execute(fajn);
        }
        executor.shutdown();
    }
}
