/**
 * Created by Lach on 2016-10-16.
 */
package NNetworks;

import java.io.Serializable;

public class NeuronNetwork implements Serializable {

    Neuron neuronType;
    WarstwaNeuronow[] network;

    public NeuronNetwork()
    {
        neuronType = new Neuron();
    }
    public NeuronNetwork(Neuron type)
    {
        neuronType = type;
    }

    public void Init(int[] neuronsPerLayer)
    {
        network = new WarstwaNeuronow[neuronsPerLayer.length];
        for(int i=0;i<neuronsPerLayer.length;i++)
        {
            network[i] = new WarstwaNeuronow();
            network[i].setNeuronType(neuronType);
            network[i].DodajPuste(neuronsPerLayer[i]);
        }
        for(int i=0;i<neuronsPerLayer[0];i++)
        {
            network[0].AccessNeuron(i).SetEntries(1);
        }
        for(int i=0;i<neuronsPerLayer.length-1;i++)
        {
            network[i].ConnectWithOtherWarstwa(network[i+1]);
        }

        for(int i=1;i<neuronsPerLayer.length;i++)
        {
            network[i].RandomAllWeights();
        }
    }
    public void RandomWeights()
    {
        for(int i=1;i<network.length;i++)
        {
            network[i].RandomAllWeights();
        }
    }
    public  WarstwaNeuronow AccessLayer(int i)
    {
        if(i<0) throw(new IndexOutOfBoundsException());
        if(i>=network.length) throw(new IndexOutOfBoundsException());

        return network[i];
    }
    public int GetLayerNumber()
    {
        return network.length;
    }

    public double[] RunNetwork(double[] entries)
    {
        double[] networkResult = null;
        if((entries.length != AccessLayer(0).GetNeuronNumber()))
        {
            return networkResult;
        }
        for(int i=0;i<entries.length;i++)
        {
            AccessLayer(0).AccessNeuron(i).setExitValue(entries[i]);
        }
        for(int i=1;i<GetLayerNumber();i++)
        {
            AccessLayer(i).CalcExits();
        }
        networkResult = new double[AccessLayer(GetLayerNumber()-1).GetNeuronNumber()];
        for(int i=0;i<networkResult.length;i++)
        {
            networkResult[i] = AccessLayer(GetLayerNumber()-1).AccessNeuron(i).getExitValue();
        }
        return networkResult;
    }

    public static void main(String[] args)
    {
        NeuronNetwork tmp = new NeuronNetwork(new NeuronBetter());
        int[] entry = {300,200,100,100,100};
        long time = System.nanoTime();
        tmp.Init(entry);
        System.out.println("Czas: "+(double)(System.nanoTime()-time)/1000000000);

    }

}
