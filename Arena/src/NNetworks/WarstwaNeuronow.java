package NNetworks;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by Lach on 2016-10-15.
 */


public class WarstwaNeuronow implements Serializable {

    Neuron neuronType;

    public Neuron getNeuronType() {
        return neuronType;
    }

    public void setNeuronType(Neuron neuronType) {
        this.neuronType = neuronType;
    }

    private Neuron[] content;

    WarstwaNeuronow()
    {

    }
    public void DodajPuste(int n)
    {
        content = new Neuron[n];
        for(int i=0;i<n;i++) content[i] = neuronType.NewNeuron();
    }
    public void RandomAllWeights()
    {
        for(int i=0;i<content.length;i++)
        {
            for(int j=0;j<content[i].GetEntriesSize();j++)
            {
                content[i].RandomWeights0to1();
            }
        }
    }

    public Neuron AccessNeuron(int i)
    {
        if(i<0) throw(new IndexOutOfBoundsException());
        if(i>=content.length) throw(new IndexOutOfBoundsException());

        return content[i];
    }
    public int GetNeuronNumber()
    {
        return content.length;
    }

    public void ConnectWithOtherWarstwa(WarstwaNeuronow other)
    {
        for(int i=0;i<content.length;i++)
        {
            content[i].SetExits(other.GetNeuronNumber());
        }
        for(int i=0;i<other.GetNeuronNumber();i++)
        {
            other.AccessNeuron(i).SetEntries(content.length);
        }
        for(int i=0;i<content.length;i++)
        {
            for(int j=0;j<other.GetNeuronNumber();j++)
            {
                content[i].ConnectWith(other.AccessNeuron(j));
            }
        }
    }

    public void CalcExits()
    {
        for(int i=0;i< GetNeuronNumber();i++)
        {
            AccessNeuron(i).CalcExit();
        }
    }

}
