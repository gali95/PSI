import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Lach on 2016-10-19.
 */
public class tescik {
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JButton wynikButton;
    private JButton uczButton;
    private JLabel label3;
    private JLabel labalowynikoitero;
    private JLabel Result1;
    private JLabel Result2;
    private JLabel Result3;
    private JLabel Result4;

    XORNetwork network;

    public void SetActualWynikLabels(double[] wyniki)
    {
        Result1.setText(Double.toString(wyniki[0]));
        Result2.setText(Double.toString(wyniki[1]));
        Result3.setText(Double.toString(wyniki[2]));
        Result4.setText(Double.toString(wyniki[3]));
    }

    public static void main(String[] args)
    {

        JFrame rama = new JFrame("apka");
        tescik nowy = new tescik();
        rama.setContentPane(nowy.panel1);
        rama.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        rama.pack();

        rama.setVisible(true);
    }

    public tescik()
    {

        network = new XORNetwork();
        network.eloha = this;
        int[] ilosciNeuronow = {2,100,100,1};
        network.CreateNetwork(ilosciNeuronow);
        ActionListener fajny = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==wynikButton)
                {
                    double[] wejscie = { Double.parseDouble(textField1.getText()) , Double.parseDouble(textField2.getText()) };
                    network.testEntries = wejscie;
                    network.testResult = new double[1];
                    network.TestNetwork();
                    label3.setText(Double.toString(network.networkResult[0]));

                }
                else if(e.getSource()==uczButton)
                {
                    network.LearnUntil(0.99);
                    labalowynikoitero.setText(Integer.toString(network.itersToLearn));
                }
            }
        };
        uczButton.addActionListener(fajny);
        wynikButton.addActionListener(fajny);
    }

}
