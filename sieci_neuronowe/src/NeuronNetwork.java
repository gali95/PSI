/**
 * Created by Lach on 2016-10-16.
 */
public class NeuronNetwork {

    WarstwaNeuronow[] network;

    public void Init(int[] neuronsPerLayer)
    {
        network = new WarstwaNeuronow[neuronsPerLayer.length];
        for(int i=0;i<neuronsPerLayer.length;i++)
        {
            network[i] = new WarstwaNeuronow();
            network[i].DodajPuste(neuronsPerLayer[i]);
        }

        for(int i=0;i<neuronsPerLayer.length-1;i++)
        {
            network[i].ConnectWithOtherWarstwa(network[i+1]);
        }

        for(int i=0;i<neuronsPerLayer.length;i++)
        {
            network[i].RandomAllWeights();
        }
    }
    public  WarstwaNeuronow AccessLayer(int i)
    {
        if(i<0) throw(new IndexOutOfBoundsException());
        if(i>=network.length) throw(new IndexOutOfBoundsException());

        return network[i];
    }
    public int GetLayerNumber()
    {
        return network.length;
    }

}
