/**
 * Created by Lach on 2016-10-09.
 */
package NNetworks;

import java.io.Serializable;

public class NeuronWej implements Serializable {

    private double value;
    private double weight;
    private NeuronWyj connection;
    private Neuron source;

    public NeuronWyj getConnection() {
        return connection;
    }

    public void setConnection(NeuronWyj connection) {
        this.connection = connection;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public NeuronWej(Neuron targ) {

        value = 0;
        weight = 1;
        connection = null;
        source = targ;
    }

    public Neuron getNeuron()
    {
        return source;
    }
}
