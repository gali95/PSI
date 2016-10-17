/**
 * Created by Lach on 2016-10-09.
 */
public class NeuronWej {

    private double value;
    private double weight;
    private NeuronWyj connection;

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

    public NeuronWej() {

        value = 0;
        weight = 1;
        connection = null;
    }
}
