package NNetworks.DoubleEvolutionNetwork;

import NNetworks.EvolutionaryNeuronNetwork;
import NNetworks.NetworkEvolution;
import NNetworks.NeuronBetter;
import NNetworks.NeuronNetwork;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.TreeSet;

/**
 * Created by Lach on 2016-12-03.
 */
public class NPCNetworkBreeder extends NetworkEvolution implements Serializable{

    protected NPCNetwork[] networks;
    protected int weightsAttackCount,weightsMovementCount;
    int randTestNum;
    int[] nerPerAttack,nerPerMovement;

    public String GetAttackFormat()
    {
        String ret="";
        for(int i=0;i<networks[0].attack.GetLayerNumber();i++)
        {
            ret += String.valueOf(networks[0].attack.AccessLayer(i).GetNeuronNumber());
            ret += " ";
        }
        return ret;
    }

    protected void setRepeatMultipleNumber(int index)
    {
        networks[index].setRepeatMultipleNumber(1);
    }
    public String GetMovementFormat()
    {
        String ret="";
        for(int i=0;i<networks[0].movement.GetLayerNumber();i++)
        {
            ret += String.valueOf(networks[0].movement.AccessLayer(i).GetNeuronNumber());
            ret += " ";
        }
        return ret;
    }
    public NPCNetworkBreeder()
    {
        Random rand = new Random();
        weightsAttackCount=0;
        weightsMovementCount=0;
        randTestNum = rand.nextInt(1000);
    }
    public NPCNetwork GetNPCNetwork(int i)
    {
        return networks[i];
    }
    public void Init(int[] neuronPerAttackLayer,int[] neuronPerMovementLayer, int networks_n)
    {
        nerPerAttack = neuronPerAttackLayer;
        nerPerMovement = neuronPerMovementLayer;
        networks = new NPCNetwork[networks_n];
        for(int i=0;i<networks_n;i++)
        {
            networks[i] = new NPCNetwork(new NeuronBetter());
            networks[i].attack.Init(neuronPerAttackLayer);
            networks[i].movement.Init(neuronPerMovementLayer);
        }
        CountWeights();
    }
    public void EmptyInit(int networks_n)
    {
        networks = new NPCNetwork[networks_n];
    }
    public void PartialInit(int[] neuronPerAttackLayer,int[] neuronPerMovementLayer, int index)
    {
        networks[index] = new NPCNetwork(new NeuronBetter());
        networks[index].attack.Init(neuronPerAttackLayer);
        networks[index].movement.Init(neuronPerMovementLayer);
        if(index==0) CountWeights();
    }
    public NPCNetworkBreeder(int[] neuronPerAttackLayer,int[] neuronPerMovementLayer, int networks_n)
    {
        Random rand = new Random();
        weightsAttackCount=0;
        weightsMovementCount=0;
        randTestNum = rand.nextInt(1000);
        Init(neuronPerAttackLayer,neuronPerMovementLayer,networks_n);
    }

    public int getRandTestNum() {
        return randTestNum;
    }
    public int getNetworksCount() { return networks.length;}

