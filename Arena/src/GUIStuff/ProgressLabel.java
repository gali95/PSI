package GUIStuff;

import AActualGame.AAOEAttack;
import AActualGame.AObject;

import javax.swing.*;

/**
 * Created by Lach on 2016-12-08.
 */
public class ProgressLabel {

    JLabel target;
    int actual,max;

    public void SetNewProgress(int max)
    {
        this.max = max;
        actual = 0;
        UpdateLabel();
    }
    public synchronized Boolean Increase() // returns true if it was last
    {
        actual++;
        UpdateLabel();
        return actual == max;
    }
    public void UpdateLabel()
    {
        target.setText(String.valueOf(actual) + "/" + String.valueOf(max));
    }
    public void SetLabel(JLabel target)
    {
        this.target = target;
    }

    public static void main(String[] args) {

        Object[] wsk = new Object[3],wsk2 = new Object[3];

        wsk[0] = new MainWindow();
        wsk[1] = new MainWindow();
        wsk[2] = new MainWindow();

        wsk2[0] = new MainWindow();
        wsk2[1] = new ProgressLabel();
        wsk2[2] = new MainWindow();

        for(int i=0;i<wsk.length;i++)
        {
            if(wsk[i].getClass() != wsk2[i].getClass())
            {
                System.out.println("zle");
            }
        }





    }

}
