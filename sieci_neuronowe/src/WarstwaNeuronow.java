import java.util.LinkedList;

/**
 * Created by Lach on 2016-10-15.
 */
public class WarstwaNeuronow {

    Neuron neuronType;

    public Neuron getNeuronType() {
        return neuronType;
    }

    public void setNeuronType(Neuron neuronType) {
        this.neuronType = neuronType;
    }

    private java.util.List<Neuron> content;

    WarstwaNeuronow()
    {
        content= new LinkedList<Neuron>();
    }
    public void DodajPuste(int n)
    {
        for(int i=0;i<n;i++) content.add(neuronType.NewNeuron());
        //content.get(n).setExitValue(1);
    }
    public void RandomAllWeights()
    {
        for(int i=0;i<content.size();i++)
        {
            for(int j=0;j<content.get(i).GetEntriesSize();j++)
            {
                content.get(i).RandomWeights0to1();
            }
        }
    }

    public void AddNeuron() {
        content.add(neuronType.NewNeuron());
    }
    public Neuron AccessNeuron(int i)
    {
        if(i<0) throw(new IndexOutOfBoundsException());
        if(i>=content.size()) throw(new IndexOutOfBoundsException());

        return content.get(i);
    }
    public void EraseNeuron(int i)
    {
        if(i<0) throw(new IndexOutOfBoundsException());
        if(i>=content.size()) throw(new IndexOutOfBoundsException());

        content.remove(i);
    }
    public int GetNeuronNumber()
    {
        return content.size();
    }

    public void ConnectWithOtherWarstwa(WarstwaNeuronow other)
    {
        for(int i=0;i<content.size();i++)
        {
            for(int j=0;j<other.GetNeuronNumber();j++)
            {
                content.get(i).ConnectWith(other.AccessNeuron(j));
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
