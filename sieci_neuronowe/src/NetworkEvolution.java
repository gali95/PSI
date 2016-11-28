import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * Created by Lach on 2016-11-14.
 */
public class NetworkEvolution {

    private EvolutionaryNeuronNetwork[] networks;
    int weightsCount;

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
    public void CountWeights()
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
    public int GetNetworksCount()
    {
        return networks.length;
    }
    public NeuronNetwork GetNetwork(int i)
    {
        return networks[i];
    }
    public double[] getWeights(int networkIndex)
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
    public void setWeights(int networkIndex, double[] weights)
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
    public double[] mixWeights(double[] firsts,double[] seconds)
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
            ret[i] = 0;
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
    public void ResetGrades()
    {
        for(int i=0;i<networks.length;i++)
        {
            networks[i].grade = 0;
        }
    }
    public void ChangeGrade(int network_index)
    {
        networks[network_index].grade += network_index;
    }
    public void SortByGrades()
    {
        Arrays.sort(networks, new Comparator<EvolutionaryNeuronNetwork>() {
            public int compare(EvolutionaryNeuronNetwork o1, EvolutionaryNeuronNetwork o2) {
                return o2.compareTo(o1);
            }
        });
    }
    // TODO ustawienia odnosnie tego jaka czesc usunac i zastapic nowymi

    public static void main(String[] args)
    {
        NetworkEvolution test = new NetworkEvolution();
        int[] warstwy = new int[3];
        warstwy[0] = 5;
        warstwy[1] = 5;
        warstwy[2] = 5;
        test.Init(warstwy,10);
        test.SortByGrades();
    }

}
