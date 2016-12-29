package GeneticAlghorithm.basicClassesInterfaces;

/**
 * Created by Lach on 2016-12-25.
 */
public interface Geneable {

    public Object[] GetGenes();
    public default void SetProperGenes(Object[] genes)
    {
        if(AreGenesCompatible(genes))
        {
            SetGenes(genes);
        }
    }
    public void SetGenes(Object[] genes);  // default setGenes func to overload
    public default boolean AreGenesCompatible(Object[] genes)
    {
        Object[] myOwn = GetGenes();
        if(myOwn.length != genes.length) return false;
        for(int i=0;i<genes.length;i++)
        {
            if(myOwn[i].getClass() != genes[i].getClass())
            {
                return false;
            }
        }
        return true;
    }
    public double GetGrades();
    public void SetGrades(double grade);
    public Geneable CreateEmptyChild();  // create object that can import genes from mixer
    public default int compareTo(Geneable other)
    {
        Double mine = GetGrades();
        Double others = other.GetGrades();
        return mine.compareTo(others) ;
    }
    public void Mutate(double chanceTo);

}
