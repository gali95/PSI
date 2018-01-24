package NNetworks;

import java.awt.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Lach on 2016-10-09.
 */
public class Neuron implements Serializable {

    NeuronWej[] entries;
    public int entriesL;
    NeuronWyj exit;
    double b;

    public double getB() {
        return b;
    }

    public double getExitValue()
    {
        return exit.getValue();
    }

    public void setB(double b) {
        this.b = b;
    }

    public void RandomWeights0to1()
    {
        Random random = new Random();
        for(int i=0;i<GetEntriesSize();i++) {
            AccessEntry(i).setWeight((((double)random.nextInt(1001) / 1000)-0.5)*2);
        }
    }

    public Neuron()
    {
        exit = new NeuronWyj(this);
        b = 1;
        entriesL = 0;
    }

    public void SetEntriesExitsSizes(int entrySize,int exitsSize)
    {
        SetEntries(entrySize);
        exit.SetConnections(exitsSize);
    }

    public void SetEntries(int entrySize)
    {
        entries = new NeuronWej[entrySize];
        for(int i=0;i<entrySize;i++)
        {
            entries[i] = new NeuronWej(this);
        }
    }

    public void SetExits(int exitsSize)
    {
        exit.SetConnections(exitsSize);
    }

    public void CalcExit() {

        double sum = 0;
        for (int i = 0; i < entriesL; i++) {
            sum += entries[i].getValue() * entries[i].getWeight();
        }
        sum += b;
        if (sum < 0) exit.setValue(0);
        else exit.setValue(1);

    }


    public NeuronWej AccessEntry(int i)
    {
        if(i<0) throw(new IndexOutOfBoundsException());
        if(i>=entries.length) throw(new IndexOutOfBoundsException());

        return entries[i];
    }

    public int GetEntriesSize()
    {
        return entries.length;
    }
    public int GetActualEntriesSize()
    {
        return entriesL;
    }

    int ConnectWith(Neuron other)
    {
        other.AccessEntry(other.entriesL).setConnection(exit);
        exit.AddConnection(other.AccessEntry(other.entriesL));
        other.entriesL++;

        //other.AddEntry();
        //int i = other.GetEntriesSize() -1;
        //other.AccessEntry(i).setConnection(exit);
        //exit.AddConnection(other.AccessEntry(i));
        return 1;                 // TODO zmienic tak zeby zwracalo ilosc tych samych polaczen (ten samo wyjscie polaczone z tym samym neuronem)

    }

    public void setExitValue(double end)
    {
        exit.setValue(end);
    }

    public double[] getEntriesValue()
    {
        double[] ret = new double[entriesL];
        for(int i=0;i<entriesL;i++)
        {
            ret[i] = entries[i].getValue();
        }
        return ret;
    }

    public Neuron NewNeuron()
    {
        return new Neuron();
    }



}
