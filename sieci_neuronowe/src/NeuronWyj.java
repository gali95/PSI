import java.util.LinkedList;

/**
 * Created by Lach on 2016-10-09.
 */
public class NeuronWyj {

    private double value;
    private java.util.List<NeuronWej> connections;

    public double getValue() {
        return value;
    }

    public void setValue(double wyj) {
        this.value = wyj;
        for(int i=0;i<connections.size();i++)
        {
            connections.get(i).setValue(wyj);
        }
    }

    public NeuronWyj()
    {
        connections = new LinkedList<NeuronWej>();
        value = 0;
    }

    public void AddConnection(NeuronWej entry) {
        connections.add(entry);
    }

    public NeuronWej AccessConnection(int i)
    {
        if(i<0) throw(new IndexOutOfBoundsException());
        if(i>=connections.size()) throw(new IndexOutOfBoundsException());

        return connections.get(i);
    }

    public void EraseConnection(int i)
    {
        if(i<0) throw(new IndexOutOfBoundsException());
        if(i>=connections.size()) throw(new IndexOutOfBoundsException());

        connections.remove(i);
    }
    public int GetConnectionSize()
    {
        return connections.size();
    }
}
