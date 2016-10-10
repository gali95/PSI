import java.awt.*;
import java.util.LinkedList;

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

    public Neuron()
    {
        entries = new java.util.LinkedList<NeuronWej>();
        exit = new NeuronWyj();
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
        entries.add(new NeuronWej());
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

}
