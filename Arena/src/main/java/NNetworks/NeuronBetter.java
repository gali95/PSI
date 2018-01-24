/**
 * Created by Lach on 2016-10-19.
 */
package NNetworks;

import java.io.Serializable;

public class NeuronBetter extends Neuron implements Serializable{

    private double error;

    public void CalcExit()
    {
        double sum = 0;
        for (int i = 0; i < entriesL; i++) {
            sum += entries[i].getValue() * entries[i].getWeight();
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
