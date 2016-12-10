package GUIStuff;

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

}
