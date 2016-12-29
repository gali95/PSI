package GeneticAlghorithm.basicClassesInterfaces;

import NNetworks.DoubleEvolutionNetwork.NPCNetwork;

/**
 * Created by Lach on 2016-12-25.
 */
public interface GeneTester {

    public void TestOnce(Geneable[] population);
    public default void TestMultiple(Geneable[] population, int testTimes)
    {
        for(int i=0;i<testTimes;i++)
        TestOnce(population);
    }

}
