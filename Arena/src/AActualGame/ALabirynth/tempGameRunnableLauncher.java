package AActualGame.ALabirynth;

import AActualGame.ACharacter;
import GUIStuff.MainWindow;
import GUIStuff.ProgressLabel;

/**
 * Created by Lach on 2017-01-03.
 */
public class tempGameRunnableLauncher implements Runnable {

    public ACharacter chosenToTest;
    public double maxTime,timeTick;
    public int testNumber;
    public MainWindow fata;
    public ProgressLabel progressu;
    public boolean visuallessSimulationMode,thatWasLast;
    public static int k;

    static
    {
        k=0;
    }

    @Override
    public void run() {

        if(!visuallessSimulationMode) {
            tempGame initer = new tempGame();//(ASIMouse)population[i],testTimes);

            initer.chosenToTest = chosenToTest;
            ((ASIMouse)chosenToTest).dontGrade = true;
            initer.maxTime = maxTime;
            initer.timeTick = timeTick;
            initer.testNumber = testNumber;
            initer.fata = fata;
            initer.progressu = progressu;
            initer.visuallessSimulationMode = visuallessSimulationMode;
            initer.LogicInit();

            initer.runWithGraphic();

        }
        else
        {
            ((ASIMouse)chosenToTest).dontGrade = false;
            AGame initer = new AGame(chosenToTest,testNumber);
            initer.maxTime = maxTime;
            initer.timeTick = timeTick;
            initer.fata = fata;
            initer.progressu = progressu;
            initer.times.init.Stop();

            initer.RunWIthoutGraphic();
            //System.out.println(k++);

        }
    }
}
