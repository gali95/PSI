package GeneticAlghorithm.basicClassesInterfaces;

/**
 * Created by Lach on 2016-12-25.
 */
public class GeneticAlgorithm {

    private Geneable[] population;
    private GeneMixer mixer;
    private GeneTester tester;
    private NextGenerationCreator nextGen;
    private GeneRandomizer geneRand;

    public GeneRandomizer getGeneRand() {
        return geneRand;
    }

    public void setGeneRand(GeneRandomizer geneRand) {
        this.geneRand = geneRand;
    }

    public Geneable[] getPopulation() {
        return population;
    }

    public void setPopulation(Geneable[] population) {

        for(int i=1;i<population.length;i++)
        {
            if(!population[0].AreGenesCompatible(population[i].GetGenes()))
            {
                this.population = null;
                return;
            }
        }
        this.population = population;
    }

    public GeneMixer getMixer() {
        return mixer;
    }

    public void setMixer(GeneMixer mixer) {
        this.mixer = mixer;
    }

    public GeneTester getTester() {
        return tester;
    }

    public void setTester(GeneTester tester) {
        this.tester = tester;
    }

    public NextGenerationCreator getNextGen() {
        return nextGen;
    }

    public void setNextGen(NextGenerationCreator nextGen) {
        this.nextGen = nextGen;
    }

    public void MutateAll(double chance)
    {
        Geneable[] pop = getPopulation();
        for(int i=0;i< pop.length;i++)
        {
            pop[i].Mutate(chance);
        }
    }

    public void ResetGrades()
    {
        if(population == null) return;

        for (Geneable aPopulation : population) {
            aPopulation.SetGrades(0);
        }

    }
}
