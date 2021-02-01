package app

import lib.sRAD.gui.component.Resource.*
import lib.sRAD.gui.sComponent.*
import lib.sRAD.logic.isInt
import java.util.*
import javax.swing.JOptionPane

const val PRE_ORDEN = 0
const val POS_ORDEN = 1

class App: SFrame() {

    private val pInformativo: SPanel
    //pIzquierdo
    private val pIzquierdo: SPanel
    private val pGrafica: Grafica
    private val tfIngresar: STextField
    //objetos decorativos del panel informativo
    private val bInsertar: SButton
    private val bRetirar: SButton
    private val bIzquierda: SButton
    private val bDerecha: SButton
    private val bModo: SButton //Cambia de modo el árbol
    private val lIn: SLabel //label In-orden
    private val lPre: SLabel //label Pre-orden
    private val lPos: SLabel //label Pos-orden
    private val lNiveles: SLabel
    private val lGordura: SLabel //label ancho del árbol
    private val lAltura: SLabel
    private val lHojas: SLabel
    private val lCompleto: SLabel //Indica si el árbol es completo

    private val tfEsta: STextField //Valor para buscar si está en el árbol
    private val lEsta: SLabel //Label correspondiente al tfEsta
    //modo abecedario
    private val tfInOrden: STextField //ingresa in-orden para re-construir
    private val lInOrden: SLabel //label correspondiente al anterior

    private val tfSegundoOrden: STextField //ingresa en pre-orden o en pos-orden dependiendo del orden seleccionado
    private val lSegundoOrden: SLabel //tipo de orden que se ingresa para la re-construccion
    private var orden = PRE_ORDEN //segundo orden

    private val bOrden: SButton //cambia el segundo orden
    private val bConstruir: SButton //boton para re-construir

    init {
        //frame y pIzquierdo
        pIzquierdo = SPanel(30, 50, 720, 650)
        add(pIzquierdo)

        tfIngresar = STextField(2, 622, 716, 28)
        tfIngresar.addActionListener { insertar() }

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

        lIn = SLabel(32, 96, 1000, 25, "")
        lPre = SLabel(32, 128, 1000, 25, "")
        lPos = SLabel(32, 160, 1000, 25, "")
        lNiveles = SLabel(32, 192, 1000, 25, "")
        lGordura = SLabel(32, 224, 430, 25, "")
        lAltura = SLabel(32, 256, 430, 25, "")
        lHojas = SLabel(32, 288, 430, 25, "")
        lCompleto = SLabel(32, 320, 430, 25, "")

        lEsta = SLabel(32, 356, 430, 25, "")
        tfEsta = STextField(86, 352, 70, 32)
        tfEsta.addActionListener {
            lEsta.text = "¿Está                   en el árbol?${
                if(pGrafica.arbol.estaEnArbol(pGrafica.arbol.raiz, tfEsta.text)) ": Sí"
                else ": No"
            }"
        }

        //modo abecedario
        lInOrden = SLabel(32, 356+64, 200, 25, "In-orden")
        tfInOrden = STextField(86+20+5, 352+64, 230, 32)

        lSegundoOrden = SLabel(32, 356+64+40, 200, 25, "")
        tfSegundoOrden = STextField(86+20+5, 352+64+40, 230, 32)

        bOrden = SButton(86+20+5+230+20, 352+64+40+3, 25, 25, "<>", defaultCursor, fontText, null, agca2, null, DTII3, null)
        bOrden.addActionListener {
            orden = if(orden == PRE_ORDEN) POS_ORDEN else PRE_ORDEN
            actualizarInformacion()
        }
        bOrden.toolTipText = "Cambiar de orden"

        bConstruir = SButton(150, 352+64+40+3+64, 150, 32, "RE-CONSTRUIR")
        bConstruir.addActionListener { construir() }
        bConstruir.toolTipText = "Construir un árbol a partir de la información dada"

        //general
        bIzquierda = SButton(82, 32, 25, 25, "<", defaultCursor, fontTitle, null, agca2, null, DTII3, null)
        bIzquierda.addActionListener {
            lIn.setLocation(lIn.x-32, lIn.y)
            lPre.setLocation(lPre.x-32, lPre.y)
            lPos.setLocation(lPos.x-32, lPos.y)
            lNiveles.setLocation(lNiveles.x-32, lNiveles.y)
        }
        bIzquierda.toolTipText = "Mover la información hacia la izquierda"

