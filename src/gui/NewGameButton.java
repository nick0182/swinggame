package gui;

import javax.swing.*;
import java.awt.*;

class NewGameButton extends JButton{

    NewGameButton() {
        super("New Game");

        Dimension dim = getPreferredSize();
        dim.width = 250;
        dim.height = 75;
        setPreferredSize(dim);
    }
}
