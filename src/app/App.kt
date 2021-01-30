package app

import lib.sRAD.gui.sComponent.SFrame
import lib.sRAD.gui.sComponent.SPanel

class App: SFrame() {

    init {
        val pIzquierdo = SPanel(30, 50, 720, 650)
        add(pIzquierdo)

        val grafica = object: Grafica() {
            override fun getCurrentFrame(): SFrame {
                return this@App
            }
        }
        pIzquierdo.add(grafica)

        setMainBar("Arbol Binario")
        setProperties()
    }

}