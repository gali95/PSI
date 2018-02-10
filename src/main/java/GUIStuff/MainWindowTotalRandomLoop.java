package GUIStuff;

import GeneticAlghorithm.basicClassesInterfaces.Geneable;
import GeneticAlghorithm.defaultImplementations.AGameSi.OnlyWeights.NeuronNetworkWeights;

/**
 * Created by Lach on 2016-12-29.
 */
public class MainWindowTotalRandomLoop implements Runnable{

    public Geneable best;
    public MainWindow sauce;

    public MainWindowTotalRandomLoop(MainWindow sauce) {
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

            sauce.TestujButton(1);

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



            sauce.SortBreeder();
            //if(!sauce.koniecPetli) sauce.NextGenerationButton();
            if(sauce.GetBestGrade() > best.GetGrades())
            {
                best.SetGenes(sauce.breeder2.getPopulation()[0].GetGenes());
                best.SetGrades(sauce.breeder2.getPopulation()[0].GetGrades());
                System.out.println(best.GetGrades());
            }
            if(!sauce.koniecPetli) sauce.breeder2.getGeneRand().SetRandomGenes(sauce.breeder2.getPopulation());
            sauce.breeder2.ResetGrades();

        }
        sauce.besto = best;
        System.out.println("Najlepsza ocena: " + sauce.besto.GetGrades());


    }

}
