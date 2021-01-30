package lib.sRAD.gui.component;

import lib.sRAD.gui.sComponent.SFrame;

public class VentanaEmergente extends SFrame {

    private SFrame frame;

    public VentanaEmergente(SFrame frame, int width, int height) {
        this.frame = frame;
        setProperties(width, height, Theme.vebg);
    }

    public void lanzar() {
        frame.setEnabled(false);
        setVisible(true);
    }

    public void cerrar() {
        setVisible(false);
        frame.setEnabled(true);
    }

}
