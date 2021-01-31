package app

import lib.sRAD.gui.sComponent.SFrame
import lib.sRAD.gui.sComponent.SPanel

class App: SFrame() {

    private val pInformativo: SPanel
    private val pGrafica: Grafica

    init {
        val pIzquierdo = SPanel(30, 50, 720, 650)
        add(pIzquierdo)

        pGrafica = object: Grafica() {
            override fun getCurrentFrame(): SFrame {
                return this@App
            }
        }
        pIzquierdo.add(pGrafica)

        pInformativo = SPanel(770, 50, 480, 650)
        add(pInformativo)

        setMainBar("Arbol Binario")
        setProperties()
    }

    fun actualizarInformacion() {

    }

}