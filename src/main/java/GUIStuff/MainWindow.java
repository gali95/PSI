package GUIStuff;

import AActualGame.ALabirynth.ASIMouse;
import AActualGame.ALabirynth.tempGameRunnableLauncher;
import GeneticAlghorithm.basicClassesInterfaces.Geneable;
import GeneticAlghorithm.defaultImplementations.AGameSi.Labirynth.GeneticAlgorithmLabirynth;
import GeneticAlghorithm.defaultImplementations.AGameSi.PopulationInfo;
import NNetworks.DoubleEvolutionNetwork.NPCNetwork;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

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
    private JLabel objectDescription;


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
                } else if (e.getSource() == nextGenerationButton) {
                    NextGenerationButton();
                }
            }
        };

        button1.addActionListener(fajny);
        newBreederButton.addActionListener(fajny);
        saveBreederButton.addActionListener(fajny);
        nextGenerationButton.addActionListener(fajny);

        comboBox1.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() != ItemEvent.SELECTED) return;
                switch (e.getItemSelectable().getSelectedObjects()[0].toString()) {
                    case "One-Time Testing":
                        operationParameterDescription.setText("Tests per subject:");
                        button3.setText("Execute Tests");
                        break;
                    case "Test until...":
                        operationParameterDescription.setText("Desired subject score:");
                        button3.setText("Start Learning");
                        break;
                    default:
                        System.out.println(e.getItemSelectable().getSelectedObjects()[0].toString());
                        System.out.println(e.getStateChange());
                }
            }
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (button3.getText()) {
                    case "Execute Tests":
                        TestujButton(Integer.parseInt(textField2.getText()));
                        break;
                    case "Start Learning":
                        TestujDoButton(Integer.parseInt(textField2.getText()));
                        break;
                }
            }
        });
        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()) return;
                int selectionIndex = ((JList)(e.getSource())).getLeadSelectionIndex();

                WyswietlButton(breeder2.getPopulation()[selectionIndex]);
                UstawButton(breeder2.getPopulation()[selectionIndex]);
            }
        });
        list1.setCellRenderer(new GeneableCellRenderer());
        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ConnectProgressLabel();
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
        list1.setListData(breeder2.getPopulation());
        zakonczonoTestowanie = true;
    }

    public void NextGenerationButton() {
        breeder2.setPopulation(breeder2.getNextGen().NewGeneration(breeder2.getPopulation(), breeder2.getMixer()));
        breeder2.MutateAll(breeder2.mutationChance);
        breeder2.ResetGrades();
    }

    public void UstawButton(Geneable clicked) {
        choosen = (ASIMouse) clicked;
    }

    public void WyswietlButton(Geneable clicked) {
        objectDescription.setText(clicked.GetDescription() + "\n");
    }

    public double GetBestGrade() {
        return breeder2.getPopulation()[0].GetGrades();
    }

    public void TestujDoButton(int minimalObjectsToStop) {
        breeder2.TestUntil(minimalObjectsToStop);
    }

    public void TestujButton(int numOfTestPerObject) {
        przerwijFlag = false;
        koniecTest = false;
        koniecPetli = false;

        breeder2.getTester().TestMultiple(breeder2.getPopulation(), numOfTestPerObject);
    }

    public void CreateBreederFromForm(String networkNum)//String networkAttackFormat,String networkMovementFormat)
    {
        int networksNum = Integer.parseInt(networkNum);
        breeder2 = new GeneticAlgorithmLabirynth(this, progressu, networksNum);
        SetBreederInfo();
        informacyje = new PopulationInfo(8, 1.0);

        list1.setListData(breeder2.getPopulation());
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("MainWindow");
        frame.setContentPane(new MainWindow().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    public void ConnectProgressLabel() {
        progressu.SetLabel(statusLabel);
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
        mainPanel.setLayout(new BorderLayout(0, 0));
        populationPanel = new JPanel();
        populationPanel.setLayout(new BorderLayout(0, 0));
        mainPanel.add(populationPanel, BorderLayout.EAST);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        populationPanel.add(panel1, BorderLayout.NORTH);
        breederSize = new JLabel();
        breederSize.setText("population size");
        panel1.add(breederSize, BorderLayout.CENTER);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(0, 0));
        panel1.add(panel2, BorderLayout.SOUTH);
        newBreederButton = new JButton();
        newBreederButton.setText("New Population");
        panel2.add(newBreederButton, BorderLayout.CENTER);
        saveBreederButton = new JButton();
        saveBreederButton.setText("Save Results");
        panel2.add(saveBreederButton, BorderLayout.SOUTH);
        listAndDetailsPanel = new JPanel();
        listAndDetailsPanel.setLayout(new BorderLayout(0, 0));
        mainPanel.add(listAndDetailsPanel, BorderLayout.CENTER);
        list1 = new JList();
        listAndDetailsPanel.add(list1, BorderLayout.CENTER);
        button1 = new JButton();
        button1.setText("Test Game");
        listAndDetailsPanel.add(button1, BorderLayout.SOUTH);
        objectDescription = new JLabel();
        objectDescription.setText("object description");
        listAndDetailsPanel.add(objectDescription, BorderLayout.NORTH);
        algoActionsPanel = new JPanel();
        algoActionsPanel.setLayout(new BorderLayout(0, 0));
        mainPanel.add(algoActionsPanel, BorderLayout.WEST);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout(0, 0));
        algoActionsPanel.add(panel3, BorderLayout.NORTH);
        comboBox1 = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("One-Time Testing");
        defaultComboBoxModel1.addElement("Test until...");
        comboBox1.setModel(defaultComboBoxModel1);
        panel3.add(comboBox1, BorderLayout.NORTH);
        operationParameterDescription = new JLabel();
        operationParameterDescription.setText("method parameter");
        panel3.add(operationParameterDescription, BorderLayout.CENTER);
        textField2 = new JTextField();
        panel3.add(textField2, BorderLayout.SOUTH);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new BorderLayout(0, 0));
        algoActionsPanel.add(panel4, BorderLayout.SOUTH);
        button3 = new JButton();
        button3.setText("Execute Method");
        panel4.add(button3, BorderLayout.NORTH);
        nextGenerationButton = new JButton();
        nextGenerationButton.setText("Next Generation");
        panel4.add(nextGenerationButton, BorderLayout.CENTER);
        statusLabel = new JLabel();
        statusLabel.setText("Label");
        mainPanel.add(statusLabel, BorderLayout.SOUTH);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
