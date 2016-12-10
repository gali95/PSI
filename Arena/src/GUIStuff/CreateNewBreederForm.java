package GUIStuff;

import GUIStuff.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * Created by Lach on 2016-12-07.
 */
public class CreateNewBreederForm implements Runnable{
    private JButton OKButton;
    private JButton ANULUJButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JPanel nazw;
    private MainWindow source;
    private JFrame fata;

    public CreateNewBreederForm(MainWindow nSource)
    {
        ActionListener fajny = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==OKButton)
                {
                    source.CreateBreederFromForm(textField1.getText(),textField3.getText(),textField2.getText());
                }
                else
                {
                    fata.dispatchEvent(new WindowEvent(fata, WindowEvent.WINDOW_CLOSING));
                }
            }
        };
        OKButton.addActionListener(fajny);
        ANULUJButton.addActionListener(fajny);
        source = nSource;
    }
    public void run()
    {
        JFrame frame = new JFrame("CreateNewBreederForm");
        frame.setContentPane(this.nazw);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        fata = frame;
        frame.pack();
        frame.setVisible(true);
    }

}
