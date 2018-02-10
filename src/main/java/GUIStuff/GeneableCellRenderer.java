package GUIStuff;

import GeneticAlghorithm.basicClassesInterfaces.Geneable;

import javax.swing.*;
import java.awt.*;

public class GeneableCellRenderer extends JLabel implements ListCellRenderer{

    private static final Color HighlightColor = new Color (50,220,255);
    private static final Color DefaultColor = new Color (240,240,240);

    public GeneableCellRenderer()
    {
        setOpaque(true);
        setIconTextGap(10);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Geneable entry = (Geneable) value;
        setText(entry.GetName()+" "+index);
        if(isSelected)
        {
            setBackground(HighlightColor);
            setForeground(Color.white);
        }
        else
        {
            setBackground(Color.white);
            setForeground(Color.black);
        }
        return this;
    }
}
