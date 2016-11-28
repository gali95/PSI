import java.awt.*;

/**
 * Created by Smaug on 2016-10-14.
 */
public class Connection {

    public Perceptron perceptron; //neuron bedacy celem polaczenia oraz waga polaczenia
    public double weight;

    public Connection(Perceptron n){
        this.perceptron=n;
        this.weight=1.0;
    }
    public Connection(Perceptron n, double w){
        this.perceptron=n;
        this.weight=w;
    }
}
