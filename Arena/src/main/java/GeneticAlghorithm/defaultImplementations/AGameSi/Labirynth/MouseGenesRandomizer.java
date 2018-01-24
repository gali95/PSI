package GeneticAlghorithm.defaultImplementations.AGameSi.Labirynth;

import GeneticAlghorithm.basicClassesInterfaces.GeneRandomizer;
import GeneticAlghorithm.basicClassesInterfaces.Geneable;

import java.util.Random;

/**
 * Created by Lach on 2017-01-02.
 */
public class MouseGenesRandomizer implements GeneRandomizer{


    @Override
    public void SetRandomGenes(Geneable[] population) {             // funkcja ktora ustawia losowe wartosci genów myszy, czyli jedna z mozliwych czterech wartosci od 1 do 4

        Random random = new Random();
        for(int i=0;i<population.length;i++)
        {
            Object[] genes = population[i].GetGenes();
            for(int j=0;j<genes.length;j++)
            {
                genes[j] = random.nextInt(4)+1;
            }
            population[i].SetGenes(genes);
        }
    }
}
