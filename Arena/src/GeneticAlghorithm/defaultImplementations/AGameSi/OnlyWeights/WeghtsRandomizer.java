package GeneticAlghorithm.defaultImplementations.AGameSi.OnlyWeights;

import GeneticAlghorithm.basicClassesInterfaces.GeneRandomizer;
import GeneticAlghorithm.basicClassesInterfaces.Geneable;

import java.util.Random;

/**
 * Created by Lach on 2016-12-26.
 */
public class WeghtsRandomizer implements GeneRandomizer {


    @Override
    public void SetRandomGenes(Geneable[] population) {
        if(population[0].getClass() != NeuronNetworkWeights.class) return;

        Object[] gen = population[0].GetGenes();

        Double[] rande = new Double[gen.length];
        Random random = new Random();

        for(int i=0;i<population.length;i++)
        {
            for(int j=0;j<gen.length;j++)
            {
                rande[j] = (((double)random.nextInt(1001) / 1000)-0.5)*2;
            }
            population[i].SetGenes(rande);
        }


    }
}
