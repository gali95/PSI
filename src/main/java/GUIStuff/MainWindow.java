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
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Lach on 2016-12-07.
 */
public class MainWindow {
    private JButton button1;
    private JPanel mainPanel;
    private JLabel breederSize;
    private JButton newBreederButton;
    private JButton saveBreederButton;
    private JTextField textField2;
    private JButton nextGenerationButton;
    private JPanel populationPanel;
    private JPanel listAndDetailsPanel;
    private JList list1;
    private JPanel algoActionsPanel;
    private JLabel statusLabel;
    private JLabel operationParameterDescription;
    private JButton button3;
    private JComboBox comboBox1;


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

    public void UpdateStatistic() {
        informacyje.AddInfo(breeder2.getPopulation());
    }

    public void SetBreederInfo() {
        breederSize.setText(String.valueOf(breeder2.getPopulation().length));
    }

    public MainWindow() {
        progressu = new ProgressLabel();
        przerwijFlag = true;
        koniecTest = true;
        myself = this;
        ActionListener fajny = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == button1) {
                    LabirynthExampleButton();
                } else if (e.getSource() == newBreederButton) {
                    CreateNewBreederForm temp = new CreateNewBreederForm(myself);
                    (new Thread(temp)).start();
                } else if (e.getSource() == saveBreederButton) {
                    SaveBreeder();
                } else if (e.getSource() == ustawButton) {
                    UstawButton();
                } else if (e.getSource() == wyswietlButton) {
                    WyswietlButton();
                } else if (e.getSource() == testujButton) {
                    TestujButton();

                } else if (e.getSource() == nextGenerationButton) {
                    NextGenerationButton();
                } else if (e.getSource() == testujDoButton) {
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

        comboBox1.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {

            }
        });
    }

    public void LabirynthExampleButton() {
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


    public void SortBreeder() {
        breeder2.SortByGrades();
        zakonczonoTestowanie = true;
    }

    public void NextGenerationButton() {
        breeder2.setPopulation(breeder2.getNextGen().NewGeneration(breeder2.getPopulation(), breeder2.getMixer()));
        breeder2.MutateAll(breeder2.mutationChance);
        breeder2.ResetGrades();
    }

    public void UstawButton() {
        choosen = (ASIMouse) breeder2.getPopulation()[Integer.parseInt(textField1.getText())];
    }

    public void WyswietlButton() {
        networkGrade.setText(String.valueOf(breeder2.getPopulation()[Integer.parseInt(textField1.getText())].GetGrades()));
        //networkGames.setText(String.valueOf(breeder.GetNPCNetwork(Integer.parseInt(textField1.getText())).getGradesDone()));
    }

    public double GetBestGrade() {
        return breeder2.getPopulation()[0].GetGrades();
    }

    public void TestujDoButton() {
        breeder2.TestUntil(Integer.parseInt(textField1.getText()));
    }

    public void TestujButton() {
        przerwijFlag = false;
        koniecTest = false;
        koniecPetli = false;


        breeder2.getTester().TestMultiple(breeder2.getPopulation(), Integer.valueOf(textField2.getText()));


    }

    public void CreateBreederFromForm(String networkNum)//String networkAttackFormat,String networkMovementFormat)
    {

        int networksNum = Integer.parseInt(networkNum);

        breeder2 = new GeneticAlgorithmLabirynth(this, progressu, networksNum);
        SetBreederInfo();
        informacyje = new PopulationInfo(8, 1.0);

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

    public void ConnectProgressLabel() {
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

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(6, 5, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel2, new GridConstraints(0, 1, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Aktualne pokolenie");
        panel2.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        breederSize = new JLabel();
        breederSize.setText("Label");
        panel2.add(breederSize, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel3, new GridConstraints(2, 0, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("ilosc powtorzen");
        panel3.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel4, new GridConstraints(2, 1, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        textField2 = new JTextField();
        panel4.add(textField2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        button1 = new JButton();
        button1.setText("Gra Testowa");
        mainPanel.add(button1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        newBreederButton = new JButton();
        newBreederButton.setText("New Breeder");
        mainPanel.add(newBreederButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        saveBreederButton = new JButton();
        saveBreederButton.setText("Save Results");
        mainPanel.add(saveBreederButton, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pgLabel = new JLabel();
        pgLabel.setText("Label");
        mainPanel.add(pgLabel, new GridConstraints(2, 4, 2, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField1 = new JTextField();
        mainPanel.add(textField1, new GridConstraints(2, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        wyswietlButton = new JButton();
        wyswietlButton.setText("Wyswietl");
        mainPanel.add(wyswietlButton, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("wynik");
        mainPanel.add(label3, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("rozegrane gry");
        mainPanel.add(label4, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ustawButton = new JButton();
        ustawButton.setText("Ustaw");
        mainPanel.add(ustawButton, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        networkGrade = new JLabel();
        networkGrade.setText("Label");
        mainPanel.add(networkGrade, new GridConstraints(4, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        networkGames = new JLabel();
        networkGames.setText("Label");
        mainPanel.add(networkGames, new GridConstraints(5, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        testujButton = new JButton();
        testujButton.setText("Testuj");
        mainPanel.add(testujButton, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nextGenerationButton = new JButton();
        nextGenerationButton.setText("NextGeneration");
        mainPanel.add(nextGenerationButton, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        testujDoButton = new JButton();
        testujDoButton.setText("TestujDo");
        mainPanel.add(testujDoButton, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
