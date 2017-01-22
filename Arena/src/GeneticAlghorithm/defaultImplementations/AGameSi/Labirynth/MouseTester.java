package GeneticAlghorithm.defaultImplementations.AGameSi.Labirynth;

import AActualGame.ALabirynth.*;
import GUIStuff.MainWindow;
import GUIStuff.ProgressLabel;
import GeneticAlghorithm.basicClassesInterfaces.GeneTester;
import GeneticAlghorithm.basicClassesInterfaces.Geneable;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Lach on 2017-01-02.
 */
public class MouseTester implements GeneTester {          // przeladowane ponizej funkcje TestOnce i TestMultiple uruchamiaja srodowisko testowe i sprawdzaja jak poradzi w nim sobie dana populacja myszy, ustawiajac ich oceny

    public MainWindow fata;
    public ProgressLabel progressu;
    public double maxTestTime;

    public MouseTester(MainWindow fata, ProgressLabel progressu) {
        this.fata = fata;
        this.progressu = progressu;
        maxTestTime = 10;
    }

    @Override
    public void TestOnce(Geneable[] population) {
        TestMultiple(population,1);
    }

    @Override
    public void TestMultiple(Geneable[] population, int testTimes) {

        int threadsNum = 8;

        ExecutorService executor = Executors.newFixedThreadPool(threadsNum);

        progressu.SetNewProgress(testTimes*population.length);

        for (int i = 0; i < population.length; i++) {
            tempGameRunnableLauncher initer = new tempGameRunnableLauncher();//(ASIMouse)population[i],testTimes);
            initer.chosenToTest = (ASIMouse)population[i];
            initer.maxTime = maxTestTime;
            initer.timeTick = 0.11;
            initer.testNumber = testTimes;
            initer.fata = fata;
            initer.progressu = progressu;
            initer.visuallessSimulationMode = true;
            //initer.LogicInit();
            if(i == population.length-1) initer.thatWasLast = true;
            executor.execute(initer);

        }
        executor.shutdown();
        //fata.SortBreeder();
    }
}
