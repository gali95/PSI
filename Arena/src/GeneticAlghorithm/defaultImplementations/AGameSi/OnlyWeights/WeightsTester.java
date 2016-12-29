package GeneticAlghorithm.defaultImplementations.AGameSi.OnlyWeights;

import AActualGame.ATrainSIGame;
import GUIStuff.MainWindow;
import GUIStuff.ProgressLabel;
import GeneticAlghorithm.basicClassesInterfaces.GeneTester;
import GeneticAlghorithm.basicClassesInterfaces.Geneable;
import NNetworks.DoubleEvolutionNetwork.NPCNetwork;
import sun.applet.Main;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Lach on 2016-12-26.
 */
public class WeightsTester implements GeneTester {

    public NPCNetwork pattern;
    public MainWindow fata;
    public ProgressLabel progressu;
    public int SIRange;


    public WeightsTester(NPCNetwork pattern, MainWindow fata, ProgressLabel progressu,int SIRange) {
        this.pattern = pattern;
        this.fata = fata;
        this.progressu = progressu;
        this.SIRange = SIRange;
    }

    @Override
    public void TestOnce(Geneable[] population) {
        TestMultiple(population,1);
    }

    @Override
    public void TestMultiple(Geneable[] population, int testTimes) {

        int threadsNum = 8;

        ExecutorService executor = Executors.newFixedThreadPool(threadsNum);

        NPCNetwork[] sharedThreads = new NPCNetwork[threadsNum];
        for(int i=0;i<threadsNum;i++) sharedThreads[i] = (NPCNetwork)pattern.CreateEmptyChild();

        for (int i = 0; i < population.length; i++) {
            ATrainSIGame initer = new ATrainSIGame();
            int GamesToPlay = testTimes;
            initer.InitGame(sharedThreads,population[i],SIRange,10,10,GamesToPlay,60,0.02,threadsNum);
            initer.fata = fata;
            initer.progressu = progressu;
            initer.thatWasLast = false;
            if(i == population.length-1) initer.thatWasLast = true;
            executor.execute(initer);

        }
        executor.shutdown();

    }
}
