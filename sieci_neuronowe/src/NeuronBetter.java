/**
 * Created by Lach on 2016-10-19.
 */
public class NeuronBetter extends Neuron {

    public void CalcExit()
    {
        double sum = 0;
        for (int i = 0; i < entries.size(); i++) {
            sum += entries.get(i).getValue() * entries.get(i).getWeight();
        }

        double rozw = (double)1/((double)1+Math.exp(-b*sum));
        exit.setValue(rozw);

    }
    public Neuron NewNeuron()
    {
        return new NeuronBetter();
    }
}
