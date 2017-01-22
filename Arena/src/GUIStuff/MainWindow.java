package GUIStuff;

import AActualGame.ACharacter;
import AActualGame.ALabirynth.ASIMouse;
import AActualGame.ALabirynth.tempGameRunnableLauncher;
import AActualGame.ATrainSIGame;
import GeneticAlghorithm.basicClassesInterfaces.Geneable;
import GeneticAlghorithm.defaultImplementations.AGameSi.Labirynth.GeneticAlgorithmLabirynth;
import GeneticAlghorithm.defaultImplementations.AGameSi.OnlyWeights.GeneticAlgorithmWeights;
import GeneticAlghorithm.defaultImplementations.AGameSi.PopulationInfo;
import NNetworks.DoubleEvolutionNetwork.NPCNetwork;
import NNetworks.DoubleEvolutionNetwork.NPCNetworkBreeder;
import NNetworks.DoubleEvolutionNetwork.NPCNetworkBreederPararelInit;
import NNetworks.NeuronBetter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Lach on 2016-12-07.
 */
public class MainWindow {
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JButton button1;
    private JPanel mainPanel;
    private JLabel breederSize;
    private JButton newBreederButton;
    private JButton saveBreederButton;
    //private JButton openBreederButton;
    private JLabel pgLabel;
    private JTextField textField1;
    private JButton wyswietlButton;
    private JButton ustawButton;
    private JTextField textField2;
    private JLabel networkGrade;
    private JLabel networkGames;
    private JButton testujButton;
    private JButton nextGenerationButton;
    private JButton testujDoButton;
    //private NPCNetworkBreeder breeder;
    public GeneticAlgorithmLabirynth breeder2;
    private MainWindow myself;
    public ProgressLabel progressu;
    public JFrame parento;
    private ASIMouse choosen;
    private NPCNetwork scheme;
    public Boolean przerwijFlag;
    private int SIRange;
    public Geneable besto;
    public PopulationInfo informacyje;
    public boolean zakonczonoTestowanie;

    public boolean koniecTest;
    public boolean koniecPetli;

    public void UpdateStatistic()
    {
        informacyje.AddInfo(breeder2.getPopulation());
    }

    public void SetBreederInfo()
    {
        breederSize.setText(String.valueOf(breeder2.getPopulation().length));
    }

    public MainWindow()
    {
        progressu = new ProgressLabel();
        przerwijFlag = true;
        koniecTest = true;
        myself = this;
        ActionListener fajny = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==button1)
                {
                    LabirynthExampleButton();
                }
                else if(e.getSource()==newBreederButton)
                {
                    CreateNewBreederForm temp = new CreateNewBreederForm(myself);
                    (new Thread(temp)).start();
                }
                else if(e.getSource()==saveBreederButton)
                {
                    SaveBreeder();
                }
                else if(e.getSource()==ustawButton)
                {
                    UstawButton();
                }
                else if(e.getSource()==wyswietlButton)
                {
                    WyswietlButton();
                }
                else if(e.getSource()==testujButton)
                {
                    TestujButton();

                }
                else if(e.getSource()==nextGenerationButton)
                {
                    NextGenerationButton();
                }
                else if(e.getSource()==testujDoButton)
                {
                    TestujDoButton();
                }
            }
        };

        button1.addActionListener(fajny);
        newBreederButton.addActionListener(fajny);
        saveBreederButton.addActionListener(fajny);
        ustawButton.addActionListener(fajny);
        wyswietlButton.addActionListener(fajny);
        testujButton.addActionListener(fajny);
        nextGenerationButton.addActionListener(fajny);
        testujDoButton.addActionListener(fajny);
    }

    public void LabirynthExampleButton()
    {
        tempGameRunnableLauncher example = new tempGameRunnableLauncher();
        example.chosenToTest = choosen;
        example.maxTime = 15;
        example.timeTick = 0.04;
        example.testNumber = 1000;
        example.fata = this;
        example.progressu = progressu;
        example.visuallessSimulationMode = false;
        (new Thread(example)).start();
    }


    public void SortBreeder()
    {
        breeder2.SortByGrades();
        zakonczonoTestowanie = true;
    }

    public void NextGenerationButton()
    {
        breeder2.setPopulation(breeder2.getNextGen().NewGeneration(breeder2.getPopulation(),breeder2.getMixer()));
        breeder2.MutateAll(breeder2.mutationChance);
        breeder2.ResetGrades();
    }

    public void UstawButton()
    {
        choosen = (ASIMouse)breeder2.getPopulation()[Integer.parseInt(textField1.getText())];
    }
    public void WyswietlButton()
    {
        networkGrade.setText(String.valueOf(breeder2.getPopulation()[Integer.parseInt(textField1.getText())].GetGrades()));
        //networkGames.setText(String.valueOf(breeder.GetNPCNetwork(Integer.parseInt(textField1.getText())).getGradesDone()));
    }
    public double GetBestGrade()
    {
        return breeder2.getPopulation()[0].GetGrades();
    }

    public void TestujDoButton()
    {
        breeder2.TestUntil(Integer.parseInt(textField1.getText()));
    }
    public void TestujButton()
    {
        przerwijFlag = false;
        koniecTest = false;
        koniecPetli = false;


        breeder2.getTester().TestMultiple(breeder2.getPopulation(),Integer.valueOf(textField2.getText()));


    }

    public void CreateBreederFromForm(String networkNum)//String networkAttackFormat,String networkMovementFormat)
    {

        int networksNum = Integer.parseInt(networkNum);

        breeder2 = new GeneticAlgorithmLabirynth(this,progressu,networksNum);
        SetBreederInfo();
        informacyje = new PopulationInfo(8,1.0);

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainWindow");
        MainWindow dat = new MainWindow();
        dat.parento = frame;
        frame.setContentPane(dat.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        dat.ConnectProgressLabel();
    }

    private void createUIComponents() {
        String[] characters = {"Gracz","ProsteSI","WybranaSiecSI"};

        comboBox1 = new JComboBox(characters);
        comboBox2 = new JComboBox(characters);


    }

    public void ConnectProgressLabel()
    {
        progressu.SetLabel(pgLabel);
    }


    private void SaveBreeder() {

        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showSaveDialog(parento);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();

            informacyje.SaveToFile(file.getAbsolutePath());

        }
    }

}
