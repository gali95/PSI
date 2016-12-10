package GUIStuff;

import AActualGame.ATrainSIGame;
import NNetworks.DoubleEvolutionNetwork.NPCNetwork;
import NNetworks.DoubleEvolutionNetwork.NPCNetworkBreeder;
import NNetworks.DoubleEvolutionNetwork.NPCNetworkBreederPararelInit;

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
    private JLabel breederAttackFormat;
    private JLabel breederMovementFormat;
    private JLabel breederInt;
    private JButton newBreederButton;
    private JButton saveBreederButton;
    private JButton openBreederButton;
    private JLabel pgLabel;
    private JTextField textField1;
    private JButton wyswietlButton;
    private JButton ustawButton;
    private JTextField textField2;
    private JTextField textField3;
    private JLabel networkGrade;
    private JLabel networkGames;
    private JButton testujButton;
    private JButton przerwijTestyButton;
    private JButton nextGenerationButton;
    private NPCNetworkBreeder breeder;
    private MainWindow myself;
    private ProgressLabel progressu;
    public JFrame parento;
    private NPCNetwork choosen;
    public Boolean przerwijFlag;
    private int SIRange;

    public void SetBreederInfo()
    {
        breederSize.setText(String.valueOf(breeder.getNetworksCount()));
        breederAttackFormat.setText(String.valueOf(breeder.GetAttackFormat()));
        breederMovementFormat.setText(String.valueOf(breeder.GetMovementFormat()));
        breederInt.setText(String.valueOf(breeder.getRandTestNum()));
    }

    public MainWindow()
    {
        progressu = new ProgressLabel();
        przerwijFlag = true;
        myself = this;
        ActionListener fajny = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==button1)
                {
                    ExampleGame sym = new ExampleGame(comboBox1.getSelectedItem().toString(),comboBox2.getSelectedItem().toString(),choosen,SIRange);
                    (new Thread(sym)).start();
                }
                else if(e.getSource()==newBreederButton)
                {
                    CreateNewBreederForm temp = new CreateNewBreederForm(myself);
                    (new Thread(temp)).start();
                }
                else if(e.getSource()==openBreederButton)
                {
                    OpenBreeder();
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
                else if(e.getSource()==przerwijTestyButton)
                {
                    LosujAżDobre();
                }
                else if(e.getSource()==nextGenerationButton)
                {
                    breeder.championsToPreserveNumber = Integer.parseInt(textField3.getText());
                    breeder.NextGeneration();
                }
            }
        };

        button1.addActionListener(fajny);
        newBreederButton.addActionListener(fajny);
        openBreederButton.addActionListener(fajny);
        saveBreederButton.addActionListener(fajny);
        ustawButton.addActionListener(fajny);
        wyswietlButton.addActionListener(fajny);
        testujButton.addActionListener(fajny);
        przerwijTestyButton.addActionListener(fajny);
        nextGenerationButton.addActionListener(fajny);
    }

    public void LosujAżDobre()
    {
        Boolean przestan=false;
        int minOcena= 1000;
        while(!przestan)
        {
            breeder.GetNPCNetwork(0).attack.RandomWeights();
            breeder.GetNPCNetwork(0).movement.RandomWeights();
            breeder.GetNPCNetwork(0).ResetGradesDone();
            breeder.GetNPCNetwork(0).setGrade(0);
            TestujButton();
            while(progressu.actual!=progressu.max || progressu.actual==0)
            {
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(breeder.GetNPCNetwork(Integer.parseInt(textField1.getText())).getGradesDone()>minOcena) przestan = true;
        }

    }

    public void SortBreeder()
    {
        breeder.SortByGrades();
    }

    public void UstawButton()
    {
        choosen = breeder.GetNPCNetwork(Integer.parseInt(textField1.getText()));
    }
    public void WyswietlButton()
    {
        networkGrade.setText(String.valueOf(breeder.GetNPCNetwork(Integer.parseInt(textField1.getText())).getGrade()));
        networkGames.setText(String.valueOf(breeder.GetNPCNetwork(Integer.parseInt(textField1.getText())).getGradesDone()));
    }
    public void TestujButton()
    {
        przerwijFlag = false;

        ExecutorService executor = Executors.newFixedThreadPool(8);

        int sumaToPlay = 0;
        for(int i=0;i<breeder.getNetworksCount();i++) sumaToPlay-=breeder.GetNPCNetwork(i).getGradesDone();
        sumaToPlay += (breeder.getNetworksCount() * Integer.valueOf(textField2.getText()));

        progressu.SetNewProgress(sumaToPlay);

        for (int i = 0; i < breeder.getNetworksCount(); i++) {
            ATrainSIGame initer = new ATrainSIGame();
            int GamesToPlay = Integer.valueOf(textField2.getText()) - breeder.GetNPCNetwork(i).getGradesDone();
            initer.InitGame(breeder.GetNPCNetwork(i),SIRange,10,10,GamesToPlay,60,0.02);
            initer.fata = this;
            initer.progressu = progressu;
            initer.thatWasLast = false;
            if(i == breeder.getNetworksCount()-1) initer.thatWasLast = true;
            executor.execute(initer);

        }
        executor.shutdown();
    }

    public void CreateBreederFromForm(String networkNum,String networkAttackFormat,String networkMovementFormat)
    {
        String[] attackArray = networkAttackFormat.split(" ");
        String[] movementArray = networkMovementFormat.split(" ");
        int[] attackPattern = new int[attackArray.length];
        for(int i=0;i<attackPattern.length;i++)
        {
            attackPattern[i] = Integer.parseInt(attackArray[i]);
        }
        int[] movementPattern = new int[movementArray.length];
        for(int i=0;i<movementArray.length;i++)
        {
            movementPattern[i] = Integer.parseInt(movementArray[i]);
        }
        SIRange = attackPattern[0];
        movementPattern[0] = ((attackPattern[0]*2+1)*(attackPattern[0]*2+1)*3+3);
        attackPattern[0] = ((attackPattern[0]*2+1)*(attackPattern[0]*2+1)*3+3);
        int networksNum = Integer.parseInt(networkNum);
        breeder = new NPCNetworkBreeder();
        breeder.EmptyInit(networksNum);

        przerwijFlag = false;

        ExecutorService executor = Executors.newFixedThreadPool(8);

        progressu.SetNewProgress(networksNum);

        for (int i = 0; i < networksNum; i++) {
            NPCNetworkBreederPararelInit initer = new NPCNetworkBreederPararelInit();
            initer.pgLabel = progressu;
            initer.attackPattern = attackPattern;
            initer.movementPattern = movementPattern;
            initer.index = i;
            initer.target = breeder;
            initer.fata = this;
            executor.execute(initer);
        }
        executor.shutdown();

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

    private void OpenBreeder() {
        //Handle open button action.
            JFileChooser fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(parento);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                //This is where a real application would open the file.
                breeder = NPCNetworkBreeder.OpenFromFile( file.getAbsolutePath() );
                SetBreederInfo();
            }

    }

    private void SaveBreeder() {
        //Handle open button action.
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showSaveDialog(parento);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            //This is where a real application would open the file.
            breeder.SaveToFile( file.getAbsolutePath() );
            SetBreederInfo();
        }
    }

}
