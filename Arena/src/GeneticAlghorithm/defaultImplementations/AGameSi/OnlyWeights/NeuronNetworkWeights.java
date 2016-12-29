package GeneticAlghorithm.defaultImplementations.AGameSi.OnlyWeights;

import GeneticAlghorithm.basicClassesInterfaces.Geneable;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by Lach on 2016-12-25.
 */
public class NeuronNetworkWeights implements Geneable, Serializable {

    double[] weights;
    double grade;

    public NeuronNetworkWeights(int size)
    {
        weights = new double[size];
    }

    public NeuronNetworkWeights(NeuronNetworkWeights copy)
    {
        weights = new double[copy.GetGenes().length];
    }

    @Override
    public Object[] GetGenes() {
        Double[] ret = new Double[weights.length];
        for(int i=0;i<weights.length;i++) ret[i] = weights[i];
        return ret;
    }

    @Override
    public void SetGenes(Object[] genes) {
        weights = new double[genes.length];
        for(int i=0;i<genes.length;i++) weights[i] = (Double)genes[i];
    }

    @Override
    public double GetGrades() {
        return grade;
    }

    @Override
    public void SetGrades(double grade) {
        this.grade = grade;
    }

    @Override
    public Geneable CreateEmptyChild() {
        NeuronNetworkWeights temp = (NeuronNetworkWeights)this;
        return new NeuronNetworkWeights(temp);
    }

    @Override
    public void Mutate(double chanceTo) {
        Random random = new Random();
        Object[] taken = GetGenes();
        for(int i=0;i<taken.length;i++)
        {
            double randChange = random.nextDouble()%1.0;
            if(randChange<chanceTo)
            {
                taken[i] = (((double)random.nextInt(1001) / 1000)-0.5)*2;
            }
        }
        SetGenes(taken);
    }

}
