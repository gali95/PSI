import java.util.Comparator;

/**
 * Created by Lach on 2016-11-27.
 */
public class EvolutionaryNeuronNetwork extends NeuronNetwork implements Comparable<EvolutionaryNeuronNetwork>{

    double grade;
    int repeatMultipleNumber;

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
