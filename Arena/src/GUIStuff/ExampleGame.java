package GUIStuff;

import AActualGame.ADisplay;
import AActualGame.AGame;
import AActualGame.AMap;
import AverTimeMeasure.AvgTimeMeasure;
import Matho.Vector2;
import NNetworks.DoubleEvolutionNetwork.NPCNetwork;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.concurrent.TimeUnit;

/**
 * Created by Lach on 2016-11-01.
 */
public class ExampleGame implements Runnable {
    private JPanel panel1;
    private JPanel game;
    private JLabel l1;
    private JPanel inny;
    private JLabel l3;
    private JLabel l4;
    private JLabel l2;
    private JCheckBox checkBox1;
    public ADisplay dsp;
    private AGame tempLoc;
    private Boolean przerwij;
    public JFrame fata;

    public void NextFrame()
    {

    }
    private void createUIComponents() {

        przerwij = false;
        dsp = new ADisplay();
        game = dsp;
        // TODO: place custom component creation code here

    }
    void InitDisplay()
    {
        dsp.source = new AMap();
        dsp.source.InitEmpty(10,10,"tile.jpg");
        dsp.source.zarzadzacz = tempLoc;
        dsp.source.zarzadzacz.targetLocation = dsp.source;
        dsp.source.zarzadzacz.spawnPlace1 = new Vector2();
        dsp.source.zarzadzacz.spawnPlace2 = new Vector2();
        dsp.source.zarzadzacz.spawnPlace1.x = 1;
        dsp.source.zarzadzacz.spawnPlace1.y = 1;
        dsp.source.zarzadzacz.spawnPlace2.x = 3;
        dsp.source.zarzadzacz.spawnPlace2.y = 3;
        dsp.source.zarzadzacz.StartGame();

    }

