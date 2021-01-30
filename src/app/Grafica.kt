package app

import lib.sRAD.gui.component.VentanaEmergente
import lib.sRAD.gui.sComponent.*
import java.awt.event.MouseEvent
import java.awt.event.MouseListener

abstract class Grafica: SPanel() {
    val arbol = ArbolBinario()
    private val pInterno = SPanel(0, 0, 716, 620)//Panel contenedor de la gráfica

    init {
        pInterno.addMouseListener(object: MouseListener {
            override fun mouseClicked(e: MouseEvent?) {
                addVertice()
            }

            override fun mousePressed(e: MouseEvent?) {
            }

            override fun mouseReleased(e: MouseEvent?) {
            }

            override fun mouseEntered(e: MouseEvent?) {
            }

            override fun mouseExited(e: MouseEvent?) {
            }

        })
        add(pInterno)
        setProperties(2, 2, 716, 620)
    }

    fun addVertice() {
        abrirVentanaInsertar()
    }

    private fun abrirVentanaInsertar() {
        val ventana = VentanaEmergente(getCurrentFrame(), 500, 80)

        val lText = SLabel(30, 23, 240, 28,"Inserte un valor en el árbol")
        ventana.add(lText)

        val taNum = STextField(235, 21, 100, 32)
        ventana.add(taNum)

        val btCancelar = SButton(355, 21, 100, 32, "Cancelar")
        ventana.add(btCancelar)

        ventana.lanzar()
        return
    }

    abstract fun getCurrentFrame(): SFrame

}