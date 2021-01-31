package app

import lib.sRAD.gui.component.Resource.*
import lib.sRAD.gui.sComponent.SButton
import lib.sRAD.gui.sComponent.SFrame
import lib.sRAD.gui.sComponent.SPanel

class App: SFrame() {

    private val pInformativo: SPanel
    private val pGrafica: Grafica
    //objetos decorativos del panel informativo
    private val bInsertar: SButton
    private val bRetirar: SButton

    init {
        //frame
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

        //objetos decorativos del panel informativo
        bInsertar = SButton(32, 32, 25, 25, "+", defaultCursor, fontTitle, null, wp1, null, DTII3, null)
        bInsertar.addActionListener { pGrafica.addVertice() }
        bInsertar.toolTipText = "Insertar valor en el árbol"

        bRetirar = SButton(57, 32, 25, 25, "-", defaultCursor, fontTitle, null, ta7, null, DTII3, null)
        bRetirar.addActionListener { pGrafica.removeVertice() }
        bRetirar.toolTipText = "Retirar valor del árbol"

        //actualizar
        actualizarInformacion()
    }

    fun actualizarInformacion() {
        pInformativo.removeAll()

        //carga informacion general
        pInformativo.add(bRetirar)

        //carga informacion particular
        if(pGrafica.arbol.modo == ArbolBinario.MODO_ABECEDARIO) {
            1
        }
        else {
            pInformativo.add(bInsertar)
        }
        repaint()
    }

}