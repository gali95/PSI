import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Smaug on 2016-10-14.
 */
public class Layer {

    public ArrayList perceptrons;

    public Layer(int perceptronsAmount){

        try {
            if (perceptronsAmount < 1) {
                throw(new Exception("Perceptron amount cannot be smaller than 1"));
            }else{
                perceptrons=new ArrayList();
                for(int i=0;i<perceptronsAmount;i++){
                    perceptrons.add(new Perceptron());
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public void AddPerceptron(Perceptron x){
        perceptrons.add(x);
    }
    public void ConnectLayers(Layer w){
        for (Object x: perceptrons) {
            ((Perceptron) x).AddLayerDataInputs(w);
        }
    }
    public void SetOutputs(double []wy){
        for(int i=0;i<perceptrons.toArray().length;i++){
            ((Perceptron)perceptrons.toArray()[i]).output=wy[i];
        }
    }
    public double[] ReturnOutputs() {
        double[] wy = new double[perceptrons.toArray().length];
        for (int i = 0; i < perceptrons.toArray().length; i++) {
            wy[i] = ((Perceptron) perceptrons.toArray()[i]).output;
        }
        return wy;
    }
    public void CountAllPerceptronsOutputs(){
        for(Object x: perceptrons){
            ((Perceptron)x).CountOutput();
        }
    }
    public void ClearErrors(){
        for(Object x:perceptrons){
            ((Perceptron)x).error=0.0;
        }
    }
    public void CountErrors(double []correctOutputs){
        for(int i=0;i<perceptrons.toArray().length;i++){
            ((Perceptron)perceptrons.toArray()[i]).CountError(correctOutputs[i]);
        }
    }
    public void RandomWeights(double min, double max, Random R){
        for(Object x: perceptrons){
            ((Perceptron)x).RandomWeights(min,max,R);
        }
    }
    public void SetWeights(double n){
        for(Object x: perceptrons){
            ((Perceptron)x).ChangeWeight(n);
        }
    }
    public void ChangeErrorsBackwards(){
        for(Object x:perceptrons){
            ((Perceptron)x).ChangeErrorsBackwards();
        }
    }
}
