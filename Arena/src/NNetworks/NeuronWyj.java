package NNetworks;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by Lach on 2016-10-09.
 */

public class NeuronWyj implements Serializable {

    private Neuron source;
    private double value;
    private NeuronWej[] connections;
    public int connectionsL;

    public double getValue() {
        return value;
    }

    public void setValue(double wyj) {
        this.value = wyj;
        if(connections==null) return;
        for(int i=0;i<connections.length;i++)
        {
            connections[i].setValue(wyj);
        }
    }

    public NeuronWyj(Neuron source)
    {
        value = 0;
        connectionsL = 0;
        this.source = source;
    }

    public NeuronWyj(int connSize)
    {
        value = 0;
        connectionsL = 0;
        connections = new NeuronWej[connSize];
    }

    public void SetConnections(int connSize)
    {
        connections = new NeuronWej[connSize];
        for(int i=0;i<connSize;i++) connections[i] = new NeuronWej(source);
    }

    public void AddConnection(NeuronWej entry) {
        connections[connectionsL++] = entry;
    }

    public NeuronWej AccessConnection(int i)
    {
        if(i<0) throw(new IndexOutOfBoundsException());
        if(i>=connections.length) throw(new IndexOutOfBoundsException());

        return connections[i];
    }

    public int GetConnectionSize()
    {
        return connections.length;
    }

    public int GetActualConnectionsSize()
    {
        return connectionsL;
    }
}
