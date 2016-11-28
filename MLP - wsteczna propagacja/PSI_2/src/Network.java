import java.util.Random;

/**
 * Created by Smaug on 2016-10-14.
 */
public class Network {

    Layer wInput, wOutput;
    Layer[] wHidden;

    public Network(int sizeOfInput, int sizeOfHidden, int sizeOfOutput, int amountOfHidden){
        wInput=new Layer(sizeOfInput);
        wOutput=new Layer(sizeOfOutput);
        wHidden=new Layer[amountOfHidden];

        for(int i=0;i<amountOfHidden;i++) {
            wHidden[i] = new Layer(sizeOfHidden);
        }
        wOutput.ConnectLayers(wHidden[amountOfHidden-1]);

        for(int i=(amountOfHidden-1);i>0;i--){
            wHidden[i].ConnectLayers(wHidden[i-1]);
        }
        wHidden[0].ConnectLayers(wInput);
    }
    public void RandomWeights(double min, double max){
        Random rand = new Random();

        wOutput.RandomWeights(min, max,rand);
        for(int i=0;i<wHidden.length;i++){
            wHidden[i].RandomWeights(min, max, rand);
        }
    }
    public double[] CountOutput(double[] input){
        wInput.SetOutputs(input);
        for(int i=0;i<wHidden.length;i++){
            wHidden[i].CountAllPerceptronsOutputs();
        }
        wOutput.CountAllPerceptronsOutputs();
        return wOutput.ReturnOutputs();
    }

    public void Teach(double[] correctOutput, double[] input,double n){

        wOutput.ClearErrors();

        for(int i=0;i<wHidden.length;i++) {
            wHidden[i].ClearErrors();
        }

        CountOutput(input);

        wOutput.CountErrors(correctOutput);
        wOutput.ChangeErrorsBackwards();

        for(int i=(wHidden.length-1);i>0;i--){
            wHidden[i].ChangeErrorsBackwards();
        }

        wOutput.SetWeights(n);
        for(int i=0;i<wHidden.length;i++) {
            wHidden[i].SetWeights(n);
        }

    }
}
