package app

import lib.sRAD.gui.component.VentanaEmergente
import lib.sRAD.gui.sComponent.*
import lib.sRAD.logic.isInt
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.JOptionPane

abstract class Grafica: SScrollPane() {
    val arbol = ArbolBinario()
    private val pInterno = SPanel(0, 0, 706, 610)//Panel contenedor de la gráfica

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
        pInterno.border = null
        add(pInterno)
        setViewportView(pInterno)
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
        taNum.addActionListener {
            if(taNum.text.isInt()) {
                arbol.insertar(taNum.text.toInt())
                actualizar()
            }
            else {
                JOptionPane.showMessageDialog(null, "Ingrese valores enteros", "Error", JOptionPane.ERROR_MESSAGE)
            }
            ventana.cerrar()
        }
        ventana.add(taNum)

        val btCancelar = SButton(355, 21, 100, 32, "Cancelar")
        btCancelar.addActionListener {
            ventana.cerrar()
        }
        ventana.add(btCancelar)

        ventana.lanzar()
        return
    }

    fun actualizar() {
        pInterno.removeAll()
        if(!arbol.isEmpty()) {
            pInterno.setSize(706, pInterno.height)
            dibujar(arbol.raiz)
        }
        repaint()
    }

    fun dibujar(nodo: NodoBinario?) {
        if(nodo!=null) {
            //modifica tamaño del panel
            if(nodo.x+100>pInterno.width) {
                pInterno.setSize(nodo.x + 100, pInterno.height)
            }
            if(nodo.y+100>pInterno.height) {
                pInterno.setSize(pInterno.width, nodo.y + 100)
            }
            //dibuja nodo
            val panel  = SPanel(nodo.x, nodo.y, 64, 32)

            val lValor = SLabel(4, 4, 60, 28, nodo.valor.toString())
            panel.add(lValor)

            pInterno.add(panel)

            //dibuja nodos hijos
            dibujar(nodo.izquierda)
            dibujar(nodo.derecha)
        }
    }

    abstract fun getCurrentFrame(): SFrame

}