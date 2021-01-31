package app

import lib.sRAD.gui.component.Resource.*
import lib.sRAD.gui.sComponent.SButton
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
            override fun actualizar() {
                super.actualizar()
                actualizarInformacion()
            }
        }
        pIzquierdo.add(pGrafica)

        pInformativo = SPanel(770, 50, 480, 650)
        add(pInformativo)

        setMainBar("Arbol Binario")
        setProperties()

        actualizarInformacion()
    }

    fun actualizarInformacion() {
        //carga informacion general

        //carga informacion particular

        if(pGrafica.arbol.modo == ArbolBinario.MODO_ABECEDARIO) {
            1
        }
        else {

            val bInsertar = SButton(32, 32, 32, 32, "+", handCursor, fontTitle2, wp1, white, wp2Border, mustard, darkOcherBorder)
            bInsertar.addActionListener { pGrafica.addVertice() }
            bInsertar.toolTipText = "insertar valor en el árbol"
            pInformativo.add(bInsertar)

        }
        repaint()
    }

}