    protected void CountWeights()
    {
        weightsAttackCount = 0;
        NeuronNetwork lA = networks[0].attack;
        for(int i=0;i<lA.GetLayerNumber();i++)
        {
            for(int j=0;j<lA.AccessLayer(i).GetNeuronNumber();j++)
            {
                for(int k=0;k<lA.AccessLayer(i).AccessNeuron(j).GetEntriesSize();k++)
                {
                    weightsAttackCount++;
                }
            }
        }

        weightsMovementCount = 0;
        NeuronNetwork lM = networks[0].movement;
        for(int i=0;i<lM.GetLayerNumber();i++)
        {
            for(int j=0;j<lM.AccessLayer(i).GetNeuronNumber();j++)
            {
                for(int k=0;k<lM.AccessLayer(i).AccessNeuron(j).GetEntriesSize();k++)
                {
                    weightsMovementCount++;
                }
            }
        }
    }
    public NeuronNetwork GetNetwork(int i,NPCNetworkType choice)
    {
        if(choice==NPCNetworkType.attack)
        return networks[i].attack;
        else return networks[i].movement;
    }
    protected double[] getWeights(int networkIndex,NPCNetworkType choice)
    {
        double[] ret = new double[weightsAttackCount];
        int counter = 0;

        NeuronNetwork l;
        if(choice==NPCNetworkType.attack)
            l = networks[networkIndex].attack;
        else
            l = networks[networkIndex].movement;

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
    protected double[] getWeights(NPCNetwork choosen,NPCNetworkType choice) {

        double[] ret = new double[weightsAttackCount];
        int counter = 0;

        NeuronNetwork l;
        if(choice==NPCNetworkType.attack)
            l = choosen.attack;
        else
            l = choosen.movement;

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
    protected void setWeights(int networkIndex, double[] weights,NPCNetworkType choice)
    {
        if(weights.length != weightsCount) return;
        int counter = 0;

        NeuronNetwork l;
        if(choice==NPCNetworkType.attack)
            l = networks[networkIndex].attack;
        else
            l = networks[networkIndex].movement;


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
    protected void setWeights(NPCNetwork choosen, double[] weights,NPCNetworkType choice)
    {
        if(weights.length != weightsCount) return;
        int counter = 0;

        NeuronNetwork l;
        if(choice==NPCNetworkType.attack)
            l = choosen.attack;
        else
            l = choosen.movement;


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

    protected void ResetGrades()
    {

        for(int i=0;i<networks.length;i++)
        {
            networks[i].setGrade(0);
            networks[i].ResetGradesDone();
            System.out.println("zresetowalem!");
        }
    }

    public void SortByGrades()
    {
        Arrays.sort(networks, new Comparator<NPCNetwork>() {
            public int compare(NPCNetwork o1, NPCNetwork o2) {
                return o2.compareTo(o1);
            }
        });
    }


    private double[][][] GetChampionsGrades()
    {
        double[][][] ret = new double[championsToPreserveNumber][][];
        for(int i=0;i<championsToPreserveNumber;i++)
        {
            ret[i] = new double[2][];
            ret[i][0] = getWeights(i,NPCNetworkType.attack);
            ret[i][1] = getWeights(i,NPCNetworkType.movement);
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
        double[][][] source = GetChampionsGrades();

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
                            setWeights(k, mixWeights(source[i][0], source[j][0]),NPCNetworkType.attack);
                            setWeights(k++, mixWeights(source[i][1], source[j][1]),NPCNetworkType.movement);
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
    public void NextGeneration2()
    {
        double range=0;
        for(int i=0;i<networks.length;i++)
        {
            double tymcz = networks[i].getGrade();
            networks[i].setGrade(range);
            range += tymcz;
        }
        TreeSet<NPCNetwork> chances = new TreeSet<NPCNetwork>(new Comparator<NPCNetwork>() {
            public int compare(NPCNetwork o1, NPCNetwork o2) {
                return o2.compareTo(o1);
            }
        });

        for(int i=0;i<networks.length;i++) {

            chances.add(networks[i]);
        }

        NPCNetwork[] choosen = new NPCNetwork[networks.length];
        Random rand = new Random();
        NPCNetwork randomPurposeOnly = new NPCNetwork(new NeuronBetter());

        for(int i=0;i<networks.length;i++) {
            randomPurposeOnly.setGrade(rand.nextDouble()%range);
            choosen[i] = chances.floor(randomPurposeOnly);
        }

        double[][][] newGrades = new double[networks.length][][];

        for(int i=0;i<networks.length;i+=2)
        {
            newGrades[i] = new double[2][];
            newGrades[i][0] = mixWeights(getWeights(choosen[i],NPCNetworkType.attack),getWeights(choosen[i+1],NPCNetworkType.attack));
            newGrades[i][1] = mixWeights(getWeights(choosen[i],NPCNetworkType.movement),getWeights(choosen[i+1],NPCNetworkType.movement));
            newGrades[i+1] = new double[2][];
            newGrades[i+1][0] = mixWeights(getWeights(choosen[i],NPCNetworkType.attack),getWeights(choosen[i+1],NPCNetworkType.attack));
            newGrades[i+1][1] = mixWeights(getWeights(choosen[i],NPCNetworkType.movement),getWeights(choosen[i+1],NPCNetworkType.movement));
        }

        for(int i=0;i<networks.length;i++)
        {

            setWeights(i,newGrades[i][0],NPCNetworkType.attack);
            setWeights(i,newGrades[i][1],NPCNetworkType.movement);

        }

        ResetGrades();
        //        dodaj mutacje
    }


    public void SaveToFile(String filePath)
    {
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(filePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static NPCNetworkBreeder OpenFromFile(String filePath)
    {
        NPCNetworkBreeder e = null;
        try {
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            e = (NPCNetworkBreeder) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException i) {
            i.printStackTrace();
            return e;
        }catch(ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return e;
        }
        return e;
    }

}
