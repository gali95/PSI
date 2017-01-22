package GeneticAlghorithm.defaultImplementations.AGameSi;

import GeneticAlghorithm.basicClassesInterfaces.GeneMixer;
import GeneticAlghorithm.basicClassesInterfaces.Geneable;

import java.util.Random;

/**
 * Created by Lach on 2016-12-25.
 */
public class WeightsMixer implements GeneMixer{  // implementacja TwoSidedWeightsMixer, uproszczona, bez możliwości zastosowania dwóch wariantów


    @Override
    public Object[] ActualMixing(Geneable g1, Geneable g2) {

        Object[] ret = new Object[g1.GetGenes().length];
        int[] from = new int[ret.length];

        Object[] firsts=g1.GetGenes(),seconds=g2.GetGenes();

        for(int i=0;i<firsts.length/2;i++)
        {
            from[i] = 0;
        }
        for(int i=firsts.length/2;i<firsts.length;i++)
        {
            from[i] = 1;
        }
        Random random = new Random();
        int tymcz;
        int tymcz2;
        for(int i=0;i<firsts.length;i++)
        {
            tymcz2 = random.nextInt(firsts.length);
            tymcz = from[i];
            from[i] = from[tymcz2];
            from[tymcz2] = tymcz;
        }
        for(int i=0;i<ret.length;i++)
        {
            if(from[i]==0) ret[i] = firsts[i];
            else ret[i] = seconds[i];
        }

        return ret;
    }

}
