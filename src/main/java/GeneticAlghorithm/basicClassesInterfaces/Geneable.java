package GeneticAlghorithm.basicClassesInterfaces;

/**
 * Created by Lach on 2016-12-25.
 */
public interface Geneable {

    public Object[] GetGenes();    // funkcja pobierająca genotyp obiektu, zwraca tablice dowonlych obiektow, jest to zależne od implementacji interfejsu
    public default void SetProperGenes(Object[] genes)       // funkcja ustawiająca genotyp, jednoczesnie sprawdzająca czy jest on kompatybilny z aktualnym genotypem (czy gen nowy i poprzedni jest obiektem tego samego typu i czy ilosc genow w genotypie sie nie zmieni)
    {
        if(AreGenesCompatible(genes))
        {
            SetGenes(genes);
        }
    }
    public void SetGenes(Object[] genes);  // funkcja ustawiająca osobnikowi dany genotyp // default setGenes func to overload
    public default boolean AreGenesCompatible(Object[] genes)  // sprawdzanie kompatybilnosci aktualnego, i znajdujacego sie w argumencie funkcji genotypu
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
    public double GetGrades();    // pobranie aktualnej "oceny przystosowania
    public void SetGrades(double grade);  // ustawienie aktualnej oceny przystosowania
    public Geneable CreateEmptyChild();  // utworzenie kopii tego obiektu, uzywane gdy chcemy umiescic np. nowo stworzony genotyp do obiektu bez pozbywania sie aktualnego, dlatego trzeba stworzyc nowego osobnika ktory dostanie nowy genotyp // create object that can import genes from mixer
    public default int compareTo(Geneable other)
    {
        Double mine = GetGrades();
        Double others = other.GetGrades();
        return mine.compareTo(others) ;
    }
    public void Mutate(double chanceTo);  // zmutowanie aktualnego genotypu, chanceTo jest to szansa w zakresie 0.0-1.0 ktora definiuje szanse KAŻDEGO genu na bycie poddanym mutacji
    default String GetName()
    {
        return "Geneable";
    }
    default String GetDescription()
    {
        return "Grades: " + GetGrades();
    }

}