        bDerecha = SButton(107, 32, 25, 25, ">", defaultCursor, fontTitle, null, agca2, null, DTII3, null)
        bDerecha.addActionListener {
            lIn.setLocation(lIn.x+32, lIn.y)
            lPre.setLocation(lPre.x+32, lPre.y)
            lPos.setLocation(lPos.x+32, lPos.y)
            lNiveles.setLocation(lNiveles.x+32, lNiveles.y)
        }
        bDerecha.toolTipText = "Mover información hacia la derecha"

        bModo = SButton(132, 32, 25, 25, "M", defaultCursor, fontTitle, null, wp4, null, DTII3, null)
        bModo.addActionListener {
            pGrafica.arbol.cambiarModo()
            pGrafica.actualizar()
        }
        bModo.toolTipText = "Cambiar de modo"

        //actualizar
        actualizarInformacion()
    }

    fun actualizarInformacion() {
        val arbol = pGrafica.arbol

        pInformativo.removeAll()

        //carga informacion general
        pInformativo.add(bRetirar)
        pInformativo.add(bModo)

        if(!arbol.isEmpty()) {
            lIn.text = "In-orden: ${arbol.inOrden(arbol.raiz)}"
            pInformativo.add(lIn)

            lPre.text = "Pre-orden: ${arbol.preOrden(arbol.raiz)}"
            pInformativo.add(lPre)

            lPos.text = "Pos-orden:${arbol.posOrden(arbol.raiz)}"
            pInformativo.add(lPos)

            lNiveles.text = "Niveles: ${arbol.niveles(arbol.raiz)}"
            pInformativo.add(lNiveles)

            lGordura.text = "Gordura: ${arbol.gordura(arbol.raiz)}"
            pInformativo.add(lGordura)

            lAltura.text = "Altura: ${arbol.altura(arbol.raiz)}"
            pInformativo.add(lAltura)

            lHojas.text = "Número de hojas: ${arbol.numeroDeHojas(arbol.raiz)}"
            pInformativo.add(lHojas)

            lCompleto.text = "El árbol ${if(arbol.completo()) "" else "no "}es completo"
            pInformativo.add(lCompleto)

            pInformativo.add(tfEsta)
            lEsta.text = "¿Está                   en el árbol?"
            pInformativo.add(lEsta)

            pInformativo.add(bIzquierda)
            pInformativo.add(bDerecha)
        }
        else {
            //re-acomoda labels
            lIn.setLocation(32, lIn.y)
            lPre.setLocation(32, lPre.y)
            lPos.setLocation(32, lPos.y)
            lNiveles.setLocation(32, lNiveles.y)
        }

        //carga informacion particular
        if(arbol.modo == ArbolBinario.MODO_ABECEDARIO) {
            //pIzquierdo
            pIzquierdo.remove(tfIngresar)
            pIzquierdo.setSize(720, 624)
            //pInformativo
            pInformativo.add(lInOrden)
            pInformativo.add(tfInOrden)

            lSegundoOrden.text = if(orden == PRE_ORDEN) "Pre-orden" else "Pos-orden"
            pInformativo.add(lSegundoOrden)
            pInformativo.add(tfSegundoOrden)

            pInformativo.add(bOrden)
            pInformativo.add(bConstruir)
        }
        else {
            //pIzquierdo
            pIzquierdo.add(tfIngresar)
            pIzquierdo.setSize(720, 652)
            //pInformativo
            pInformativo.add(bInsertar)
        }
        repaint()
    }

    private fun insertar() {
        pGrafica.arbol.raiz= null
        val tokens = StringTokenizer(tfIngresar.text)
        //ingresa los valores que sean válidos
        while (tokens.hasMoreTokens()) {
            val token = tokens.nextToken()
            if (token.isInt()) {
                pGrafica.arbol.insertar(token.toInt())
            }
            else {
                JOptionPane.showMessageDialog(null, "El valor $token no se insertará ya que no es un entero válido", "Error",
                    JOptionPane.ERROR_MESSAGE)
            }
        }
        pGrafica.actualizar()
    }

    private fun construir() {

    }

}