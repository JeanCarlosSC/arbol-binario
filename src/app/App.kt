package app

import lib.sRAD.gui.component.Resource.*
import lib.sRAD.gui.sComponent.SButton
import lib.sRAD.gui.sComponent.SFrame
import lib.sRAD.gui.sComponent.SLabel
import lib.sRAD.gui.sComponent.SPanel

class App: SFrame() {

    private val pInformativo: SPanel
    private val pGrafica: Grafica
    //objetos decorativos del panel informativo
    private val bInsertar: SButton
    private val bRetirar: SButton
    private val lIn: SLabel //label In-orden
    private val lPre: SLabel //label Pre-orden
    private val lPos: SLabel //label Pos-orden
    private val lNiveles: SLabel

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

        lIn = SLabel(32, 96, 430, 25, "")
        lPre = SLabel(32, 128, 430, 25, "")
        lPos = SLabel(32, 160, 430, 25, "")
        lNiveles = SLabel(32, 192, 430, 25, "")
        //actualizar
        actualizarInformacion()
    }

    fun actualizarInformacion() {
        val arbol = pGrafica.arbol

        pInformativo.removeAll()

        //carga informacion general
        pInformativo.add(bRetirar)

        if(!arbol.isEmpty()) {
            lIn.text = "In-orden: ${arbol.inOrden(arbol.raiz)}"
            pInformativo.add(lIn)

            lPre.text = "Pre-orden: ${arbol.preOrden(arbol.raiz)}"
            pInformativo.add(lPre)

            lPos.text = "Pos-orden:${arbol.posOrden(arbol.raiz)}"
            pInformativo.add(lPos)

            lNiveles.text = "Recorrido por niveles: ${arbol.niveles(arbol.raiz)}"
            pInformativo.add(lNiveles)
        }

        //carga informacion particular
        if(arbol.modo == ArbolBinario.MODO_ABECEDARIO) {
            1
        }
        else {
            pInformativo.add(bInsertar)
        }
        repaint()
    }

}