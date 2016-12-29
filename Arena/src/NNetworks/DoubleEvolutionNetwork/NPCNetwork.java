package NNetworks.DoubleEvolutionNetwork;


import GeneticAlghorithm.basicClassesInterfaces.Geneable;
import Interfaces.AHeritage;
import NNetworks.EvolutionaryNeuronNetwork;
import NNetworks.Neuron;
import NNetworks.NeuronBetter;
import NNetworks.NeuronNetwork;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by Lach on 2016-12-03.
 */
public class NPCNetwork implements Comparable<NPCNetwork>, AHeritage, Serializable, Geneable {

    public NeuronNetwork movement,attack;
    private double grade;
    private int gradesDone;
    private int repeatMultipleNumber;
    public Boolean gamesInProcess;

    public double getGrade() {
        System.out.print("NPCNetwork");
        return grade;
    }

    public void setGrade(double grade) {
        System.out.print("NPCNetwork");
        this.grade = grade;
    }

    public int getRepeatMultipleNumber() {
        System.out.print("NPCNetwork");
        return repeatMultipleNumber;
    }

    public int getGradesDone() {
        return gradesDone;
    }

    public void ResetGradesDone() {
        this.gradesDone = 0;
    }

    public void setRepeatMultipleNumber(int repeatMultipleNumber) {
        System.out.print("NPCNetwork");
        this.repeatMultipleNumber = repeatMultipleNumber;
    }

    public NPCNetwork()
    {
        movement = new EvolutionaryNeuronNetwork();
        attack = new EvolutionaryNeuronNetwork();
        gradesDone = 0;
        gamesInProcess = false;
    }
    public NPCNetwork(Neuron neuronType)
    {
        movement = new EvolutionaryNeuronNetwork(neuronType);
        attack = new EvolutionaryNeuronNetwork(neuronType);
    }

    public int compareTo(NPCNetwork other)
    {
        Double first = new Double(this.grade);
        Double second = new Double(other.grade);
        return (first.compareTo(second));
    }

    @Override
    public void GameEnded(double characterPoints) {
        grade += characterPoints;
        gradesDone++;
    }


    @Override
    public Object[] GetGenes() {
        Object[] ret = new Object[attack.weightsCount+movement.weightsCount];

        NeuronNetwork l = attack;
        //ret[0] = attack.weightsCount;
        int counter=0;

        for(int i=1;i<l.GetLayerNumber();i++)
        {
            for(int j=0;j<l.AccessLayer(i).GetNeuronNumber();j++)
            {
                for(int k=0;k<l.AccessLayer(i).AccessNeuron(j).GetEntriesSize();k++)
                {
                    ret[counter++] = l.AccessLayer(i).AccessNeuron(j).AccessEntry(k).getWeight();
                }
            }
        }

        l = movement;


        for(int i=1;i<l.GetLayerNumber();i++)
        {
            for(int j=0;j<l.AccessLayer(i).GetNeuronNumber();j++)
            {
                for(int k=0;k<l.AccessLayer(i).AccessNeuron(j).GetEntriesSize();k++)
                {
                    ret[counter++] = l.AccessLayer(i).AccessNeuron(j).AccessEntry(k).getWeight();
                }
            }
        }

        return ret;

    }

    @Override
    public void SetGenes(Object[] genes) {

        NeuronNetwork l = attack;
        int counter=0;

        for(int i=1;i<l.GetLayerNumber();i++)
        {
            for(int j=0;j<l.AccessLayer(i).GetNeuronNumber();j++)
            {
                for(int k=0;k<l.AccessLayer(i).AccessNeuron(j).GetEntriesSize();k++)
                {
                    l.AccessLayer(i).AccessNeuron(j).AccessEntry(k).setWeight((Double)genes[counter++]);
                }
            }
        }

        l = movement;

        for(int i=1;i<l.GetLayerNumber();i++)
        {
            for(int j=0;j<l.AccessLayer(i).GetNeuronNumber();j++)
            {
                for(int k=0;k<l.AccessLayer(i).AccessNeuron(j).GetEntriesSize();k++)
                {
                    l.AccessLayer(i).AccessNeuron(j).AccessEntry(k).setWeight((Double)genes[counter++]);
                    //System.out.println(i + " " + j + " " + k + " ");
                }
            }
        }

    }

    @Override
    public double GetGrades() {
        return getGrade();
    }

    @Override
    public void SetGrades(double grade) {
        setGrade(grade);
    }

    @Override
    public Geneable CreateEmptyChild() {
        NPCNetwork temp = (NPCNetwork)this;
        NPCNetwork ret = new NPCNetwork(new NeuronBetter());
        ret.attack = temp.attack.CopyWithoutWeights();
        ret.movement = temp.movement.CopyWithoutWeights();
        return ret;
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
