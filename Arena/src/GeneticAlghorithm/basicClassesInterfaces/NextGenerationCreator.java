package GeneticAlghorithm.basicClassesInterfaces;

/**
 * Created by Lach on 2016-12-25.
 */
public interface NextGenerationCreator {

    public Geneable[] NewGeneration(Geneable[] population, GeneMixer choosen);  // funkcja ktora odpowiada za cały mechanizm selekcji osobników, na wejsciu pobiera populacje która powinna już mieć swoje oceny przystosowania a zwraca nowe pokolenie, nie nadpisujac starego

}
