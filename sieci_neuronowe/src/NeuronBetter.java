/**
 * Created by Lach on 2016-10-19.
 */
public class NeuronBetter extends Neuron {

    private double error;

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

    public double getError() {
        return error;
    }

    public void setLastLayerError(double properResult)
    {
        error = (exit.getValue() - properResult) * exit.getValue() * ( 1 - exit.getValue() );
       // System.out.println(error);
    }
    public void setError() {
        double ret = exit.getValue() * (1 - exit.getValue());
        double sum = 0;
        for (int i = 0; i < exit.GetConnectionSize(); i++)
        {
            sum += ((NeuronBetter)exit.AccessConnection(i).getNeuron()).getError() * exit.AccessConnection(i).getWeight();
        }
        error = ret * sum;
        //System.out.println(error);
    }
}
