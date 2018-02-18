package GeneticAlghorithm.defaultImplementations.AGameSi.Labirynth;

import AActualGame.ALabirynth.ASIMouse;
import GUIStuff.MainWindow;
import GUIStuff.ProgressLabel;
import GeneticAlghorithm.basicClassesInterfaces.Geneable;
import GeneticAlghorithm.basicClassesInterfaces.GeneticAlgorithm;
import GeneticAlghorithm.defaultImplementations.AGameSi.RouleteNextGeneration;
import GeneticAlghorithm.defaultImplementations.AGameSi.TwoSidedWeightsMixer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by Lach on 2017-01-02.
 */
public class GeneticAlgorithmLabirynth extends GeneticAlgorithm{  // implementacja głównego algorytmu w zastosowaniu do labiryntu i mysz

    public double mutationChance;
    public MainWindow fata;

    public GeneticAlgorithmLabirynth(MainWindow fata, ProgressLabel progressu,int size)  // konstruktor ktory wybiera odpowiednie interfejsy, kazdy z nich posiada swoj opis w swoim pliku, tu go pomine
    {
        setGeneRand(new MouseGenesRandomizer());
        setMixer(new TwoSidedWeightsMixer());
        setTester(new MouseTester(fata,progressu));
        setNextGen(new RouleteNextGeneration());
        CreateFreshPopulation(size);
        this.fata = fata;
        mutationChance = 0.01;
    }

    public void CreateFreshPopulation(int size)   // funkcja tworząca początkową populacje o rozmiarze "size"
    {
        ASIMouse[] mouses = new ASIMouse[size];
        for(int i=0;i<mouses.length;i++)
        {
            try {
                mouses[i] = new ASIMouse();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        setPopulation(mouses);
        getGeneRand().SetRandomGenes(mouses);
    }

    public void SortByGrades() {              // sortowanie populacji wg ocen w kolejnosci malejącej
        Geneable[] c = getPopulation();
        Arrays.sort(c, new Comparator<Geneable>() {
            public int compare(Geneable o1, Geneable o2) {
                return o2.compareTo(o1);
            }
        });
        setPopulation(c);
    }

    public synchronized void TestUntil(int score) {            // funkcja ktora zawiera w sobie caly algorytm genetyczny - testuje populacje i tworzy nowe pokolenie az conajmniej jedne z jej osobnikow nie osiagnie wyniku "score"

        TestUntilThread threadObject = new TestUntilThread();
        threadObject.init(this,fata,score);
        (new Thread(threadObject)).start();

    }

}
