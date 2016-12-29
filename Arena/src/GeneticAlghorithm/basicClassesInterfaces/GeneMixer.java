package GeneticAlghorithm.basicClassesInterfaces;

/**
 * Created by Lach on 2016-12-25.
 */
public interface GeneMixer {

    public default Object[] MixGenes(Geneable g1, Geneable g2)
    {
        if(g1.AreGenesCompatible(g2.GetGenes()))
        {
            return  ActualMixing(g1,g2);
        }
        else
            return null;
    }
    public Object[] ActualMixing(Geneable g1,Geneable g2);  // here actual mixing should happen
    public default Geneable CreateChild(Geneable g1, Geneable g2)
    {
        Object[] mixed = MixGenes(g1,g2);
        if(mixed==null) return null;
        Geneable ret = g1.CreateEmptyChild();
        ret.SetGenes(mixed);
        return ret;
    }
    public default Geneable CreateChild(Geneable g1, Geneable g2,boolean side)
    {
        Object[] mixed = MixGenes(g1,g2,side);
        if(mixed==null) return null;
        Geneable ret = g1.CreateEmptyChild();
        ret.SetGenes(mixed);
        return ret;
    }
    public default Object[] MixGenes(Geneable g1, Geneable g2, boolean side)         // used for ActualMixing with "side" value
    {
        if(g1.AreGenesCompatible(g2.GetGenes()))
        {
            return  ActualMixing(g1,g2,side);
        }
        else
            return null;
    }
    public default Object[] ActualMixing(Geneable g1, Geneable g2, boolean side)         // overload to change output depending on "side" value
    {
        return ActualMixing(g1,g2);
    }


}
