package GeneticAlghorithm.defaultImplementations.AGameSi;

import GeneticAlghorithm.basicClassesInterfaces.GeneMixer;
import GeneticAlghorithm.basicClassesInterfaces.Geneable;

import java.util.Random;

/**
 * Created by Lach on 2016-12-25.
 */
public class SinglePointMixer implements GeneMixer {   // utworzenie nowego genotypu, poprzez przedzielenie genotypow rodzicow w indeksie losowym i na podstawie wymieszania tych dwoch nierownych polowek, tak zeby uzyskac genotyp o orginalnej wielkosci
    @Override
    public Object[] ActualMixing(Geneable g1, Geneable g2) {
        return ActualMixing(g1,g2,true);
    }

    @Override
    public Object[] ActualMixing(Geneable g1, Geneable g2, boolean side) {

        Object[] genes1,genes2;
        if(side) {
            genes1 = g1.GetGenes();
            genes2 = g2.GetGenes();
        }
        else
        {
            genes1 = g2.GetGenes();
            genes2 = g1.GetGenes();
        }
        Object[] ret = new Object[genes1.length];

        int i;
        for(i=0;i<genes1.length/2;i++)
        {
            ret[i] = genes1[i];
        }
        for(;i<genes1.length;i++)
        {
            ret[i] = genes2[i];
        }
        return ret;
    }
}
