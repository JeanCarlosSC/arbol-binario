package lib.sRAD.gui.sComponent;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

import static lib.sRAD.gui.component.Resource.semiDarkGray2Border;
import static lib.sRAD.gui.component.Resource.semiDarkGrayBlue;

public class SPanel extends JPanel {

    public SPanel() {
        this(0, 0, 0, 0, semiDarkGrayBlue, semiDarkGray2Border, null);
    }

    public SPanel(int x, int y, int width, int height, Color background, Border border) {
        setProperties(x, y, width, height, background, border, null);
    }

    public SPanel(int x, int y, int width, int height, Color background, Border border, LayoutManager layout) {
        setProperties(x, y, width, height, background, border, layout);
    }

    public void setProperties() {
        setProperties(0, 0, 0, 0, semiDarkGrayBlue, semiDarkGray2Border, null);
    }

    public void setProperties(int x, int y, int width, int height, Color background, Border border) {
        setProperties(x, y, width, height, background, border, null);
    }

    public void setProperties(int x, int y, int width, int height, Color background, Border border, LayoutManager layout) {
        this.setBounds(x, y, width, height);
        this.setBackground(background);
        this.setBorder(border);
        this.setLayout(layout);
    }

}
