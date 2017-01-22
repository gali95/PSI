package GeneticAlghorithm.defaultImplementations.AGameSi;

import GeneticAlghorithm.basicClassesInterfaces.GeneMixer;
import GeneticAlghorithm.basicClassesInterfaces.Geneable;

import java.util.Objects;
import java.util.Random;

/**
 * Created by Lach on 2016-12-30.
 */
public class TwoSidedWeightsMixer implements GeneMixer{  // bardziej zaawansowane mieszanie genow - w losowej kolejnosci, z opcją dwóch wariantów używanych w przypaddku gdy rodzice będą tworzyć pare potomków, losowośc zapewnienia 50% udzialu genów dla kazdego z rodziców, w losowej kolejności (wyjątkiem jest sytuacja nieparzystych ilosci genów, wtedy zawsze pierwszy rodzic przekaże jeden gen więcej)

    int[] from;

    private void SetSeed(int lenght)
    {
        from = new int[lenght];

        for(int i=0;i<lenght/2;i++)
        {
            from[i] = 0;
        }
        for(int i=lenght/2;i<lenght;i++)
        {
            from[i] = 1;
        }
        Random random = new Random();
        int tymcz;
        int tymcz2;
        for(int i=0;i<lenght;i++)
        {
            tymcz2 = random.nextInt(lenght);
            tymcz = from[i];
            from[i] = from[tymcz2];
            from[tymcz2] = tymcz;
        }

    }
    public void WipeSeed()
    {
        from = null;
    }

    @Override
    public Object[] ActualMixing(Geneable g1, Geneable g2) {
        return ActualMixing(g1,g2,false);
    }

    @Override
    public Object[] ActualMixing(Geneable g1, Geneable g2, boolean side) {
        Object[] firsts = g1.GetGenes(), seconds = g2.GetGenes();
        if(from == null)
        {
            SetSeed(firsts.length);
        }
        Object[] ret = new Object[firsts.length];
        if(side)
        {
            for(int i=0;i<ret.length;i++)
            {
                if(from[i]==0) ret[i] = firsts[i];
                else ret[i] = seconds[i];
            }
        }
        else
        {
            for(int i=0;i<ret.length;i++)
            {
                if(from[i]==1) ret[i] = firsts[i];
                else ret[i] = seconds[i];
            }
        }
        return ret;
    }
}
