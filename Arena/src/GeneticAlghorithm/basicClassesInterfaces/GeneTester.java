package GeneticAlghorithm.basicClassesInterfaces;

import NNetworks.DoubleEvolutionNetwork.NPCNetwork;

/**
 * Created by Lach on 2016-12-25.
 */
public interface GeneTester {  // interfejs używany przez środowiska testowe danej populacji, muszą one zaimplementować sposób jedno lub wielokrotnego poddania testom danej populacji

    public void TestOnce(Geneable[] population);     // testowanie jednokrotne
    public default void TestMultiple(Geneable[] population, int testTimes)  // testowanie wielokrotne
    {
        for(int i=0;i<testTimes;i++)
        TestOnce(population);
    }

}
