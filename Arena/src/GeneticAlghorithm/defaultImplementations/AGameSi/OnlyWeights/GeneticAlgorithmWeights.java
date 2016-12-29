package GeneticAlghorithm.defaultImplementations.AGameSi.OnlyWeights;

import GUIStuff.MainWindow;
import GUIStuff.ProgressLabel;
import GeneticAlghorithm.basicClassesInterfaces.Geneable;
import GeneticAlghorithm.basicClassesInterfaces.GeneticAlgorithm;
import GeneticAlghorithm.defaultImplementations.AGameSi.RouleteNextGeneration;
import GeneticAlghorithm.defaultImplementations.AGameSi.WeightsMixer;
import NNetworks.DoubleEvolutionNetwork.NPCNetwork;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;

import static sun.misc.Version.println;

/**
 * Created by Lach on 2016-12-26.
 */
public class GeneticAlgorithmWeights extends GeneticAlgorithm {

    public NPCNetwork pattern;
    public int SIRange;
    public MainWindow fata;
    public double mutationChance;

    public GeneticAlgorithmWeights(NPCNetwork pattern, MainWindow fata, ProgressLabel progressu,int size,int SIRange) {
        super();
        this.SIRange = SIRange;
        this.fata = fata;
        this.pattern = pattern;
        setGeneRand(new WeghtsRandomizer());
        setMixer(new WeightsMixer());
        setNextGen(new RouleteNextGeneration());
        setTester(new WeightsTester(pattern,fata,progressu,SIRange));
        CreateFreshPopulation(pattern,size);
        mutationChance = 0.01;
    }

    public void CreateFreshPopulation(NPCNetwork prototype,int size)
    {
        ((WeightsTester)getTester()).pattern = (NPCNetwork)prototype.CreateEmptyChild();
        Geneable[] pack = new NeuronNetworkWeights[size];
        for(int i=0;i<pack.length;i++)
        {
            pack[i] = new NeuronNetworkWeights(prototype.attack.weightsCount+prototype.movement.weightsCount);
        }
        setPopulation(pack);
        getGeneRand().SetRandomGenes(pack);
    }
    public void SaveToFile(String filePath)
    {
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(filePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            Geneable[] pop = getPopulation();
            out.writeObject(pattern);
            out.writeObject(SIRange);
            out.writeObject((pop.length));
            for(int i=0;i<pop.length;i++)
                out.writeObject(pop[i]);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static GeneticAlgorithmWeights OpenFromFile(String filePath, MainWindow fata, ProgressLabel progressu)
    {
        GeneticAlgorithmWeights e = null;// = new GeneticAlgorithmWeights(fata,progressu,size);
        try {
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            NPCNetwork ck = (NPCNetwork)in.readObject();
            int range = (int)in.readObject();
            int lng = (Integer) in.readObject();
            e = new GeneticAlgorithmWeights(ck,fata,progressu,lng,range);
            Geneable[] tab = new Geneable[lng];
            for(int i=0;i<lng;i++)
            {
                tab[i] = (NeuronNetworkWeights) in.readObject();
            }
            in.close();
            fileIn.close();
        }catch(IOException i) {
            i.printStackTrace();
            return e;
        }catch(ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return e;
        }
        return e;
    }

    public void SortByGrades()
    {
        Geneable[] c = getPopulation();
        Arrays.sort(c, new Comparator<Geneable>() {
            public int compare(Geneable o1, Geneable o2) {
                return o2.compareTo(o1);
            }
        });
        setPopulation(c);
    }

}
