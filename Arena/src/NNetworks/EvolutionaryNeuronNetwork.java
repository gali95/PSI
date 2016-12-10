package NNetworks;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by Lach on 2016-11-27.
 */
public class EvolutionaryNeuronNetwork extends NeuronNetwork implements Comparable<EvolutionaryNeuronNetwork>, Serializable{

    private double grade;
    private int repeatMultipleNumber;

    public double getGrade() {
        System.out.print("EvolutionaryNeuronNetwork");
        return grade;
    }

    public void setGrade(double grade) {
        System.out.print("EvolutionaryNeuronNetwork");
        this.grade = grade;
    }

    public int getRepeatMultipleNumber() {
        System.out.print("EvolutionaryNeuronNetwork");
        return repeatMultipleNumber;
    }

    public void setRepeatMultipleNumber(int repeatMultipleNumber) {
        System.out.print("EvolutionaryNeuronNetwork");
        this.repeatMultipleNumber = repeatMultipleNumber;
    }



    public EvolutionaryNeuronNetwork()
    {
        super();
        grade = 0;
    }
    public EvolutionaryNeuronNetwork(Neuron type)
    {
        super(type);
        grade = 0;
    }
    public int compareTo(EvolutionaryNeuronNetwork other)
    {
        Double first = new Double(this.grade);
        Double second = new Double(other.grade);
        return (first.compareTo(second));
    }
}
