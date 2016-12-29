package GeneticAlghorithm.defaultImplementations.AGameSi;

import GeneticAlghorithm.basicClassesInterfaces.GeneMixer;
import GeneticAlghorithm.basicClassesInterfaces.Geneable;
import GeneticAlghorithm.basicClassesInterfaces.NextGenerationCreator;
import NNetworks.DoubleEvolutionNetwork.NPCNetwork;
import NNetworks.DoubleEvolutionNetwork.NPCNetworkType;
import NNetworks.NeuronBetter;

import java.util.Comparator;
import java.util.Random;
import java.util.TreeSet;

/**
 * Created by Lach on 2016-12-25.
 */
public class RouleteNextGeneration implements NextGenerationCreator {
    @Override
    public Geneable[] NewGeneration(Geneable[] population, GeneMixer mixer) {
        double range=0;
        for(int i=0;i<population.length;i++)
        {
            double tymcz = population[i].GetGrades();
            population[i].SetGrades(range);
            range += tymcz;
        }
        TreeSet<Geneable> chances = new TreeSet<Geneable>(new Comparator<Geneable>() {
            public int compare(Geneable o1, Geneable o2) {
                return o2.compareTo(o1);
            }
        });

        for(int i=0;i<population.length;i++) {

            chances.add(population[i]);
        }

        Geneable[] choosen = new Geneable[population.length];
        Random rand = new Random();
        Geneable randomPurposeOnly = population[0].CreateEmptyChild();

        for(int i=0;i<population.length;i++) {
            randomPurposeOnly.SetGrades(rand.nextDouble()*range);
            choosen[i] = chances.floor(randomPurposeOnly);
        }


        ////////

        Object[][] newGenes = new Object[population.length][];

        for(int i=0;i<population.length;i+=2)
        {
            newGenes[i] = mixer.MixGenes(choosen[i],choosen[i+1],true);
            newGenes[i+1] = mixer.MixGenes(choosen[i],choosen[i+1],false);
        }

        Geneable newPopulation[] = new Geneable[population.length];
        for(int i=0;i<population.length;i++)
        {
            newPopulation[i] = population[i].CreateEmptyChild();
        }

        for(int i=0;i<population.length;i++)
        {

            newPopulation[i].SetGenes(newGenes[i]);

        }

        return newPopulation;
    }
}
