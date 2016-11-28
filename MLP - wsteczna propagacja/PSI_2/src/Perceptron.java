import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Smaug on 2016-10-05.
 */
public class Perceptron {

    double b; //polaryzacja/bias
    double error;
    double output;
    ArrayList dataInput; //jezeli neuron dostaje dane od innych neuronow

    public Perceptron(){
        b=0.0;
        error=0.0;
        output=0.0;
        dataInput=new ArrayList();
    }

    public void AddDataInput(Perceptron x){
        dataInput.add(new Connection(x,1.0));
    }
    public void AddDataInput(Perceptron x, double w){
        dataInput.add(new Connection(x,w));
    }
    public void AddLayerDataInputs(Layer w){
        for(Object x : w.perceptrons){
            AddDataInput((Perceptron)x);
        }
    }

    public void RandomWeights(double min, double max, Random R) {
        for (Object x : dataInput) {
            ((Connection) x).weight = (R.nextDouble() * (max - min)) + min;

           // System.out.println(((Connection) x).weight);
        }
        b = (R.nextDouble() * (max - min)) + min;
    }
    public void ChangeWeight(double n){
        for(Object x: dataInput){
            ((Connection)x).weight+=n*error*CountDerivative(output)*((Connection)x).perceptron.output;
        }
        b+=n*error*CountDerivative(output)*1.0;
    }
    public void CountError(double correctOutput){
        error=correctOutput-output;
    }
    public void CountOutput(){
        output=0.0;
        for(Object con : dataInput){
            output+=((Connection)con).weight*((Connection)con).perceptron.output;
        }
        output+=b*1.0;

        output=CountSin(output);
        //System.out.println(output);
    }
    private double CountSin(double x){

        double beta=1.0;
        return (1.0/(1.0+Math.pow(Math.E,-(beta*x))));
        /*
        if(x>=0)
            return 1;
        else
            return 0;
            */
    }
    private double CountDerivative(double x){
        return x*(1.0-x);
    }
    public void ChangeErrorsBackwards(){
        for(Object x:dataInput){
            ((Connection)x).perceptron.error+=error*((Connection)x).weight;
        }
    }

}
