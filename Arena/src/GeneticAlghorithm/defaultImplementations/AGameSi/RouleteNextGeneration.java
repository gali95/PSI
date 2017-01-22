package GeneticAlghorithm.defaultImplementations.AGameSi;

import GeneticAlghorithm.basicClassesInterfaces.GeneMixer;
import GeneticAlghorithm.basicClassesInterfaces.Geneable;
import GeneticAlghorithm.basicClassesInterfaces.NextGenerationCreator;
import NNetworks.DoubleEvolutionNetwork.NPCNetwork;
import NNetworks.DoubleEvolutionNetwork.NPCNetworkType;
import NNetworks.NeuronBetter;

import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.TreeSet;

/**
 * Created by Lach on 2016-12-25.
 */
public class RouleteNextGeneration implements NextGenerationCreator {  // jest to implementacja selekcji osobników za pomocą metody ruletki, glowna funkcja zwraca nowe pokolenie na podstawie wejsciowego
    @Override
    public Geneable[] NewGeneration(Geneable[] population, GeneMixer mixer) {
        double range=0;

        double min = 0;
        for(int i=0;i<population.length;i++)
        {
            double tempGrade = population[i].GetGrades();
            if(tempGrade<min) min = tempGrade;
        }
        if(min==0)
        {
            for(int i=0;i<population.length;i++)
            {
                population[i].SetGrades(population[i].GetGrades()+1);
            }
        }


        for(int i=0;i<population.length;i++)
        {
            double tymcz = population[i].GetGrades();
            if(tymcz<0) tymcz = 0;
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
            choosen[i] = chances.ceiling(randomPurposeOnly);
            if(choosen[i] == null)
            {

                System.out.println(chances.last().GetGrades() + " " + chances.first().GetGrades() + " " + randomPurposeOnly.GetGrades() + " " + range);
            }
        }

        ////////

        Object[][] newGenes = new Object[population.length][];

        TwoSidedWeightsMixer mixerCasted = (TwoSidedWeightsMixer)mixer;

        for(int i=0;i<population.length;i+=2)
        {
            newGenes[i] = mixerCasted.MixGenes(choosen[i],choosen[i+1],true);
            newGenes[i+1] = mixerCasted.MixGenes(choosen[i],choosen[i+1],false);
            mixerCasted.WipeSeed();
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
