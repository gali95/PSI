import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Lach on 2016-10-09.
 */
public class Neuron {

    java.util.List<NeuronWej> entries;
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
            AccessEntry(i).setWeight((((double)random.nextInt(1001) / 1000)));//-0.5)*2);
        }
    }

    public Neuron()
    {
        entries = new java.util.LinkedList<NeuronWej>();
        exit = new NeuronWyj();
        b = 1;
    }

    public void CalcExit() {

        double sum = 0;
        for (int i = 0; i < entries.size(); i++) {
            sum += entries.get(i).getValue() * entries.get(i).getWeight();
        }
        sum += b;
        if (sum < 0) exit.setValue(0);
        else exit.setValue(1);

    }

    public void AddEntry() {
        entries.add(new NeuronWej(this));
    }

    public NeuronWej AccessEntry(int i)
    {
        if(i<0) throw(new IndexOutOfBoundsException());
        if(i>=entries.size()) throw(new IndexOutOfBoundsException());

        return entries.get(i);
    }

    public void EraseEntry(int i)
    {
        if(i<0) throw(new IndexOutOfBoundsException());
        if(i>=entries.size()) throw(new IndexOutOfBoundsException());

        entries.remove(i);
    }
    public int GetEntriesSize()
    {
        return entries.size();
    }

    int ConnectWith(Neuron other)
    {
        other.AddEntry();
        int i = other.GetEntriesSize() -1;
        other.AccessEntry(i).setConnection(exit);
        exit.AddConnection(other.AccessEntry(i));
        return 1;                 // TODO zmienic tak zeby zwracalo ilosc tych samych polaczen (ten samo wyjscie polaczone z tym samym neuronem)

    }

    public void setExitValue(double end)
    {
        exit.setValue(end);
    }

    public double[] getEntriesValue()
    {
        double[] ret = new double[entries.size()];
        for(int i=0;i<entries.size();i++)
        {
            ret[i] = entries.get(i).getValue();
        }
        return ret;
    }

    public Neuron NewNeuron()
    {
        return new Neuron();
    }



}
