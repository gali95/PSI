package GeneticAlghorithm.defaultImplementations.AGameSi;

import GeneticAlghorithm.basicClassesInterfaces.Geneable;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by Lach on 2017-01-04.
 */
public class PopulationInfo {    // klasa ta sluzy tylko do archiwizowania informacji o postepach algorytmu, zbiera informacje o danej populacji, mianowicie o tym jaka ilosc osobnikow miesci sie w danych zakresach ocen i umozliwia eksport tych informacji do pliku

    public ArrayList<int[]> populationGrades;
    public double partRange;
    public int partsNum;

    public PopulationInfo(int parts,double partRange)
    {
        partsNum = parts;
        this.partRange = partRange;
        populationGrades = new ArrayList<int[]>();
    }
    public void AddInfo(Geneable[] population)  // uzycie tej funkcji dodaje do archiwum informacje o populacji "population" domyslnie uzywa sie jej na kazdym nowym pokoleniu przez co przechowujemy informacje o postepie algorytmu
    {
        int[] add = new int[partsNum];
        for(int i=0;i<population.length;i++)
        {
            int gotten = (int)(population[i].GetGrades()/partRange);
            if(gotten < partsNum && gotten >=0)
            {
                add[gotten]++;
            }
        }
        populationGrades.add(add);
    }

    public void SaveToFile(String filepath)  // zapis archiwum do sciezki "filepath"
    {

        try {
            Writer wr = new FileWriter(filepath);
            for(int i=0;i<populationGrades.size();i++)
            {
                wr.write(i+";");
                for(int j=0;j<populationGrades.get(i).length;j++)
                {
                    wr.write(populationGrades.get(i)[j]+",");
                }
                wr.write("\n");
            }
            wr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
