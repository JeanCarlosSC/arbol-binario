package lib.sRAD.gui.sComponent;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static lib.sRAD.gui.component.Resource.*;

public class SButton extends JButton {

    /**
     * default button
     */
    public SButton() {
        super();
    }

    /**
     * Icon button
     */
    public SButton(int x, int y, Icon icon, Cursor cursor) {
        setProperties(x, y, icon, cursor);
    }

    /**
     * text button
     */
    public SButton(int x, int y, int width, int height) {
        this(x, y, width, height, "", handCursor, fontTitleMini, darkGray, darkWhite, semiDarkGray2Border, "CENTER", true, semiDarkGray);
    }
    public SButton(int x, int y, int width, int height, String text, Cursor cursor, Font font, Color background, Color foreground, Border border,
                   String hAlignment, Boolean isSolid, Color backgroundEntered ) {
        setProperties(x, y, width, height, text, cursor, font, background, foreground, border, LEFT, isSolid, backgroundEntered);
    }

    public void setProperties(int x, int y, int width, int height, String text, Cursor cursor, Font font, Color background, Color foreground,
                              Border border, int hAlignment, Boolean isSolid, Color backgroundEntered) {
        setProperties(x, y, width, height, cursor, background, isSolid);
        this.setText(text);
        this.setFont(font);
        this.setForeground(foreground);
        this.setBorder(border);
        this.setHorizontalAlignment(hAlignment);
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(backgroundEntered);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(background);
            }
        });
    }

    public void setProperties(int x, int y, int width, int height, Cursor cursor, Color background, Boolean isSolid) {
        setLocation(x, y);
        setSize(width, height);
        setCursor(cursor);
        setBackground(background);
        this.setContentAreaFilled(isSolid);
    }

    public void setProperties(int x, int y, Icon icon, Cursor cursor) {
        this.setLocation(x, y);
        this.setContentAreaFilled(false);
        this.setBorder(null);
        this.setCursor(cursor);
        this.setFocusable(false);
        if (icon != null) {
            this.setSize(icon.getIconWidth(), icon.getIconHeight());
            this.setIcon(icon);
        }
    }

    /**
     * text button filled with icon
     */
    public void setProperties(int x, int y, int width, int height, Cursor cursor, Color background, Boolean isSolid, Icon icon) {
        this.setProperties(x, y, icon, cursor);
        this.setSize(width, height);
        this.setBackground(background);
        this.setContentAreaFilled(isSolid);
    }

}
