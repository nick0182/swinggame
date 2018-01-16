package gui;

import javax.swing.*;
import java.awt.*;

class ExitButton extends JButton{

    ExitButton() {
        super("Exit");

        Dimension dim = getPreferredSize();
        dim.width = 250;
        dim.height = 75;
        setPreferredSize(dim);
    }
}
