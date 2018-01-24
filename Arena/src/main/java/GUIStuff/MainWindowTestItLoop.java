package GUIStuff;

import GeneticAlghorithm.basicClassesInterfaces.Geneable;
import GeneticAlghorithm.defaultImplementations.AGameSi.OnlyWeights.NeuronNetworkWeights;

/**
 * Created by Lach on 2016-12-29.
 */
public class MainWindowTestItLoop implements Runnable{

    public MainWindow sauce;
    public Geneable best;

    MainWindowTestItLoop(MainWindow sauce)
    {
        this.sauce = sauce;
    }

    @Override
    public void run() {

        //sauce.TestujButton();
        //System.out.println(sauce.GetBestGrade());

        best = new NeuronNetworkWeights((NeuronNetworkWeights)sauce.breeder2.getPopulation()[0]);
        best.SetGenes(sauce.breeder2.getPopulation()[0].GetGenes());

        while(!sauce.koniecPetli)
        {

            sauce.TestujButton();

            while(!sauce.koniecTest)
            {
                synchronized (this){
                    try {
                        wait(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            System.out.println(sauce.GetBestGrade());
            if(sauce.GetBestGrade() > best.GetGrades())
            {
                best.SetGenes(sauce.breeder2.getPopulation()[0].GetGenes());
                best.SetGrades(sauce.breeder2.getPopulation()[0].GetGrades());
                System.out.println(best.GetGrades());
                sauce.besto = best;
            }
            if(!sauce.koniecPetli) sauce.NextGenerationButton();
        }


    }


}
