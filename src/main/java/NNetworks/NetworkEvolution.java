package NNetworks;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * Created by Lach on 2016-11-14.
 */
public class NetworkEvolution implements Serializable{

    protected EvolutionaryNeuronNetwork[] networks;
    protected int weightsCount;

    public NetworkEvolution()
    {
        weightsCount = 0;
    }
    public void Init(int[] neuronPerLayer, int networks_n)
    {
        networks = new EvolutionaryNeuronNetwork[networks_n];
        for(int i=0;i<networks_n;i++)
        {
            networks[i] = new EvolutionaryNeuronNetwork(new NeuronBetter());
            networks[i].Init(neuronPerLayer);
        }
        CountWeights();
    }
    protected void CountWeights()
    {
        weightsCount = 0;
        NeuronNetwork l = networks[0];
        for(int i=0;i<l.GetLayerNumber();i++)
        {
            for(int j=0;j<l.AccessLayer(i).GetNeuronNumber();j++)
            {
                for(int k=0;k<l.AccessLayer(i).AccessNeuron(j).GetEntriesSize();k++)
                {
                    weightsCount++;
                }
            }
        }
    }
    protected int GetNetworksCount()
    {
        return networks.length;
    }
    public NeuronNetwork GetNetwork(int i)
    {
        return networks[i];
    }
    protected double[] getWeights(int networkIndex)
    {
        double[] ret = new double[weightsCount];
        int counter = 0;

        NeuronNetwork l = networks[networkIndex];
        for(int i=0;i<l.GetLayerNumber();i++)
        {
            for(int j=0;j<l.AccessLayer(i).GetNeuronNumber();j++)
            {
                for(int k=0;k<l.AccessLayer(i).AccessNeuron(j).GetEntriesSize();k++)
                {
                    ret[counter++] = l.AccessLayer(i).AccessNeuron(j).AccessEntry(k).getWeight();
                }
            }
        }
        return ret;

    }
    protected void setWeights(int networkIndex, double[] weights)
    {
        if(weights.length != weightsCount) return;
        int counter = 0;

        NeuronNetwork l = networks[networkIndex];
        for(int i=0;i<l.GetLayerNumber();i++)
        {
            for(int j=0;j<l.AccessLayer(i).GetNeuronNumber();j++)
            {
                for(int k=0;k<l.AccessLayer(i).AccessNeuron(j).GetEntriesSize();k++)
                {
                    l.AccessLayer(i).AccessNeuron(j).AccessEntry(k).setWeight(weights[counter++]);
                }
            }
        }
    }
    protected double[] mixWeights(double[] firsts,double[] seconds)
    {
        double[] ret = null;
        if(firsts.length != seconds.length) return ret;
        ret = new double[firsts.length];
        for(int i=0;i<firsts.length/2;i++)
        {
            ret[i] = 0;
        }
        for(int i=firsts.length/2;i<firsts.length;i++)
        {
            ret[i] = 1;
        }
        Random random = new Random();
        double tymcz;
        int tymcz2;
        for(int i=0;i<firsts.length;i++)
        {
            tymcz2 = random.nextInt(firsts.length);
            tymcz = ret[i];
            ret[i] = ret[tymcz2];
            ret[tymcz2] = tymcz;
        }
        for(int i=0;i<ret.length;i++)
        {
            if(ret[i]==0) ret[i] = firsts[i];
            else ret[i] = seconds[i];
        }
        return ret;
    }
    protected void ResetGrades()
    {
        for(int i=0;i<networks.length;i++)
        {
            networks[i].setGrade(0);
        }
    }
    public void ChangeGrade(int network_index)
    {
        networks[network_index].setGrade(networks[network_index].getGrade() + network_index);
    }
    protected void SortByGrades()
    {
        Arrays.sort(networks, new Comparator<EvolutionaryNeuronNetwork>() {
            public int compare(EvolutionaryNeuronNetwork o1, EvolutionaryNeuronNetwork o2) {
                return o2.compareTo(o1);
            }
        });
    }

    public int championsToPreserveNumber;

    protected void setRepeatMultipleNumber(int index)
    {
        networks[index].setRepeatMultipleNumber(1);
    }

    private double[][] GetChampionsGrades()
    {
        double[][] ret = new double[championsToPreserveNumber][];
        for(int i=0;i<championsToPreserveNumber;i++)
        {
            ret[i] = getWeights(i);
        }
        return ret;
    }

    public void NextGeneration()
    {
        SortByGrades();
        int thingsLeft = networks.length;
        for(int i=0;i<championsToPreserveNumber;i++) {
            setRepeatMultipleNumber(i);
        }
        double[][] source = GetChampionsGrades();

        int k=0;
        while(k<thingsLeft)
        {
            for(int i=0;i<championsToPreserveNumber;i++)
            {
                for(int j=0;j<championsToPreserveNumber;j++)
                {
                    if(i!=j)
                    {
                        for(int l=0;l<networks[i].getRepeatMultipleNumber();l++) {
                            setWeights(k++, mixWeights(source[i], source[j]));
                            if(k==thingsLeft) break;
                        }
                    }
                    if(k==thingsLeft) break;
                }
                if(k==thingsLeft) break;
            }
        }

        ResetGrades();
    }


}
