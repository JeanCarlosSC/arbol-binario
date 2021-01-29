package lib.sRAD.gui.sComponent;

import javax.swing.*;
import java.awt.*;

import static lib.sRAD.gui.resource.Resource.darkWhite;
import static lib.sRAD.gui.resource.Resource.fontText;

public class SLabel extends JLabel {

    public SLabel(){
        super();
    }

    /**
     * icon label
     */
    public SLabel (int x, int y, ImageIcon icon) {
        setProperties(x, y, icon, null);
    }
    public SLabel (int x, int y, ImageIcon icon, Cursor cursor) {
        setProperties(x, y, icon, cursor);
    }

    /**
     * text label
     */
    public SLabel (int x, int y, int width) {
        setProperties(x, y, width, 18, "", fontText, darkWhite, LEFT, null);
    }
    public SLabel (int x, int y, int width, int height, String text, Font font, Color foreground, int hAlignment, Color background) {
        setProperties(x , y, width, height, text, font, foreground, hAlignment, background);
    }

    /**
     * icon label
     */
    public void setProperties(int x, int y, ImageIcon icon) {
        this.setSize(icon.getIconWidth(), icon.getIconHeight());
        this.setLocation(x, y);
        this.setIcon(icon);
    }
    public void setProperties(int x, int y, ImageIcon icon, Cursor cursor) {
        setProperties(x, y, icon);
        this.setCursor(cursor);
    }

    /**
     * text label
     */
    public void setProperties(int x, int y, int width, int height, String text, Font font, Color foreground, int hAlignment) {
        this.setBounds(x, y, width, height);
        this.setForeground(foreground);
        this.setFont(font);
        this.setText(text);
        this.setHorizontalAlignment(hAlignment);
    }
    public void setProperties(int x, int y, int width, int height, String text, Font font, Color foreground, int hAlignment, Color background) {
        setProperties(x, y, width, height, text, font, foreground, hAlignment);
        this.setBackground(background);
    }

}