    class PlayerUpAction extends AbstractAction
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            dsp.source.player.MoveUp();
            //System.out.println("przycisksiemaaa");
        }

    }
    class PlayerDownAction extends AbstractAction
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            dsp.source.player.MoveDown();
            //System.out.println("przycisksiemaaa");
        }

    }
    class PlayerRightAction extends AbstractAction
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            dsp.source.player.MoveRight();
            //System.out.println("przycisksiemaaa");
        }

    }
    class PlayerLeftAction extends AbstractAction
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            dsp.source.player.MoveLeft();
            //System.out.println("przycisksiemaaa");
        }

    }


    class PlayerAttackUpAction extends AbstractAction
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            dsp.source.player.AttackUp();
            //System.out.println("przycisksiemaaa");
        }

    }
    class PlayerAttackDownAction extends AbstractAction
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            dsp.source.player.AttackDown();
            //System.out.println("przycisksiemaaa");
        }

    }
    class PlayerAttackRightAction extends AbstractAction
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            dsp.source.player.AttackRight();
            //System.out.println("przycisksiemaaa");
        }

    }
    class PlayerAttackLeftAction extends AbstractAction
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            dsp.source.player.AttackLeft();
            //System.out.println("przycisksiemaaa");
        }

    }

    public ExampleGame(String first, String second, NPCNetwork choosen,int choosenRange)
    {
        tempLoc = new AGame(first,second,choosen,choosenRange);

        ActionListener act = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==checkBox1)
                {
                    przerwij=true;
                    fata.dispatchEvent(new WindowEvent(fata, WindowEvent.WINDOW_CLOSING));
                }
            }
        };

        checkBox1.addActionListener(act);

        InitDisplay();

        PlayerUpAction npa = new PlayerUpAction();
        panel1.getInputMap().put(KeyStroke.getKeyStroke("W"), "nastepnyButtonKeyboardShortcut");
        panel1.getActionMap().put("nastepnyButtonKeyboardShortcut", npa);

        PlayerDownAction npa2 = new PlayerDownAction();
        panel1.getInputMap().put(KeyStroke.getKeyStroke("S"), "a2");
        panel1.getActionMap().put("a2", npa2);

        PlayerLeftAction npa3 = new PlayerLeftAction();
        panel1.getInputMap().put(KeyStroke.getKeyStroke("A"), "a3");
        panel1.getActionMap().put("a3", npa3);

        PlayerRightAction npa4 = new PlayerRightAction();
        panel1.getInputMap().put(KeyStroke.getKeyStroke("D"), "a4");
        panel1.getActionMap().put("a4", npa4);

        PlayerAttackUpAction npa5 = new PlayerAttackUpAction();
        panel1.getInputMap().put(KeyStroke.getKeyStroke("I"), "a5");
        panel1.getActionMap().put("a5", npa5);

        PlayerAttackDownAction npa6 = new PlayerAttackDownAction();
        panel1.getInputMap().put(KeyStroke.getKeyStroke("K"), "a6");
        panel1.getActionMap().put("a6", npa6);

        PlayerAttackLeftAction npa7 = new PlayerAttackLeftAction();
        panel1.getInputMap().put(KeyStroke.getKeyStroke("J"), "a7");
        panel1.getActionMap().put("a7", npa7);

        PlayerAttackRightAction npa8 = new PlayerAttackRightAction();
        panel1.getInputMap().put(KeyStroke.getKeyStroke("L"), "a8");
        panel1.getActionMap().put("a8", npa8);
    }

    public ExampleGame()
    {
        PlayerUpAction npa = new PlayerUpAction();
        panel1.getInputMap().put(KeyStroke.getKeyStroke("W"), "nastepnyButtonKeyboardShortcut");
        panel1.getActionMap().put("nastepnyButtonKeyboardShortcut", npa);

        PlayerDownAction npa2 = new PlayerDownAction();
        panel1.getInputMap().put(KeyStroke.getKeyStroke("S"), "a2");
        panel1.getActionMap().put("a2", npa2);

        PlayerLeftAction npa3 = new PlayerLeftAction();
        panel1.getInputMap().put(KeyStroke.getKeyStroke("A"), "a3");
        panel1.getActionMap().put("a3", npa3);

        PlayerRightAction npa4 = new PlayerRightAction();
        panel1.getInputMap().put(KeyStroke.getKeyStroke("D"), "a4");
        panel1.getActionMap().put("a4", npa4);

        PlayerAttackUpAction npa5 = new PlayerAttackUpAction();
        panel1.getInputMap().put(KeyStroke.getKeyStroke("I"), "a5");
        panel1.getActionMap().put("a5", npa5);

        PlayerAttackDownAction npa6 = new PlayerAttackDownAction();
        panel1.getInputMap().put(KeyStroke.getKeyStroke("K"), "a6");
        panel1.getActionMap().put("a6", npa6);

        PlayerAttackLeftAction npa7 = new PlayerAttackLeftAction();
        panel1.getInputMap().put(KeyStroke.getKeyStroke("J"), "a7");
        panel1.getActionMap().put("a7", npa7);

        PlayerAttackRightAction npa8 = new PlayerAttackRightAction();
        panel1.getInputMap().put(KeyStroke.getKeyStroke("L"), "a8");
        panel1.getActionMap().put("a8", npa8);

    }

    public void run() {
        JFrame frame = new JFrame("ExampleGame");
        frame.setContentPane(this.panel1);
        this.fata = frame;
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        AvgTimeMeasure miernik = new AvgTimeMeasure(2);
        while(!przerwij)
        {

            try {
                TimeUnit.MILLISECONDS.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //long tiem = System.nanoTime();
            this.dsp.physicComponent(0.04);
            //miernik.times[0] += (double)(System.nanoTime() - tiem)/1000000000;
            //tiem = System.nanoTime();
            this.dsp.paintComponent(this.dsp.getGraphics());
            //miernik.times[1] += (double)(System.nanoTime() - tiem)/1000000000;
            //miernik.iterations++;
            //this.l2.setText(String.valueOf(miernik.getTime(0)));
            //this.l4.setText(String.valueOf(miernik.getTime(1)));
        }
    }
}
