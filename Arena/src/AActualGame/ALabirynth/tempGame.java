package AActualGame.ALabirynth;

import AActualGame.ACharacter;
import AActualGame.ADisplay;
import AActualGame.AGraphicFiles;
import AActualGame.AObject;
import GUIStuff.ExampleGame;
import GUIStuff.MainWindow;
import GUIStuff.ProgressLabel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Lach on 2017-01-01.
 */
public class tempGame{
    public JPanel namae;
    private JButton button1;
    public AGame logic;
    public ACharacter chosenToTest;
    public int testNumber;
    public double maxTime,timeTick;
    public boolean visuallessSimulationMode;
    public boolean thatWasLast;
    public MainWindow fata;
    public ProgressLabel progressu;

    public tempGame()
    {

        ActionListener act = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==button1)
                {
                    logic.zakoncz = true;
                }
            }
        };

        button1.addActionListener(act);

        /*
        tempGame.PlayerUpAction npa = new tempGame.PlayerUpAction();
        namae.getInputMap().put(KeyStroke.getKeyStroke("W"), "nastepnyButtonKeyboardShortcut");
        namae.getActionMap().put("nastepnyButtonKeyboardShortcut", npa);

        tempGame.PlayerDownAction npa2 = new tempGame.PlayerDownAction();
        namae.getInputMap().put(KeyStroke.getKeyStroke("S"), "a2");
        namae.getActionMap().put("a2", npa2);

        tempGame.PlayerLeftAction npa3 = new tempGame.PlayerLeftAction();
        namae.getInputMap().put(KeyStroke.getKeyStroke("A"), "a3");
        namae.getActionMap().put("a3", npa3);

        tempGame.PlayerRightAction npa4 = new tempGame.PlayerRightAction();
        namae.getInputMap().put(KeyStroke.getKeyStroke("D"), "a4");
        namae.getActionMap().put("a4", npa4);
           */
    }

    public void LogicInit()
    {
        logic = new AGame(chosenToTest,testNumber);
        logic.fata = fata;
        logic.progressu = progressu;
        logic.SetDisplay((ADisplay)namae);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        namae = new ADisplay();

    }


    public void runWithGraphic()
    {
        JFrame frame = new JFrame("tempGame");
        frame.setContentPane(namae);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        logic.Sciany();
        logic.actTime = 0;

        while(!logic.zakoncz)
        {

            try {
                TimeUnit.MILLISECONDS.sleep((long)(timeTick*1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            logic.dsp.physicComponent(timeTick);
            logic.dsp.paintComponent(logic.dsp.getGraphics());
            logic.actTime+=timeTick;
            if(logic.actTime>maxTime)
            {
                logic.Reset();
                System.out.println("reset");
            }
        }
        frame.dispose();
        //System.out.println(actTime);
    }
    public void runWithoutGraphic()
    {
        logic.Sciany();
        double actTime = 0;
        while(!logic.zakoncz)
        {
            logic.dsp.physicComponent(timeTick);
            actTime+=timeTick;
            if(actTime>maxTime) logic.Reset();
        }
    }
    /*
    class PlayerUpAction extends AbstractAction
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            logic.dsp.source.player.MoveUp();
            //System.out.println("przycisksiemaaa");
        }

    }
    class PlayerDownAction extends AbstractAction
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            logic.dsp.source.player.MoveDown();
            //System.out.println("przycisksiemaaa");
        }

    }
    class PlayerRightAction extends AbstractAction
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            logic.dsp.source.player.MoveRight();
            //System.out.println("przycisksiemaaa");
        }

    }
    class PlayerLeftAction extends AbstractAction
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            logic.dsp.source.player.MoveLeft();
            //System.out.println("przycisksiemaaa");
        }

    }
    */

}
