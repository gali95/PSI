/**
 * Created by Lach on 2016-11-26.
 */
public class Timee {
    public double time;
    public double sredniTime;
    public double iterations;

    public int hiddenLayers;
    public int neuronPerHiddenLayer;

    public int iloscProbekStat;

    public Timee(int hidLayers,int nPerLayer,int repeats)
    {
        time = 0;
        sredniTime = 0;
        iterations = 0;
        hiddenLayers = hidLayers;
        neuronPerHiddenLayer = nPerLayer;
        iloscProbekStat = repeats;
    }

    public Timee()
    {
        time = 0;
        sredniTime = 0;
        iterations = 0;
    }
    public void Divide()
    {
        time /= iloscProbekStat;
        sredniTime /= iloscProbekStat;
        iterations /= iloscProbekStat;
    }
    public String printableResult()
    {
        String ret = "Srednie wyniki dla " + hiddenLayers + " po " + neuronPerHiddenLayer + " neuronow ( " + iloscProbekStat + "):\n";
        ret += "Czas calej nauki: " + time +"\n";
        ret += "Czas jednej nauki: " + sredniTime + "\n";
        ret += "Ilosc iteracji do nauczenia sieci: " + iterations + "\n\n";
        return ret;
    }

}
