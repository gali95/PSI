package GeneticAlghorithm.defaultImplementations.AGameSi.Labirynth;

import GUIStuff.MainWindow;

public class TestUntilThread implements Runnable {

    private GeneticAlgorithmLabirynth sourceAlgorithm;
    private MainWindow fata;
    private int score;

    @Override
    public void run() {
        TestUntil(score);
    }

    public void init(GeneticAlgorithmLabirynth sourceAlgorithm, MainWindow fata,int score)
    {
        this.sourceAlgorithm = sourceAlgorithm;
        this.fata = fata;
        this.score = score;
    }

    public synchronized void TestUntil(int score) {

        while(true) {
            sourceAlgorithm.getTester().TestMultiple(sourceAlgorithm.getPopulation(), 1);
            fata.zakonczonoTestowanie = false;
            while (!fata.zakonczonoTestowanie) {  // waiting till tests are finished, before next gen can be created
                try {
                    this.wait(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(fata.GetStopOperationFlag())
                {
                    fata.OperationStopped();
                    fata.setTestUntilOngoing(false);
                    return;
                }
            }
            if(sourceAlgorithm.getPopulation()[0].GetGrades() >= score)
            {
                fata.OperationStopped();
                fata.setTestUntilOngoing(false);
                break;
            }

            sourceAlgorithm.setPopulation(sourceAlgorithm.getNextGen().NewGeneration(sourceAlgorithm.getPopulation(),sourceAlgorithm.getMixer()));
            sourceAlgorithm.MutateAll(sourceAlgorithm.mutationChance);
            sourceAlgorithm.ResetGrades();
        }

    }
}
