/**
 * Created by Lach on 2016-10-16.
 */
public class NeuronNetworkTeacher extends NeuronTeacher{

    Neuron type;

    public Neuron getType() {
        return type;
    }

    public void setType(Neuron type) {
        this.type = type;
    }

    NeuronNetworkTeacher()
    {
        type = new Neuron();
        n = 0.01;
    }

    NeuronNetwork subject;

    double[] testResult;
    double[] networkResult;
    NeuronErrorCounter err;

    public void SetErrorCounter(NeuronErrorCounter erro)
    {
        err = erro;
    }
    public void CreateNetwork(int[] neuronPerLayer)
    {
        subject = new NeuronNetwork(type);
        subject.Init(neuronPerLayer);
    }
    public void TestNetwork()
    {

        if((testEntries.length != subject.AccessLayer(0).GetNeuronNumber()) || (testResult.length != subject.AccessLayer(subject.GetLayerNumber()-1).GetNeuronNumber()))
        {
            correctResult = false;
            return;
        }
        for(int i=0;i<testEntries.length;i++)
        {
            subject.AccessLayer(0).AccessNeuron(i).setExitValue(testEntries[i]);
        }
        for(int i=1;i<subject.GetLayerNumber();i++)
        {
            subject.AccessLayer(i).CalcExits();
        }
        networkResult = new double[testResult.length];
        for(int i=0;i<networkResult.length;i++)
        {
            networkResult[i] = subject.AccessLayer(subject.GetLayerNumber()-1).AccessNeuron(i).getExitValue();
        }
        correctResult = true;
    }
    public void InitErr()
    {
        err.networkResult = networkResult;
        err.properResult = testResult;
    }
    private void BackwardErrorPropagation()
    {
        for(int i=0;i<subject.AccessLayer(subject.GetLayerNumber()-1).GetNeuronNumber()-1;i++)
        {
            ((NeuronBetter)subject.AccessLayer(subject.GetLayerNumber()-1).AccessNeuron(i)).setLastLayerError(testResult[i]);
        }
        for(int i=subject.GetLayerNumber()-2;i>0;i--)
        {
            for(int j=0;j<subject.AccessLayer(i).GetNeuronNumber();j++)
            {
                ((NeuronBetter)subject.AccessLayer(i).AccessNeuron(j)).setError();
            }
        }
    }
    private void ModifyWeights(Neuron dat)
    {
        for(int i=0;i<dat.GetEntriesSize();i++)
        {
            double old_waga = dat.AccessEntry(i).getWeight();
            double blad = err.CountError();//((NeuronBetter)dat).getError();
            double wejscie = dat.AccessEntry(i).getValue();
            dat.AccessEntry(i).setWeight(old_waga+(n*blad*wejscie));
        }
    }
    public void ModifyWeights()
    {
        for(int i=1;i<subject.GetLayerNumber();i++)
        {
            for(int j=0;j<subject.AccessLayer(i).GetNeuronNumber();j++)
            {
                ModifyWeights(subject.AccessLayer(i).AccessNeuron(j));
            }
        }
    }
    public void SetTestData()
    {
        // overload
    }
    public double GetNetworkQuality()
    {
        // overload
        return 0;
    }
    public void RunRoutine()
    {
        SetTestData();
        TestNetwork();
        if(correctResult==false) return;

    }
    public void LearningAfterRunRoutine()
    {
        if(correctResult==false) return;
        InitErr();
        //BackwardErrorPropagation();
        ModifyWeights();
    }
    public void LearningRoutine()
    {
        RunRoutine();
        LearningAfterRunRoutine();
    }
    public double GetAnswerQuality()
    {
        RunRoutine();
        InitErr();
        return err.CountError();
    }


}
