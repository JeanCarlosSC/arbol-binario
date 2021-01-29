package lib.sRAD.gui.sComponent;

import lib.sRAD.gui.component.MainBar;
import lib.sRAD.gui.component.Theme;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

import static lib.sRAD.gui.resource.Resource.blackBorderTransparent;

public class SFrame extends JFrame {

    public void setProperties() {
        setProperties(1280, 720, Theme.bg3, true, blackBorderTransparent, null, true, null);
    }

    public void setProperties(int width, int height, Color background, Boolean undecorated, Border border, Component relativeLocation,
                              Boolean visible, LayoutManager layout) {
        rootPane.setBorder(border);
        setSize(width, height);
        setLocationRelativeTo(relativeLocation);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(background);
        setUndecorated(undecorated);
        setLayout(layout);
        setVisible(visible);
    }

    public void setMainBar(String title) {
        setMainBar(title, "resources/exampleLogo.png");
    }

    public void setMainBar(String title, String pathLogo) {
        MainBar mainBar = new MainBar(this);
        mainBar.setTitle(title);
        mainBar.setLogo(new ImageIcon(pathLogo));
        this.add(mainBar);
    }

}
