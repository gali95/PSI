import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;

/**
 * Created by Lach on 2016-11-01.
 */
public class ExampleGame {
    private JPanel panel1;
    private JPanel game;
    public ADisplay dsp;

    public void NextFrame()
    {

    }
    private void createUIComponents() {

        dsp = new ADisplay();
        game = dsp;
        // TODO: place custom component creation code here
        InitDisplay();
    }
    void InitDisplay()
    {
        dsp.source = new AMap();
        dsp.source.InitEmpty(10,10,"tile.jpg");
        dsp.source.zarzadzacz = new AGame();
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

    public ExampleGame()
    {
        PlayerUpAction npa = new PlayerUpAction();
        panel1.getInputMap(2).put(KeyStroke.getKeyStroke("W"), "nastepnyButtonKeyboardShortcut");
        panel1.getActionMap().put("nastepnyButtonKeyboardShortcut", npa);

        PlayerDownAction npa2 = new PlayerDownAction();
        panel1.getInputMap(2).put(KeyStroke.getKeyStroke("S"), "a2");
        panel1.getActionMap().put("a2", npa2);

        PlayerLeftAction npa3 = new PlayerLeftAction();
        panel1.getInputMap(2).put(KeyStroke.getKeyStroke("A"), "a3");
        panel1.getActionMap().put("a3", npa3);

        PlayerRightAction npa4 = new PlayerRightAction();
        panel1.getInputMap(2).put(KeyStroke.getKeyStroke("D"), "a4");
        panel1.getActionMap().put("a4", npa4);

        PlayerAttackUpAction npa5 = new PlayerAttackUpAction();
        panel1.getInputMap(2).put(KeyStroke.getKeyStroke("I"), "a5");
        panel1.getActionMap().put("a5", npa5);

        PlayerAttackDownAction npa6 = new PlayerAttackDownAction();
        panel1.getInputMap(2).put(KeyStroke.getKeyStroke("K"), "a6");
        panel1.getActionMap().put("a6", npa6);

        PlayerAttackLeftAction npa7 = new PlayerAttackLeftAction();
        panel1.getInputMap(2).put(KeyStroke.getKeyStroke("J"), "a7");
        panel1.getActionMap().put("a7", npa7);

        PlayerAttackRightAction npa8 = new PlayerAttackRightAction();
        panel1.getInputMap(2).put(KeyStroke.getKeyStroke("L"), "a8");
        panel1.getActionMap().put("a8", npa8);

    }

    public static void main(String[] args) {
        ExampleGame exGameo =  new ExampleGame();
        JFrame frame = new JFrame("ExampleGame");
        frame.setContentPane(exGameo.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        while(true)
        {
            try {
                TimeUnit.MILLISECONDS.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            exGameo.dsp.physicComponent(0.04);
            exGameo.dsp.paintComponent(exGameo.dsp.getGraphics());
        }
    }
}
