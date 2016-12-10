package NNetworks.DoubleEvolutionNetwork;

import GUIStuff.MainWindow;
import GUIStuff.ProgressLabel;

/**
 * Created by Lach on 2016-12-08.
 */
public class NPCNetworkBreederPararelInit implements Runnable{

    public MainWindow fata;
    public NPCNetworkBreeder target;
    public  ProgressLabel pgLabel;
    public int[] attackPattern,movementPattern;
    public int index;

    public void run()
    {
        target.PartialInit(attackPattern,movementPattern,index);
        if(pgLabel.Increase()) fata.przerwijFlag=true;
        if(index==0) fata.SetBreederInfo();
    }
}
