/**
 * Created by Lach on 2016-10-16.
 */
public class NeuronNetworkTeacher extends NeuronTeacher{

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
        subject = new NeuronNetwork();
        subject.Init(neuronPerLayer);
    }
    public void TestNetwork()
    {
        if((testEntries.length != subject.AccessLayer(0).GetNeuronNumber()) || (testResult.length != subject.AccessLayer(subject.GetLayerNumber()-1).AccessNeuron(0).GetEntriesSize()))
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
    public void ModifyWeights(Neuron dat)
    {
        for(int i=0;i<dat.GetEntriesSize();i++)
        {
            dat.AccessEntry(i).setWeight(dat.AccessEntry(i).getWeight()+(n*(err.CountError())*dat.AccessEntry(i).getValue()));
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


}
